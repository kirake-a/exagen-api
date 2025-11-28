package com.lisoft.exagen.infrastructure.repositories.impl;

import com.lisoft.exagen.domain.models.PublicSurveyResponse;
import com.lisoft.exagen.domain.templates.repositories.PublicSurveyResponseRepository;
import com.lisoft.exagen.infrastructure.mappers.PublicSurveyResponseMapper;
import com.lisoft.exagen.infrastructure.repositories.jpa.PublicSurveyResponseJpaRepository;
import com.lisoft.exagen.infrastructure.schemas.PublicSurveyResponseSchema;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PublicSurveyResponseRepositoryImpl implements PublicSurveyResponseRepository {
    private final PublicSurveyResponseJpaRepository publicSurveyResponseJpaRepository;

    public  PublicSurveyResponseRepositoryImpl(
            PublicSurveyResponseJpaRepository publicSurveyResponseJpaRepository
    ) {
        this.publicSurveyResponseJpaRepository = publicSurveyResponseJpaRepository;
    }

    @Override
    public List<PublicSurveyResponse> findAllSurveyResponsesById(String surveyId) {
        return this.publicSurveyResponseJpaRepository.findByPublicSurveyId(surveyId)
                .stream()
                .map(PublicSurveyResponseMapper::toModel)
                .toList();
    }

    @Override
    public PublicSurveyResponse savePublicSurveyResponse(PublicSurveyResponse surveyResponse) {
        PublicSurveyResponseSchema createResponse = this.publicSurveyResponseJpaRepository.save(
                PublicSurveyResponseMapper.toSchema(surveyResponse)
        );

        return PublicSurveyResponseMapper.toModel(createResponse);
    }
}
