package com.lisoft.exagen.domain.models;

import java.util.Set;

public record PublicSurvey(
        String id,
        String title,
        String status,
        String userId,
        Integer totalResponses,
        Set<Integer> closedQuestions,
        Set<String> responses
) {}
