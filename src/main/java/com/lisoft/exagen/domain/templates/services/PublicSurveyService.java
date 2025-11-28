package com.lisoft.exagen.domain.templates.services;

import com.lisoft.exagen.domain.models.PublicSurvey;
import com.lisoft.exagen.domain.models.PublicSurveyResponse;

import java.util.List;

public interface PublicSurveyService {
    List<PublicSurvey> getAllSurveysByUserId(String userId);

    PublicSurvey getSurveyById(String surveyId, String userId);

    List<PublicSurveyResponse> getAllSurveyResponsesBySurveyId(String surveyId, String userId);

    PublicSurvey createSurvey(PublicSurvey survey);

    PublicSurveyResponse createResponse(PublicSurveyResponse surveyResponse);

    PublicSurvey deleteSurveyById(String surveyId, String userId);
}
