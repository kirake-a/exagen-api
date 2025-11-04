package com.lisoft.exagen.domain.models;

import java.util.Set;

public record OpenQuestion(
        Integer id,
        String statement,
        String response,
        String userId,
        Integer categoryId
) {}
