package com.lisoft.exagen.application.dtos;

import java.util.Set;

public record SurveyResponseDto(
    String id,
    String title,
    String status,
    Integer totalResponses,
    Set<Integer> questions
) {}
