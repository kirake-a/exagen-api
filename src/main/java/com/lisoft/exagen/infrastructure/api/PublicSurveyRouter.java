package com.lisoft.exagen.infrastructure.api;

import com.lisoft.exagen.application.dtos.*;
import com.lisoft.exagen.domain.enums.SurveyStatusEnum;
import com.lisoft.exagen.domain.models.PublicSurvey;
import com.lisoft.exagen.domain.models.PublicSurveyResponse;
import com.lisoft.exagen.domain.templates.services.PublicSurveyService;
import com.lisoft.exagen.infrastructure.mappers.PublicSurveyMapper;
import com.lisoft.exagen.infrastructure.mappers.PublicSurveyResponseMapper;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.lisoft.exagen.domain.utils.Constants.API_VERSION;

@RestController
@RequestMapping(API_VERSION + "/surveys")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Surveys", description = "Endpoints for managing surveys")
public class PublicSurveyRouter {
    private final PublicSurveyService surveyService;

    public  PublicSurveyRouter(PublicSurveyService surveyService) {
        this.surveyService = surveyService;
    }

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

        List<PublicSurvey> surveys = this.surveyService.getAllSurveysByUserId(userId);

        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        true,
                        "All surveys retrieved successfully",
                        surveys.stream()
                                .map(PublicSurveyMapper::toResponseDto)
                                .toList()
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

        PublicSurvey survey = this.surveyService.getSurveyById(surveyId, userId);

        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        true,
                        "We found a survey with the id: " +  surveyId,
                        PublicSurveyMapper.toResponseDto(survey)
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

        PublicSurvey createdSurvey = this.surveyService.createSurvey(
                PublicSurveyMapper.createSurveyRequestDto2Model(data, userId)
        );

        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        true,
                        "We successfully created a survey",
                        PublicSurveyMapper.toResponseDto(createdSurvey)
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
    public ResponseEntity<ResponseWrapper<List<String>>> createResponsesBySurveyId(
            @PathVariable String surveyId,
            @RequestParam List<SaveSurveyResponsesDto> data
    ) {
        LocalDateTime timestamp = LocalDateTime.now();

        List<String> responseIds = new ArrayList<>();

        for (SaveSurveyResponsesDto response : data) {

            PublicSurveyResponse savedResponse = this.surveyService.createResponse(
                    PublicSurveyResponseMapper.saveSurveyResponse2Model(
                            response,
                            surveyId,
                            timestamp
                    )
            );

            responseIds.add(savedResponse.id());
        }

        this.surveyService.updateTotalResponesByOne(surveyId);

        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        true,
                        "",
                        responseIds
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

        PublicSurvey deletedSurvey = this.surveyService.deleteSurveyById(surveyId, userId);

        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        true,
                        "Survey with id " +  surveyId + " was deleted successfully",
                        deletedSurvey.id()
                ),
                HttpStatus.OK
        );
    }
}
