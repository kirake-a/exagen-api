package com.lisoft.exagen.infrastructure.repositories.impl;

import com.lisoft.exagen.domain.templates.repositories.TestRepository;
import com.lisoft.exagen.domain.exceptions.ResourceNotFoundException;
import com.lisoft.exagen.domain.models.Test;
import com.lisoft.exagen.infrastructure.mappers.TestMapper;
import com.lisoft.exagen.infrastructure.repositories.jpa.TestJpaRepository;
import com.lisoft.exagen.infrastructure.schemas.TestSchema;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TestRepositoryImpl implements TestRepository {
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
    public Test createTest(Test test) {
        TestSchema entity = TestMapper.toSchema(test);
        TestSchema saved = jpaRepository.save(entity);
        return TestMapper.toModel(saved);
    }

    @Override
    public Test deleteTest(String id) {

        TestSchema existing = jpaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test with id " + id + " not found"));

        jpaRepository.delete(existing);

        return TestMapper.toModel(existing);
    }

    @Override
    public Test updateTest(String id, Test updatedTest) {

        TestSchema existing = jpaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test with id " + id + " not found"));

        TestSchema updatedEntity = TestMapper.toSchema(updatedTest);

        updatedEntity.setId(existing.getId());

        TestSchema saved = jpaRepository.save(updatedEntity);

        return TestMapper.toModel(saved);
    }
}
