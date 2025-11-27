package com.lisoft.exagen.infrastructure.api;

import com.lisoft.exagen.application.dtos.ResponseWrapper;
import com.lisoft.exagen.application.dtos.SurveyResponseDto;
import com.lisoft.exagen.application.dtos.SurveyResponsesResponseDto;
import com.lisoft.exagen.domain.enums.SurveyStatusEnum;
import com.lisoft.exagen.infrastructure.utils.JwtManager;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
public class PublicSurveyRouter {

    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
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
    public ResponseEntity<ResponseWrapper<SurveyResponseDto>> createSurvey(
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
