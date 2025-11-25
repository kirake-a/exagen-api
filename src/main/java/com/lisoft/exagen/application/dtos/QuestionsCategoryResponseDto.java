package com.lisoft.exagen.application.dtos;

public record QuestionsCategoryResponseDto(
        Integer categoryId,
        String userId,
        TestQuestionsDto questions
) {}
