package com.lisoft.exagen.domain.templates.repositories;

import com.lisoft.exagen.domain.models.PublicSurvey;
import com.lisoft.exagen.domain.models.PublicSurveyResponse;

import java.util.List;
import java.util.Optional;

public interface PublicSurveyRepository {
    List<PublicSurvey> findAllByUserId(String userId);

    Optional<PublicSurvey> findSurveyId(String surveyId);

    PublicSurvey savePublicSurvey(PublicSurvey survey);

    PublicSurvey updateTotalResponsesByOne(String surveyId);

    void deleteSurveyById(String surveyId);
}
