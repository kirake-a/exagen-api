package com.lisoft.exagen.infrastructure.repositories.impl;

import com.lisoft.exagen.domain.models.TestCategory;
import com.lisoft.exagen.domain.templates.repositories.TestCategoryRepository;
import com.lisoft.exagen.infrastructure.mappers.TestCategoryMapper;
import com.lisoft.exagen.infrastructure.repositories.jpa.TestCategoryJpaRepository;
import com.lisoft.exagen.infrastructure.schemas.TestCategorySchema;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TestCategoryRepositoryImpl implements TestCategoryRepository {
    private final TestCategoryJpaRepository testCategoryJpaRepository;

    public TestCategoryRepositoryImpl(TestCategoryJpaRepository testCategoryJpaRepository) {
        this.testCategoryJpaRepository = testCategoryJpaRepository;
    }

    @Override
    public TestCategory save(TestCategory category) {
        TestCategorySchema savedCategory = this.testCategoryJpaRepository.save(
                TestCategoryMapper.toSchema(category)
        );

        return TestCategoryMapper.toModel(savedCategory);
    }

    @Override
    public List<TestCategory> findAllByUserId(String userId) {
        return this.testCategoryJpaRepository.findByUserUserId(userId)
                .stream()
                .map(TestCategoryMapper::toModel)
                .toList();
    }

    @Override
    public Optional<TestCategory> findByIdAndUserUserId(Integer categoryId, String userId) {
        return this.testCategoryJpaRepository.findByIdAndUserUserId(categoryId, userId)
                .map(TestCategoryMapper::toModel);
    }

    @Override
    public boolean existsByCategoryId(Integer categoryId) {
        if (categoryId == null) return false;

        return this.testCategoryJpaRepository.existsById(categoryId);
    }
}
