package com.lisoft.exagen.application.dtos;

import java.util.Set;

public record TestResponseDto(
        String id,
        String title,
        String userId,
        Set<Integer> openQuestionIds,
        Set<Integer> closedQuestionIds,
        Integer categoryId
) {}
