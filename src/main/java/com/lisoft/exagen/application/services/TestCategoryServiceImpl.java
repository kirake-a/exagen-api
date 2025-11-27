package com.lisoft.exagen.application.services;

import com.lisoft.exagen.domain.exceptions.ResourceNotFoundException;
import com.lisoft.exagen.domain.models.Test;
import com.lisoft.exagen.domain.models.TestCategory;
import com.lisoft.exagen.domain.templates.repositories.TestCategoryRepository;
import com.lisoft.exagen.domain.templates.repositories.TestReposity;
import com.lisoft.exagen.domain.templates.services.TestCategoryService;
import com.lisoft.exagen.domain.utils.DataValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Objects;

public class TestCategoryServiceImpl implements TestCategoryService {
    private final TestCategoryRepository testCategoryRepository;
    private final TestReposity testReposity;

    private static final Logger logger = LoggerFactory.getLogger(TestCategoryServiceImpl.class);

    public  TestCategoryServiceImpl(
            TestCategoryRepository testCategoryRepository,
            TestReposity testReposity
    ) {
        this.testCategoryRepository = testCategoryRepository;
        this.testReposity = testReposity;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TestCategory> getAllTestCategoriesByUserId(String userId) {
        DataValidator.validateUserId(userId);
        logger.info("Getting all test categories by user id {}", userId);

        return this.testCategoryRepository.findAllByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Test> getAllTestsByCategoryId(
            Integer categoryId,
            String userId
    ) {
        if (Objects.isNull(categoryId)) {
            logger.error("Category Id is null");
            throw new InvalidParameterException("Category Id cannot be null");
        }

        TestCategory category = this.testCategoryRepository.findByIdAndUserUserId(categoryId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found or access denied"));

        logger.info("Getting all tests by category id {}", categoryId);

        List<Test> tests = List.of();

        if (category.tests() != null) {
            logger.info("Getting all tests by category id {}", categoryId);
            tests = this.testReposity.getAllTestsByCategoryId(categoryId);
        }

        return tests;
    }

    @Override
    @Transactional
    public TestCategory create(String categoryName, String userId) {
        logger.info("Creating test category {}", categoryName);

        if (Objects.isNull(categoryName) || categoryName.isEmpty()) {
            logger.error("Category name is null or empty");
            throw new InvalidParameterException("Category name is null or empty");
        }

        DataValidator.validateUserId(userId);

        return this.testCategoryRepository.save(
                new TestCategory(
                        null,
                        categoryName,
                        userId,
                        null
                )
        );
    }
}
