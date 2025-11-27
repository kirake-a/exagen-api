package com.lisoft.exagen.application.dtos;

import java.util.List;

public record ClosedQuestionDto(
        Integer id,
        String statement,
        List<String> options,
        String correctAnswer,
        Integer categoryId
) {}
