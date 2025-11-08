package com.lisoft.exagen.application.services;

import com.lisoft.exagen.domain.exceptions.ResourceNotFoundException;
import com.lisoft.exagen.domain.models.Test;
import com.lisoft.exagen.domain.templates.repositories.TestReposity;
import com.lisoft.exagen.domain.templates.services.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class TestServiceImpl implements TestService {
    private final TestReposity repository;

    private final Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);

    public TestServiceImpl(TestReposity repository) {
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
        logger.info("Getting all tests by user id");
        return this.repository.getAllTestsByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Test getTestById(String testId) {
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
        return null;
    }

    @Override
    @Transactional
    public Test deleteTest(String testId) {
        return null;
    }
}
