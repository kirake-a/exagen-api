package com.lisoft.exagen.application.dtos;

public record OpenQuestionRequestDto(
        String statement,
        String response,
        String userId,
        Integer categoryId) {
}
