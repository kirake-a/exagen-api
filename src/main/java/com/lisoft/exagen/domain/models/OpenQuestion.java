package com.lisoft.exagen.domain.models;

public record OpenQuestion(
        Integer id,
        String statement,
        String response,
        String userId
) {}
