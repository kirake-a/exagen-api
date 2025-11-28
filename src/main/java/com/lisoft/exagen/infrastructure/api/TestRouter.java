package com.lisoft.exagen.infrastructure.api;

import com.lisoft.exagen.application.dtos.CreateTestRequestDto;
import com.lisoft.exagen.application.dtos.ResponseWrapper;
import com.lisoft.exagen.application.dtos.TestResponseDto;
import com.lisoft.exagen.domain.models.Test;
import com.lisoft.exagen.domain.templates.services.TestService;
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
@RequestMapping(API_VERSION + "/tests")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Tests", description = "Endpoints for managing tests")
public class TestRouter {
    private final TestService testService;

    public TestRouter(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Get all tests for the authenticated user",
            description = "Allows an authenticated user to retrieve all tests with optional filters such as title and category ID"
    )
    public ResponseEntity<ResponseWrapper<List<TestResponseDto>>> getAllTest(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer categoryId,
            Authentication authentication
    ) {
        String userId = JwtManager.getUserId(authentication);
        List<Test> tests = testService.getAllTests(
                userId,
                title,
                categoryId
        );

        List<TestResponseDto> response = tests.stream()
                .map(TestMapper::toResponseDto)
                .toList();

        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        true,
                        "All tests from exagen system",
                        response
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/{testId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Get a test by its ID for the authenticated user",
            description = "Allows an authenticated user to retrieve a specific test by providing its ID"
    )
    public ResponseEntity<ResponseWrapper<TestResponseDto>> getTestById(
            @PathVariable String testId,
            Authentication authentication
    ) {
        String userId = JwtManager.getUserId(authentication);

        Test test = testService.getTestById(testId, userId);

        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        true,
                        "Successfully retrieved test",
                        TestMapper.toResponseDto(test)
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Get all tests by user ID for the authenticated user",
            description = "Allows an authenticated user to retrieve all tests associated with a specific user"
    )
    public ResponseEntity<ResponseWrapper<List<TestResponseDto>>> getTestByUserId(@PathVariable String userId) {
        List<Test> testsPreview = testService.getAllTestsByUserId(userId);

        List<TestResponseDto> response = testsPreview.stream()
                .map(TestMapper::toResponseDto)
                .toList();

        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        true,
                        "Successfully retrieved tests",
                        response
                ),
                HttpStatus.OK
        );
    }

    @PostMapping("/")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Create a new test for the authenticated user",
            description = "Allows an authenticated user to create a new test by providing the necessary data"
    )
    public ResponseEntity<ResponseWrapper<TestResponseDto>> createTest(
            @Valid @RequestBody CreateTestRequestDto testRequest,
            Authentication authentication
    ) {
        String userId = JwtManager.getUserId(authentication);

        Test createdTest = this.testService.createTest(
                TestMapper.createTestRequestDto2Model(testRequest, userId)
        );

        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        true,
                        "",
                        TestMapper.toResponseDto(createdTest)
                ),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{testId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Delete a test by its ID for the authenticated user",
            description = "Allows an authenticated user to delete a specific test by providing its ID"
    )
    public ResponseEntity<ResponseWrapper<String>> deleteTest(
            @PathVariable String testId,
            Authentication authentication
    ) {
        String userId = JwtManager.getUserId(authentication);

        Test deletedTest = this.testService.deleteTest(testId, userId);

        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        true,
                        "Test with id " + testId + " was deleted successfully",
                        deletedTest.id()
                ),
                HttpStatus.OK
        );
    }
}
