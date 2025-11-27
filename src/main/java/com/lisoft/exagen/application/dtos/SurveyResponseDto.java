package com.lisoft.exagen.application.dtos;

import java.util.List;

public record SurveyResponseDto(
    String id,
    String title,
    Integer totalResponses,
    List<ClosedQuestionDto> questions
) {}
