package com.lisoft.exagen.application.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record CreateTestRequestDto(
        @NotBlank(message = "Test must have a title")
        String title,

        @NotNull
        Set<Integer> openQuestionIds,

        @NotNull
        Set<Integer> closedQuestionIds,

        @NotNull(message = "Test must be related to a test category")
        Integer categoryId
) {}
