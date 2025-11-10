package com.lisoft.exagen.application.dtos;

import java.util.List;

public record ClosedQuestionResponseDto(
        Integer id,
        String statement,
        List<String> options,
        String correctAnswer,
        String userId,
        Integer categoryId) {
}
