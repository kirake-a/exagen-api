package com.lisoft.exagen.infrastructure.api;

import com.lisoft.exagen.application.dtos.ResponseWrapper;
import com.lisoft.exagen.application.dtos.TestCategoryResponseDto;
import com.lisoft.exagen.application.dtos.TestResponseDto;
import com.lisoft.exagen.domain.models.Test;
import com.lisoft.exagen.domain.models.TestCategory;
import com.lisoft.exagen.domain.templates.services.TestCategoryService;
import com.lisoft.exagen.infrastructure.mappers.TestCategoryMapper;
import com.lisoft.exagen.infrastructure.mappers.TestMapper;
import com.lisoft.exagen.infrastructure.utils.JwtManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lisoft.exagen.domain.utils.Constants.API_VERSION;

@RestController
@RequestMapping(API_VERSION + "/test-categories")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Test Categories", description = "Endpoints for managing test categories")
public class TestCategoryRouter {
    private final TestCategoryService testCategoryService;

    public  TestCategoryRouter(TestCategoryService testCategoryService) {
        this.testCategoryService = testCategoryService;
    }

    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Get all test categories for the authenticated user",
            description = "Allows an authenticated user to retrieve all test categories associated with their account"
    )
    public ResponseEntity<ResponseWrapper<List<TestCategoryResponseDto>>> getAllTestCategories(
            Authentication authentication
    ) {
        String userId = JwtManager.getUserId(authentication);

        List<TestCategory> categories = this.testCategoryService.getAllTestCategoriesByUserId(userId);

        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        true,
                        "",
                        categories.stream()
                                .map(TestCategoryMapper::toTestCategoryResponseDto)
                                .toList()
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/{categoryId}/tests")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Get all tests by category ID for the authenticated user",
            description = "Allows an authenticated user to retrieve all tests associated with a specific test category"
    )
    public ResponseEntity<ResponseWrapper<List<TestResponseDto>>> getAllTestsByCategoryId(
            @PathVariable Integer categoryId,
            Authentication authentication
    ) {
        String userId = JwtManager.getUserId(authentication);

        List<Test> tests = this.testCategoryService.getAllTestsByCategoryId(categoryId, userId);

        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        true,
                        "",
                        tests.stream()
                                .map(TestMapper::toResponseDto)
                                .toList()
                ),
                HttpStatus.OK
        );
    }

    @PostMapping("/")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Create a new test category for the authenticated user",
            description = "Allows an authenticated user to create a new test category by providing the necessary data"
    )
    public ResponseEntity<ResponseWrapper<TestCategoryResponseDto>> createTestCategory(
            @Valid @RequestParam String name,
            Authentication authentication
    ) {
        String userId = JwtManager.getUserId(authentication);

        TestCategory category = this.testCategoryService.create(name, userId);

        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        true,
                        "",
                        TestCategoryMapper.toTestCategoryResponseDto(category)
                ),
                HttpStatus.CREATED
        );
    }
}
