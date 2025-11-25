package com.lisoft.exagen.infrastructure.repositories.impl;

import com.lisoft.exagen.domain.templates.repositories.OpenQuestionRepository;
import com.lisoft.exagen.domain.models.OpenQuestion;
import com.lisoft.exagen.infrastructure.mappers.OpenQuestionMapper;
import com.lisoft.exagen.infrastructure.repositories.jpa.OpenQuestionJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OpenQuestionRepositoryImpl implements OpenQuestionRepository {
    private final OpenQuestionJpaRepository jpaRepository;

    public OpenQuestionRepositoryImpl(OpenQuestionJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<OpenQuestion> getAllOpenQuestionsByUserId(String userId) {
        return List.of();
    }

    @Override
    public List<OpenQuestion> getAllOpenQuestionsByCategoryId(Integer categoryId) {
        return jpaRepository.findByCategoryId(categoryId)
                .stream()
                .map(OpenQuestionMapper::toModel)
                .toList();
    }

    @Override
    public Optional<OpenQuestion> getOpenQuestionById(Integer id) {
        return null;
    }

    @Override
    public OpenQuestion createOpenQuestion(OpenQuestion openQuestion) {
        return null;
    }

    @Override
    public OpenQuestion updateOpenQuestion(OpenQuestion openQuestion) {
        return null;
    }
}
