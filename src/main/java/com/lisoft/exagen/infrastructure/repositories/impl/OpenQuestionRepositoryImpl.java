package com.lisoft.exagen.infrastructure.repositories.impl;

import com.lisoft.exagen.domain.models.OpenQuestion;
import com.lisoft.exagen.domain.templates.repositories.OpenQuestionRepository;
import com.lisoft.exagen.infrastructure.mappers.OpenQuestionMapper;
import com.lisoft.exagen.infrastructure.repositories.jpa.OpenQuestionJpaRepository;
import com.lisoft.exagen.infrastructure.schemas.OpenQuestionSchema;
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
    public List<OpenQuestion> getAllOpenQuestions() {
        return jpaRepository.findAll()
                .stream()
                .map(OpenQuestionMapper::toModel)
                .toList();
    }

    @Override
    public List<OpenQuestion> getAllOpenQuestionsByUserId(String userId) {
        return jpaRepository.findByUserUserId(userId)
                .stream()
                .map(OpenQuestionMapper::toModel)
                .toList();
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
        return jpaRepository.findById(id)
                .map(OpenQuestionMapper::toModel);
    }

    @Override
    public OpenQuestion createOpenQuestion(OpenQuestion openQuestion) {
        OpenQuestionSchema schema = OpenQuestionMapper.toSchema(openQuestion);
        OpenQuestionSchema saved = jpaRepository.save(schema);
        return OpenQuestionMapper.toModel(saved);
    }

    @Override
    public OpenQuestion updateOpenQuestion(OpenQuestion openQuestion) {
        OpenQuestionSchema schema = OpenQuestionMapper.toSchema(openQuestion);
        OpenQuestionSchema updated = jpaRepository.save(schema);
        return OpenQuestionMapper.toModel(updated);
    }

    @Override
    public OpenQuestion deleteOpenQuestion(Integer id) {
        Optional<OpenQuestionSchema> schemaOpt = jpaRepository.findById(id);
        if (schemaOpt.isPresent()) {
            OpenQuestionSchema schema = schemaOpt.get();
            jpaRepository.delete(schema);
            return OpenQuestionMapper.toModel(schema);
        }
        return null;
    }
}
