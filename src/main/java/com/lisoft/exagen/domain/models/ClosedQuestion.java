package com.lisoft.exagen.domain.models;

import java.util.List;
import java.util.Set;

public record ClosedQuestion(
        Integer id,
        String statement,
        List<String> options,
        String correctAnswer,
        String userId,
        Integer categoryId
) {}
