package com.lisoft.exagen.application.dtos;

import java.util.List;

public record ClosedQuestionRequestDto(
        String statement,
        List<String> options,
        String correctAnswer,
        String userId,
        Integer categoryId) {
}
