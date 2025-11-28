package com.lisoft.exagen.infrastructure.repositories.impl;

import com.lisoft.exagen.domain.templates.repositories.TestReposity;
import com.lisoft.exagen.domain.models.Test;
import com.lisoft.exagen.infrastructure.mappers.TestMapper;
import com.lisoft.exagen.infrastructure.repositories.jpa.TestJpaRepository;
import com.lisoft.exagen.infrastructure.schemas.TestSchema;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TestRepositoryImpl implements TestReposity {
    private final TestJpaRepository jpaRepository;

    public TestRepositoryImpl(TestJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Test> getAllTests() {
        return jpaRepository.findAll()
                .stream()
                .map(TestMapper::toModel)
                .toList();
    }

    @Override
    public List<Test> getAllTestsByUserId(String userId) {
        return jpaRepository.findByUserUserId(userId)
                .stream()
                .map(TestMapper::toModel)
                .toList();
    }

    @Override
    public Optional<Test> getTestById(String id) {
        return jpaRepository.findById(id)
                .map(TestMapper::toModel);
    }

    @Override
    public List<Test> getAllTestsByCategoryId(Integer categoryId) {
        return this.jpaRepository.findByTestCategoryId(categoryId)
                .stream()
                .map(TestMapper::toModel)
                .toList();
    }

    @Override
    public Test createTest(Test test) {
        TestSchema createdTest = this.jpaRepository.save(TestMapper.toSchema(test));

        return TestMapper.toModel(createdTest);
    }

    @Override
    public void deleteTest(String id) {
        this.jpaRepository.deleteById(id);
    }
}
