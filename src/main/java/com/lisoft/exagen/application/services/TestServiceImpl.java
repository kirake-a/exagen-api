package com.lisoft.exagen.application.services;

import com.lisoft.exagen.domain.exceptions.InvalidArgumentException;
import com.lisoft.exagen.domain.exceptions.ResourceNotFoundException;
import com.lisoft.exagen.domain.exceptions.UnauthorizedAccessException;
import com.lisoft.exagen.domain.models.Test;
import com.lisoft.exagen.domain.templates.repositories.ClosedQuestionRepository;
import com.lisoft.exagen.domain.templates.repositories.OpenQuestionRepository;
import com.lisoft.exagen.domain.templates.repositories.TestCategoryRepository;
import com.lisoft.exagen.domain.templates.repositories.TestReposity;
import com.lisoft.exagen.domain.templates.services.TestService;

import com.lisoft.exagen.domain.utils.DataValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.lisoft.exagen.domain.utils.Constants.*;

public class TestServiceImpl implements TestService {
    private final TestReposity repository;
    private final TestCategoryRepository testCategoryRepository;
    private final OpenQuestionRepository openQuestionRepository;
    private final ClosedQuestionRepository closedQuestionRepository;

    private final Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);

    public TestServiceImpl(
            TestReposity repository,
            TestCategoryRepository testCategoryRepository,
            OpenQuestionRepository openQuestionRepository,
            ClosedQuestionRepository closedQuestionRepository
    ) {
        this.repository = repository;
        this.testCategoryRepository = testCategoryRepository;
        this.openQuestionRepository = openQuestionRepository;
        this.closedQuestionRepository = closedQuestionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Test> getAllTests(
            String userId,
            String title,
            Integer categoryId
    ) {
        DataValidator.validateUserId(userId);

        logger.info("Getting all tests");
        List<Test> tests = this.repository.getAllTestsByUserId(userId);

        return tests.stream()
                .filter(t -> title == null || t.title().equals(title))
                .filter(q -> categoryId == null || q.categoryId().equals(categoryId))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Test> getAllTestsByUserId(String userId) {
        if (Objects.isNull(userId)) {
            String errorMessage = INVALID_ARGUMENT_MESSAGE + USER_ID_CANNOT_BE_NULL_MESSAGE;
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        logger.info("Getting all tests by user id");
        return this.repository.getAllTestsByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Test getTestById(String testId, String userId) {
        DataValidator.validateUserId(userId);

        if (Objects.isNull(testId)) {
            logger.error(TEST_ID_CANNOT_BE_NULL_MESSAGE);
            throw new IllegalArgumentException(TEST_ID_CANNOT_BE_NULL_MESSAGE);
        }

        logger.info("Getting test by id");
        Test testGetted =  this.repository.getTestById(testId)
                .orElseThrow(() -> {
                    String errorMessage = "Test with id " + testId + " not found";
                    logger.error(errorMessage);
                    return new ResourceNotFoundException(errorMessage);
                });

        if (!testGetted.userId().equals(userId)) {
            throw new UnauthorizedAccessException(UNAUTHORIZED_ACCESS_TO_TEST);
        }

        return testGetted;
    }

    @Override
    @Transactional
    public Test createTest(Test test) {
        DataValidator.validateUserId(test.userId());

        if (!this.testCategoryRepository.existsByCategoryId(test.categoryId())) {
            logger.error(CATEGORY_NOT_FOUND_MESSAGE);
            throw new ResourceNotFoundException(CATEGORY_NOT_FOUND_MESSAGE);
        }

        if (test.openQuestionIds().isEmpty() && test.closedQuestionIds().isEmpty()) {
            throw new InvalidArgumentException(CANNOT_CREATE_EXAM_WITH_NO_QUESTIONS);
        }

        validateOpenQuestions(test.openQuestionIds());
        validateClosedQuestions(test.closedQuestionIds());

        return this.repository.createTest(test);
    }

    @Override
    @Transactional
    public Test deleteTest(String testId, String userId) {
        DataValidator.validateUserId(userId);

        if (Objects.isNull(testId)) {
            logger.error(TEST_ID_CANNOT_BE_NULL_MESSAGE);
            throw new IllegalArgumentException(TEST_ID_CANNOT_BE_NULL_MESSAGE);
        }

        Test deletedTest = this.repository.getTestById(testId)
                .orElseThrow(() -> new ResourceNotFoundException(TEST_NOT_FOUND_MESSAGE));

        this.repository.deleteTest(testId);
        logger.info("Deleted test with id {}", testId);

        return deletedTest;
    }

    private void validateOpenQuestions(Set<Integer> ids) {
        if (ids != null && !ids.isEmpty()) {
            boolean allExist = this.openQuestionRepository.doQuestionsExist(ids);
            if (!allExist) {
                throw new ResourceNotFoundException(OPEN_QUESTIONS_NOT_FOUND_MESSAGE);
            }
        }
    }

    private void validateClosedQuestions(Set<Integer> ids) {
        if (ids != null && !ids.isEmpty()) {
            boolean allExist = this.closedQuestionRepository.doQuestionsExist(ids);
            if (!allExist) {
                throw new ResourceNotFoundException(CLOSED_QUESTIONS_NOT_FOUND_MESSAGE);
            }
        }
    }
}
