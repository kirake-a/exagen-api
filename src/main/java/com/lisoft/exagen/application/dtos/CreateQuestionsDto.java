package com.lisoft.exagen.application.dtos;

public record CreateQuestionsDto(
        Integer categoryId,
        TestQuestionsDto questions
) {}
