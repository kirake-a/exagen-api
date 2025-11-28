package com.lisoft.exagen.domain.models;

import java.time.LocalDateTime;

public record PublicSurveyResponse(
        String id,
        String surveyId,
        Integer closedQuestionId,
        String selectedAnswer,
        LocalDateTime answeredAt
) {}
