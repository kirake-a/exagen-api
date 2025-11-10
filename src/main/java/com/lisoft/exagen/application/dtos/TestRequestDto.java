package com.lisoft.exagen.application.dtos;

import java.util.Set;

public record TestRequestDto(
                String title,
                String userId,
                Set<Integer> openQuestionIds,
                Set<Integer> closedQuestionIds) {
}
