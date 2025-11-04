package com.lisoft.exagen.infrastructure.repositories.impl;

import com.lisoft.exagen.application.repositories.ClosedQuestionRepository;
import com.lisoft.exagen.domain.models.ClosedQuestion;
import com.lisoft.exagen.infrastructure.repositories.jpa.ClosedQuestionJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClosedQuestionRepositoryImpl implements ClosedQuestionRepository {
    private final ClosedQuestionJpaRepository jpRepository;

    public ClosedQuestionRepositoryImpl(ClosedQuestionJpaRepository jpRepository) {
        this.jpRepository = jpRepository;
    }

    @Override
    public List<ClosedQuestion> getAllClosedQuestions(String userId) {
        return List.of();
    }
}
