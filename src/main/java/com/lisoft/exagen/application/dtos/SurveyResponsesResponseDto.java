package com.lisoft.exagen.application.dtos;

import java.util.List;

public record SurveyResponsesResponseDto(
        String surveyId,
        String title,
        Integer totalResponses,
        List<SurveyQuestionsDto> responses
) {}
