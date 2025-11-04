package com.lisoft.exagen.infrastructure.repositories.impl;

import com.lisoft.exagen.application.repositories.OpenQuestionRepository;
import com.lisoft.exagen.domain.models.OpenQuestion;
import com.lisoft.exagen.infrastructure.repositories.jpa.OpenQuestionJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OpenQuestionRepositoryImpl implements OpenQuestionRepository {
    private final OpenQuestionJpaRepository jpaRepository;

    public OpenQuestionRepositoryImpl(OpenQuestionJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<OpenQuestion> getAllOpenQuestions(String userId) {
        return List.of();
    }
}
