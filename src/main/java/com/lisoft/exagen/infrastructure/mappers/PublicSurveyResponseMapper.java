package com.lisoft.exagen.infrastructure.mappers;

import com.lisoft.exagen.domain.models.PublicSurveyResponse;
import com.lisoft.exagen.infrastructure.schemas.ClosedQuestionSchema;
import com.lisoft.exagen.infrastructure.schemas.PublicSurveyResponseSchema;
import com.lisoft.exagen.infrastructure.schemas.PublicSurveySchema;

public class PublicSurveyResponseMapper {
    public static PublicSurveyResponse toModel(PublicSurveyResponseSchema schema) {
        String publicSurvey = schema.getPublicSurvey() != null ? schema.getPublicSurvey().getId() : "";
        Integer closedQuestion = schema.getClosedQuestion() != null ? schema.getClosedQuestion().getId() : null;

        return new PublicSurveyResponse(
                schema.getId(),
                publicSurvey,
                closedQuestion,
                schema.getSelectedAnswer(),
                schema.getAnsweredAt()
        );
    }

    public static PublicSurveyResponseSchema toSchema(PublicSurveyResponse model) {
        PublicSurveySchema survey = model.surveyId() != null ?
                PublicSurveySchema.builder()
                        .id(model.surveyId())
                        .build() :
                null;

        ClosedQuestionSchema closedQuestion = model.closedQuestionId() != null ?
                ClosedQuestionSchema.builder().id(model.closedQuestionId()).build() :
                null;

        return new PublicSurveyResponseSchema(
                model.id(),
                survey,
                closedQuestion,
                model.selectedAnswer(),
                model.answeredAt()
        );
    }
}
