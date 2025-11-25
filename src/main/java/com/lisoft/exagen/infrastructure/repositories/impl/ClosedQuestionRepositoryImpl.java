package com.lisoft.exagen.infrastructure.repositories.impl;

import com.lisoft.exagen.domain.templates.repositories.ClosedQuestionRepository;
import com.lisoft.exagen.domain.models.ClosedQuestion;
import com.lisoft.exagen.infrastructure.mappers.ClosedQuestionMapper;
import com.lisoft.exagen.infrastructure.repositories.jpa.ClosedQuestionJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ClosedQuestionRepositoryImpl implements ClosedQuestionRepository {
    private final ClosedQuestionJpaRepository jpaRepository;

    public ClosedQuestionRepositoryImpl(ClosedQuestionJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<ClosedQuestion> getAllClosedQuestionsByUserId(String userId) {
        return List.of();
    }

    @Override
    public List<ClosedQuestion> getAllClosedQuestionsByCategoryId(Integer categoryId) {
        return jpaRepository.findByCategoryId(categoryId)
                .stream()
                .map(ClosedQuestionMapper::toModel)
                .toList();
    }

    @Override
    public Optional<ClosedQuestion> getClosedQuestionById(Integer id) {
        return null;
    }

    @Override
    public ClosedQuestion createClosedQuestion(ClosedQuestion closedQuestion) {
        return null;
    }

    @Override
    public ClosedQuestion updateClosedQuestionById(ClosedQuestion closedQuestion) {
        return null;
    }
}
