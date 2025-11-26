package com.lisoft.exagen.infrastructure.api;

import com.lisoft.exagen.application.dtos.ResponseWrapper;
import com.lisoft.exagen.application.dtos.TestCategoryResponseDto;
import com.lisoft.exagen.application.dtos.TestResponseDto;
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

    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseWrapper<TestCategoryResponseDto>> getAllTestCategories(
            Authentication authentication
    ) {
        String userId = JwtManager.getUserId(authentication);

        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        true,
                        "",
                        null
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/{categoryId}/tests")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseWrapper<List<TestResponseDto>>> getAllTestsByCategoryId(
            @PathVariable String categoryId,
            Authentication authentication
    ) {
        String userId = JwtManager.getUserId(authentication);

        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        true,
                        "",
                        null
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

        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        true,
                        "",
                        null
                ),
                HttpStatus.OK
        );
    }
}
