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
@RequestMapping(API_VERSION + "/test-categories")
@SecurityRequirement(name = "bearerAuth")
public class TestCategoryRouter {
    private final TestCategoryService testCategoryService;

    public  TestCategoryRouter(TestCategoryService testCategoryService) {
        this.testCategoryService = testCategoryService;
    }

    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
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
