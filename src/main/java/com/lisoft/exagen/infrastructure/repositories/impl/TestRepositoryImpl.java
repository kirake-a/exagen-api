package com.lisoft.exagen.infrastructure.repositories.impl;

import com.lisoft.exagen.application.repositories.TestReposity;
import com.lisoft.exagen.domain.models.Test;
import com.lisoft.exagen.infrastructure.repositories.jpa.TestJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TestRepositoryImpl implements TestReposity {
    private final TestJpaRepository jpaRepository;

    public TestRepositoryImpl(TestJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Test> getAllTests() {
        return List.of();
    }
}
