package com.lisoft.exagen.domain.templates.repositories;

import com.lisoft.exagen.domain.models.PublicSurveyResponse;

import java.util.List;

public interface PublicSurveyResponseRepository {
    List<PublicSurveyResponse> findAllSurveyResponsesById(String surveyId);

    PublicSurveyResponse savePublicSurveyResponse(PublicSurveyResponse surveyResponse);
}
