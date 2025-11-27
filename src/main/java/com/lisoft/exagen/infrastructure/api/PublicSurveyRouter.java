package com.lisoft.exagen.infrastructure.api;

import com.lisoft.exagen.application.dtos.CreateSurveyRequestDto;
import com.lisoft.exagen.application.dtos.ResponseWrapper;
import com.lisoft.exagen.application.dtos.SurveyResponseDto;
import com.lisoft.exagen.application.dtos.SurveyResponsesResponseDto;
import com.lisoft.exagen.domain.enums.SurveyStatusEnum;
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
@RequestMapping(API_VERSION + "/surveys")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Surveys", description = "Endpoints for managing surveys")
public class PublicSurveyRouter {

    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Get all surveys",
            description = "Allows authenticated users to retrieve a list of surveys, with optional filtering by title and type."
    )
    public ResponseEntity<ResponseWrapper<List<SurveyResponseDto>>> getAllSurveys(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) SurveyStatusEnum type,
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

    @GetMapping("/{surveyId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Get survey by ID",
            description = "Allows authenticated users to retrieve a specific survey by its ID."
    )
    public ResponseEntity<ResponseWrapper<SurveyResponseDto>> getSurveyById(
            @PathVariable String surveyId,
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

    @GetMapping("/{surveyId}/responses")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Get all responses by survey ID",
            description = "Allows authenticated users to retrieve all responses for a specific survey by its ID."
    )
    public ResponseEntity<ResponseWrapper<List<SurveyResponsesResponseDto>>> getAllResponsesBySurveyId(
            @PathVariable String surveyId,
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
    @Operation(
            summary = "Create a new survey",
            description = "Allows authenticated users to create a new survey."
    )
    public ResponseEntity<ResponseWrapper<SurveyResponseDto>> createSurvey(
            @Valid @RequestBody CreateSurveyRequestDto data,
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

    @PostMapping("/{surveyId}/responses")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Create responses for a survey",
            description = "Allows authenticated users to create responses for a specific survey by its ID."
    )
    public ResponseEntity<ResponseWrapper<String>> createResponsesBySurveyId(
            @PathVariable String surveyId,
            Authentication authentication
    ) {
        String userId = JwtManager.getUserId(authentication);

        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        true,
                        "",
                        surveyId
                ),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{surveyId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Delete a survey",
            description = "Allows authenticated users to delete a specific survey by its ID."
    )
    public ResponseEntity<ResponseWrapper<String>> deleteSurvey(
            @PathVariable String surveyId,
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
