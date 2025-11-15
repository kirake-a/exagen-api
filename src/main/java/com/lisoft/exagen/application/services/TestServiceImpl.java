package com.lisoft.exagen.application.services;

import com.lisoft.exagen.domain.exceptions.ResourceNotFoundException;
import com.lisoft.exagen.domain.models.Test;
import com.lisoft.exagen.domain.templates.repositories.TestRepository;
import com.lisoft.exagen.domain.templates.services.TestService;
import static com.lisoft.exagen.domain.utils.Constants.INVALID_ARGUMENT_MESSAGE;
import static com.lisoft.exagen.domain.utils.Constants.USER_ID_CANNOT_BE_NULL_MESSAGE;
import static com.lisoft.exagen.domain.utils.Constants.TEST_ID_CANNOT_BE_NULL_MESSAGE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

public class TestServiceImpl implements TestService {
    private final TestRepository repository;

    private final Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);

    public TestServiceImpl(TestRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Test> getAllTests() {
        logger.info("Getting all tests");
        return this.repository.getAllTests();
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
        logger.info("Creating new test");
        Test createdTest = this.repository.createTest(test);
        logger.info("Created test with id {}", createdTest.id());

        return createdTest;
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

    @Override
    @Transactional
    public Test updateTest(String testId, Test updatedTest) {
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

        Test test = this.repository.updateTest(testId, updatedTest);
        logger.info("Updated test with id {}", testId);

        return test;
    }
}
