package com.lisoft.exagen.infrastructure.repositories.impl;

import com.lisoft.exagen.domain.models.ClosedQuestion;
import com.lisoft.exagen.domain.templates.repositories.ClosedQuestionRepository;
import com.lisoft.exagen.infrastructure.mappers.ClosedQuestionMapper;
import com.lisoft.exagen.infrastructure.repositories.jpa.ClosedQuestionJpaRepository;
import com.lisoft.exagen.infrastructure.schemas.ClosedQuestionSchema;
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
    public List<ClosedQuestion> getAllClosedQuestions() {
        return jpaRepository.findAll()
                .stream()
                .map(ClosedQuestionMapper::toModel)
                .toList();
    }

    @Override
    public List<ClosedQuestion> getAllClosedQuestionsByUserId(String userId) {
        return jpaRepository.findByUserUserId(userId)
                .stream()
                .map(ClosedQuestionMapper::toModel)
                .toList();
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
        return jpaRepository.findById(id)
                .map(ClosedQuestionMapper::toModel);
    }

    @Override
    public ClosedQuestion createClosedQuestion(ClosedQuestion closedQuestion) {
        ClosedQuestionSchema schema = ClosedQuestionMapper.toSchema(closedQuestion);
        ClosedQuestionSchema saved = jpaRepository.save(schema);
        return ClosedQuestionMapper.toModel(saved);
    }

    @Override
    public ClosedQuestion updateClosedQuestion(ClosedQuestion closedQuestion) {
        ClosedQuestionSchema schema = ClosedQuestionMapper.toSchema(closedQuestion);
        ClosedQuestionSchema updated = jpaRepository.save(schema);
        return ClosedQuestionMapper.toModel(updated);
    }

    @Override
    public ClosedQuestion deleteClosedQuestion(Integer id) {
        Optional<ClosedQuestionSchema> schemaOpt = jpaRepository.findById(id);
        if (schemaOpt.isPresent()) {
            ClosedQuestionSchema schema = schemaOpt.get();
            jpaRepository.delete(schema);
            return ClosedQuestionMapper.toModel(schema);
        }
        return null;
    }
}
