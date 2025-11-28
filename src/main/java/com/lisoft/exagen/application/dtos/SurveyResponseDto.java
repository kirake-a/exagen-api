package com.lisoft.exagen.application.dtos;

import java.util.Set;

public record SurveyResponseDto(
    String id,
    String title,
    Integer totalResponses,
    Set<Integer> questions
) {}
