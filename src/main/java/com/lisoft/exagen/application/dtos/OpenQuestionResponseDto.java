package com.lisoft.exagen.application.dtos;

public record OpenQuestionResponseDto(
        Integer id,
        String statement,
        String response,
        String userId,
        Integer categoryId) {
}
