package com.lisoft.exagen.application.dtos;

import jakarta.validation.constraints.NotNull;

public record SaveSurveyResponsesDto(
        @NotNull
        Integer questionId,

        @NotNull
        String selectedAnswer
) {}
