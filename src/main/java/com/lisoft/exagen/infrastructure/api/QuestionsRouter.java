package com.lisoft.exagen.infrastructure.api;

import com.lisoft.exagen.application.dtos.*;
import com.lisoft.exagen.domain.enums.QuestionTypeEnum;
import com.lisoft.exagen.domain.models.ClosedQuestion;
import com.lisoft.exagen.domain.models.OpenQuestion;
import com.lisoft.exagen.domain.templates.services.QuestionService;
import com.lisoft.exagen.infrastructure.mappers.ClosedQuestionMapper;
import com.lisoft.exagen.infrastructure.mappers.OpenQuestionMapper;
import com.lisoft.exagen.infrastructure.utils.JwtManager;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static com.lisoft.exagen.domain.utils.Constants.API_VERSION;
import static com.lisoft.exagen.domain.utils.Constants.QUESTION_FOUND_SUCCESSFULLY;

@RestController
@RequestMapping(API_VERSION + "/questions")
@SecurityRequirement(name = "bearerAuth")
public class QuestionsRouter {
    private final QuestionService questionService;

    public QuestionsRouter(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseWrapper<TestQuestionsDto>> getQuestions(
            @RequestParam(required = false) String statement,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Integer questionsAmount,
            @RequestParam(required = false) QuestionTypeEnum type,
            Authentication authentication
    ) {
        String userId = JwtManager.getUserId(authentication);

        TestQuestionsDto response = this.questionService.getQuestions(
                userId,
                statement,
                categoryId,
                questionsAmount,
                type
        );

        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        true,
                        "Questions retrieved successfully",
                        response
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/open/{questionId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseWrapper<OpenQuestionDto>> getOpenQuestionById(
            @NotNull @PathVariable Integer questionId,
            Authentication authentication
    ) {
        String userId = JwtManager.getUserId(authentication);

        OpenQuestion foundQuestion = this.questionService.getOpenQuestion(userId, questionId);

        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        true,
                        QUESTION_FOUND_SUCCESSFULLY,
                        OpenQuestionMapper.toOpenQuestionDto(foundQuestion)
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/closed/{questionId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseWrapper<ClosedQuestionDto>> getClosedQuestionById(
            @PathVariable Integer questionId,
            Authentication authentication
    ) {
        String userId = JwtManager.getUserId(authentication);

        ClosedQuestion foundQuestion = this.questionService.getClosedQuestion(userId, questionId);

        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        true,
                        QUESTION_FOUND_SUCCESSFULLY,
                        ClosedQuestionMapper.toClosedQuestionDto(foundQuestion)
                ),
                HttpStatus.OK
        );
    }

    @PostMapping("/")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseWrapper<String>> createQuestions(
            @Valid @RequestBody CreateQuestionsDto data,
            Authentication authentication
    ) {
        String userId = JwtManager.getUserId(authentication);

        this.questionService.createQuestion(
                userId,
                data.questions(),
                data.categoryId()
        );

        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        true,
                        "All questions were created successfully",
                        ""
                ),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{questionId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseWrapper<String>> deleteQuestionById(
            @PathVariable Integer questionId,
            @RequestParam QuestionTypeEnum type,
            Authentication authentication
    ) {
        String userId = JwtManager.getUserId(authentication);

        String response = this.questionService.deleteQuestion(userId, questionId, type);

        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        true,
                        "Question deleted successfully",
                        response
                ),
                HttpStatus.OK
        );
    }
}
