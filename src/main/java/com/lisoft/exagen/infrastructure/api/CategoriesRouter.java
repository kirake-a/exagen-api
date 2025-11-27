package com.lisoft.exagen.infrastructure.api;

import com.lisoft.exagen.application.dtos.CategoryResponseDto;
import com.lisoft.exagen.application.dtos.QuestionsCategoryResponseDto;
import com.lisoft.exagen.application.dtos.ResponseWrapper;
import com.lisoft.exagen.application.dtos.TestQuestionsDto;
import com.lisoft.exagen.domain.enums.QuestionTypeEnum;
import com.lisoft.exagen.domain.models.Category;
import com.lisoft.exagen.domain.templates.services.CategoryService;
import com.lisoft.exagen.infrastructure.mappers.CategoryMapper;
import com.lisoft.exagen.infrastructure.utils.JwtManager;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lisoft.exagen.domain.utils.Constants.API_VERSION;

@RestController
@RequestMapping(API_VERSION + "/categories")
@SecurityRequirement(name = "bearerAuth")
public class CategoriesRouter {
    private final CategoryService categoryService;

    public CategoriesRouter(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    @Operation(
        summary = "Get all categories by user ID",
        description = "Gets all categories associated with the authenticated user"
    )
    public ResponseEntity<ResponseWrapper<List<CategoryResponseDto>>> getAllCategoriesByUserId(
            Authentication authentication
    ) {
        String userId = JwtManager.getUserId(authentication);

        List<Category> categories = categoryService.getAllCategoriesByUserId(userId);

        List<CategoryResponseDto> response = categories.stream()
                .map(CategoryMapper::toCategoryResponseDto)
                .toList();

        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        true,
                        "Welcome user " + userId,
                        response
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/{categoryId}/questions")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Get all questions by category ID",
            description = "Allows an authenticated user to retrieve all questions for a specific category, with an optional filter by question type"
    )
    public ResponseEntity<ResponseWrapper<QuestionsCategoryResponseDto>> getAllQuestionsByCategoryId(
            @PathVariable Integer categoryId,
            Authentication authentication,
            @RequestParam(required = false) QuestionTypeEnum type
    ) {
        String userId = JwtManager.getUserId(authentication);

        TestQuestionsDto questions = categoryService.getQuestions(categoryId, userId, type);

        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        true,
                        "Welcome user " + userId,
                        new QuestionsCategoryResponseDto(
                                categoryId,
                                userId,
                                questions
                        )
                ),
                HttpStatus.OK
        );
    }

    @PostMapping("/")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Create a new category",
            description = "Allows an authenticated user to create a new category by providing a name"
    )
    public ResponseEntity<ResponseWrapper<CategoryResponseDto>> createCategory(
            @Valid @RequestParam String name,
            Authentication authentication
    ) {
        String userId = JwtManager.getUserId(authentication);

        Category createdCategory = categoryService.create(name, userId);

        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        true,
                        "",
                        CategoryMapper.toCategoryResponseDto(createdCategory)
                ),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{categoryId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Delete a category by ID",
            description = "Allows an authenticated user to delete a category by its ID"
    )
    public ResponseEntity<ResponseWrapper<Integer>> deleteCategory(
            @PathVariable Integer categoryId,
            Authentication authentication
    ) {
        String userId = JwtManager.getUserId(authentication);

        Category deletedCategory = categoryService.delete(categoryId, userId);

        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        true,
                        "Category deleted successfully",
                        deletedCategory.id()
                ),
                HttpStatus.OK
        );
    }
}
