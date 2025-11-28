package com.lisoft.exagen.domain.models;

import java.util.Set;

public record Test(
        String id,
        String title,
        String userId,
        Set<Integer> openQuestionIds,
        Set<Integer> closedQuestionIds,
        Integer categoryId
) {}
