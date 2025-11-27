package com.lisoft.exagen.application.services;

import com.lisoft.exagen.domain.exceptions.ResourceNotFoundException;
import com.lisoft.exagen.domain.models.Test;
import com.lisoft.exagen.domain.templates.repositories.TestCategoryRepository;
import com.lisoft.exagen.domain.templates.repositories.TestReposity;
import com.lisoft.exagen.domain.templates.services.TestService;

import com.lisoft.exagen.domain.utils.DataValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.lisoft.exagen.domain.utils.Constants.*;

public class TestServiceImpl implements TestService {
    private final TestReposity repository;
    private final TestCategoryRepository testCategoryRepository;

    private final Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);

    public TestServiceImpl(
            TestReposity repository,
            TestCategoryRepository testCategoryRepository
    ) {
        this.repository = repository;
        this.testCategoryRepository = testCategoryRepository;
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
    public Test getTestById(String testId) {
        if (Objects.isNull(testId)) {
            String errorMessage = INVALID_ARGUMENT_MESSAGE + TEST_ID_CANNOT_BE_NULL_MESSAGE;
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        logger.info("Getting test by id");
        return this.repository.getTestById(testId)
                .orElseThrow(() -> {
                    String errorMessage = "Test with id " + testId + " not found";
                    logger.error(errorMessage);
                    return new ResourceNotFoundException(errorMessage);
                });
    }

    @Override
    @Transactional
    public Test createTest(Test test) {
        if (!this.testCategoryRepository.existsByCategoryId(test.categoryId())) {
            logger.error(CATEGORY_NOT_FOUND_MESSAGE);
            throw new ResourceNotFoundException(CATEGORY_NOT_FOUND_MESSAGE);
        }

        return this.repository.createTest(test);
    }

    @Override
    @Transactional
    public Test deleteTest(String testId, String userId) {
        if (Objects.isNull(userId)) {
            String errorMessage = INVALID_ARGUMENT_MESSAGE + USER_ID_CANNOT_BE_NULL_MESSAGE;
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        if (Objects.isNull(testId)) {
            String errorMessage = INVALID_ARGUMENT_MESSAGE + TEST_ID_CANNOT_BE_NULL_MESSAGE;
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        if (!this.repository.getTestById(testId).isPresent()) {
            String errorMessage = "Test with id " + testId + " not found";
            logger.error(errorMessage);
            throw new ResourceNotFoundException(errorMessage);
        }

        Test deletedTest = this.repository.deleteTest(testId);
        logger.info("Deleted test with id {}", testId);

        return deletedTest;
    }
}
