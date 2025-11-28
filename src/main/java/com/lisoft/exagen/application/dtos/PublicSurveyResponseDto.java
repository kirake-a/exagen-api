package com.lisoft.exagen.application.dtos;

import java.util.List;

public record PublicSurveyResponseDto(
        String id,
        String title,
        Integer totalResponses,
        List<ClosedQuestionDto> closedQuestions
) {}
