package com.lisoft.exagen.application.services;

import com.lisoft.exagen.application.dtos.ClosedQuestionDto;
import com.lisoft.exagen.application.dtos.OpenQuestionDto;
import com.lisoft.exagen.application.dtos.TestQuestionsDto;
import com.lisoft.exagen.domain.enums.QuestionTypeEnum;
import com.lisoft.exagen.domain.exceptions.InvalidArgumentException;
import com.lisoft.exagen.domain.exceptions.ResourceNotFoundException;
import com.lisoft.exagen.domain.exceptions.UnauthorizedAccessException;
import com.lisoft.exagen.domain.models.Category;
import com.lisoft.exagen.domain.models.ClosedQuestion;
import com.lisoft.exagen.domain.models.OpenQuestion;
import com.lisoft.exagen.domain.templates.repositories.CategoryRepository;
import com.lisoft.exagen.domain.templates.repositories.ClosedQuestionRepository;
import com.lisoft.exagen.domain.templates.repositories.OpenQuestionRepository;
import com.lisoft.exagen.domain.templates.services.QuestionService;
import com.lisoft.exagen.domain.utils.DataValidator;
import com.lisoft.exagen.infrastructure.mappers.ClosedQuestionMapper;
import com.lisoft.exagen.infrastructure.mappers.OpenQuestionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.lisoft.exagen.domain.utils.Constants.*;

public class QuestionServiceImpl implements QuestionService {
    private final OpenQuestionRepository openQuestionRepository;
    private final ClosedQuestionRepository closedQuestionRepository;
    private final CategoryRepository categoryRepository;

    private final Logger logger = LoggerFactory.getLogger(QuestionServiceImpl.class);

    public QuestionServiceImpl(
            OpenQuestionRepository openQuestionRepository,
            ClosedQuestionRepository closedQuestionRepository,
            CategoryRepository categoryRepository
    ) {
        this.openQuestionRepository = openQuestionRepository;
        this.closedQuestionRepository = closedQuestionRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public TestQuestionsDto getQuestions(
            String userId,
            String statement,
            Integer categoryId,
            Integer questionsAmount,
            QuestionTypeEnum type
    ) {
        DataValidator.validateUserId(userId);

        if(categoryId != null) {
            var category = this.categoryRepository.getById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException(CATEGORY_NOT_FOUND_MESSAGE));
            DataValidator.validateOwnership(userId, category.userId());
        }

        List<OpenQuestionDto> openQuestions = List.of();
        List<ClosedQuestionDto> closedQuestions = List.of();

        if (type == null || type.equals(QuestionTypeEnum.OPEN)) {
           openQuestions = this.openQuestionRepository.getAllOpenQuestionsByUserId(userId)
                   .stream()
                   .filter(q -> categoryId == null || q.categoryId().equals(categoryId))
                   .filter(q -> statement == null || q.statement().toLowerCase().contains(statement.toLowerCase()))
                   .map(OpenQuestionMapper::toOpenQuestionDto)
                   .limit(questionsAmount != null ? questionsAmount : Integer.MAX_VALUE)
                   .toList();
        }

        if (type == null || type.equals(QuestionTypeEnum.CLOSED)) {
            closedQuestions = this.closedQuestionRepository.getAllClosedQuestionsByUserId(userId)
                    .stream()
                    .filter(q -> categoryId == null || q.categoryId().equals(categoryId))
                    .filter(q -> statement == null || q.statement().toLowerCase().contains(statement.toLowerCase()))
                    .map(ClosedQuestionMapper::toClosedQuestionDto)
                    .limit(questionsAmount != null ? questionsAmount : Integer.MAX_VALUE)
                    .toList();
        }

        return new TestQuestionsDto(closedQuestions, openQuestions);
    }

    @Override
    @Transactional(readOnly = true)
    public OpenQuestion getOpenQuestion(String userId, Integer questionId) {
        DataValidator.validateUserId(userId);

        OpenQuestion question = this.openQuestionRepository.getOpenQuestionById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException(QUESTION_NOT_FOUND_MESSAGE));

        if (!question.userId().equals(userId)) {
            throw new UnauthorizedAccessException(UNAUTHORIZED_ACCESS_2_QUESTION);
        }

        return question;
    }

    @Override
    @Transactional(readOnly = true)
    public ClosedQuestion getClosedQuestion(String userId, Integer questionId) {
        DataValidator.validateUserId(userId);

        ClosedQuestion question = this.closedQuestionRepository.getClosedQuestionById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException(QUESTION_NOT_FOUND_MESSAGE));

        if (!question.userId().equals(userId)) {
            throw new UnauthorizedAccessException(UNAUTHORIZED_ACCESS_2_QUESTION);
        }

        return question;
    }

    @Override
    @Transactional
    public String createQuestion(
            String userId,
            TestQuestionsDto questions,
            Integer categoryId
    ) {
        DataValidator.validateUserId(userId);
        DataValidator.validateQuestions2Add(questions.openQuestions(), questions.closedQuestions());

        Category category = this.categoryRepository.getById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        CATEGORY_NOT_FOUND_MESSAGE + " with the given id "  + categoryId
                ));

        if (!category.userId().equals(userId)) {
            throw new UnauthorizedAccessException(UNAUTHORIZED_ACCESS_2_CATEGORY);
        }

        List<OpenQuestionDto> openQuestions = questions.openQuestions();
        List<ClosedQuestionDto> closedQuestions = questions.closedQuestions();

        if (!openQuestions.isEmpty()) {
            for (OpenQuestionDto openQuestion : openQuestions) {
                this.openQuestionRepository.createOpenQuestion(
                        OpenQuestionMapper.openQuestionDtoToModel(openQuestion, userId, categoryId)
                );
            }
        }

        if (!closedQuestions.isEmpty()) {
            for (ClosedQuestionDto closedQuestion : closedQuestions) {
                this.closedQuestionRepository.createClosedQuestion(
                        ClosedQuestionMapper.closedQuestionDtoToModel(closedQuestion, userId, categoryId)
                );
            }
        }

        logger.info("All questions were created successfully with the given category {}", categoryId);
        return "All questions were created successfully";
    }

    @Override
    @Transactional
    public String deleteQuestion(
            String userId,
            Integer questionId,
            QuestionTypeEnum questionType
    ) {
        DataValidator.validateUserId(userId);

        if (Objects.isNull(questionId)) {
            throw new InvalidArgumentException("The question Id cannot be null");
        }

        Integer deletedQuestionId;

        switch (questionType) {
            case OPEN -> deletedQuestionId = this.deleteOpenQuestion(userId, questionId);
            case CLOSED -> deletedQuestionId = this.deleteClosedQuestion(userId, questionId);
            default -> throw new InvalidArgumentException("The question type is not supported");
        }

        return questionType + " question with id " + deletedQuestionId + " was deleted successfully";
    }

    private Integer deleteOpenQuestion(String userId, Integer questionId) {
        OpenQuestion question = this.openQuestionRepository.getOpenQuestionById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException(QUESTION_NOT_FOUND_MESSAGE));

        DataValidator.validateOwnership(userId, question.userId());

        this.openQuestionRepository.deleteOpenQuestionById(questionId);

        return question.id();
    }

    private Integer deleteClosedQuestion(String userId, Integer questionId) {
        ClosedQuestion question = this.closedQuestionRepository.getClosedQuestionById(questionId)
                .orElseThrow(() ->  new ResourceNotFoundException(QUESTION_NOT_FOUND_MESSAGE));

        DataValidator.validateOwnership(userId, question.userId());

        this.closedQuestionRepository.deleteClosedQuestionById(questionId);

        return question.id();
    }
}
