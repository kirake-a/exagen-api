package com.lisoft.exagen.domain.models;

import java.util.List;

public record ClosedQuestion(
        Integer id,
        String statement,
        List<String> options,
        String correctAnswer,
        String userId,
        Integer categoryId
) {}
