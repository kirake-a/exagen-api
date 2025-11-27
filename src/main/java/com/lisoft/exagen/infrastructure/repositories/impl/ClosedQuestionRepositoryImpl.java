package com.lisoft.exagen.infrastructure.repositories.impl;

import com.lisoft.exagen.domain.templates.repositories.ClosedQuestionRepository;
import com.lisoft.exagen.domain.models.ClosedQuestion;
import com.lisoft.exagen.infrastructure.mappers.ClosedQuestionMapper;
import com.lisoft.exagen.infrastructure.repositories.jpa.ClosedQuestionJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class ClosedQuestionRepositoryImpl implements ClosedQuestionRepository {
    private final ClosedQuestionJpaRepository jpaRepository;

    public ClosedQuestionRepositoryImpl(ClosedQuestionJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<ClosedQuestion> getAllClosedQuestionsByUserId(String userId) {
        return this.jpaRepository.findByUserUserId(userId)
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
        return this.jpaRepository.findById(id)
                .map(ClosedQuestionMapper::toModel);
    }

    @Override
    public ClosedQuestion createClosedQuestion(ClosedQuestion closedQuestion) {
        var entity = ClosedQuestionMapper.toSchema(closedQuestion);
        var savedEntity = this.jpaRepository.save(entity);

        return ClosedQuestionMapper.toModel(savedEntity);
    }

    @Override
    public ClosedQuestion updateClosedQuestionById(ClosedQuestion closedQuestion) {
        return null;
    }

    @Override
    public void deleteClosedQuestionById(Integer id) {
        this.jpaRepository.deleteById(id);
    }

    @Override
    public boolean doQuestionsExist(Set<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return true;
        }
        long count = this.jpaRepository.countByIdIn(ids);
        return count == ids.size();
    }
}
