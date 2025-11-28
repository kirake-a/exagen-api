package com.lisoft.exagen.domain.models;

import java.util.Set;

public record Category(
        Integer id,
        String name,
        String userId,
        Set<Integer> openQuestionIds,
        Set<Integer> closedQuestionIds
) {}
