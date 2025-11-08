package com.lisoft.exagen.infrastructure.api;

import com.lisoft.exagen.application.dtos.ResponseWrapper;
import com.lisoft.exagen.application.dtos.TestResponseDto;
import com.lisoft.exagen.domain.models.Test;
import com.lisoft.exagen.domain.templates.services.TestService;
import com.lisoft.exagen.infrastructure.mappers.TestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.lisoft.exagen.domain.utils.Constants.API_VERSION;

@RestController
@RequestMapping(API_VERSION + "/tests")
@SecurityRequirement(name = "bearerAuth")
public class TestRouter {
    private final TestService testService;

    public TestRouter(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "", description = "")
    public ResponseEntity<ResponseWrapper<List<TestResponseDto>>> getAllTest() {
        List<Test> tests = testService.getAllTests();

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
    @Operation(summary = "", description = "")
    public ResponseEntity<ResponseWrapper<TestResponseDto>> getTestById(@PathVariable String testId) {
        Test test = testService.getTestById(testId);

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
    @Operation(summary = "", description = "")
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
}
