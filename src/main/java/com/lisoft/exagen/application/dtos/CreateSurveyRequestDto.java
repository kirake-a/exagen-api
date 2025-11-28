package com.lisoft.exagen.application.dtos;

import com.lisoft.exagen.domain.enums.SurveyStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record CreateSurveyRequestDto(
        @NotBlank
        String title,

        @NotNull
        SurveyStatusEnum surveyStatus,

        @NotNull
        Set<Integer> closedQuestionsIds
) {}
