package com.lisoft.exagen.infrastructure.repositories.impl;

import com.lisoft.exagen.domain.exceptions.ResourceNotFoundException;
import com.lisoft.exagen.domain.models.PublicSurvey;
import com.lisoft.exagen.domain.models.PublicSurveyResponse;
import com.lisoft.exagen.domain.templates.repositories.PublicSurveyRepository;
import com.lisoft.exagen.infrastructure.mappers.PublicSurveyMapper;
import com.lisoft.exagen.infrastructure.repositories.jpa.PublicSurveyJpaRepository;
import com.lisoft.exagen.infrastructure.schemas.PublicSurveySchema;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.lisoft.exagen.domain.utils.Constants.SURVEY_NOT_FOUND_MESSAGE;

@Repository
public class PublicSurveyRepositoryImpl implements PublicSurveyRepository {
    private final PublicSurveyJpaRepository jpaRepository;

    public PublicSurveyRepositoryImpl(PublicSurveyJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<PublicSurvey> findAllByUserId(String userId) {
        return this.jpaRepository.findByUserUserId(userId)
                .stream()
                .map(PublicSurveyMapper::toModel)
                .toList();
    }

    @Override
    public Optional<PublicSurvey> findSurveyId(String surveyId) {
        return this.jpaRepository.findById(surveyId)
                .map(PublicSurveyMapper::toModel);
    }

    @Override
    public PublicSurvey savePublicSurvey(PublicSurvey survey) {
        PublicSurveySchema createdSurvey = this.jpaRepository.save(
                PublicSurveyMapper.toSchema(survey)
        );

        return PublicSurveyMapper.toModel(createdSurvey);
    }

    @Override
    @Transactional
    public PublicSurvey updateTotalResponsesByOne(String surveyId) {
        PublicSurveySchema schema = this.jpaRepository.findById(surveyId)
                .orElseThrow(() -> new ResourceNotFoundException(SURVEY_NOT_FOUND_MESSAGE));

        int totalResponses = schema.getTotalResponses() == null ? 0 : schema.getTotalResponses();
        schema.setTotalResponses(totalResponses + 1);

        PublicSurveySchema updatedSchema = this.jpaRepository.save(schema);

        return PublicSurveyMapper.toModel(updatedSchema);
    }

    @Override
    public void deleteSurveyById(String surveyId) {
        this.jpaRepository.deleteById(surveyId);
    }
}
