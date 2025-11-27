package com.lisoft.exagen.application.dtos;

import java.time.LocalDateTime;
import java.util.List;

public record SurveyQuestionsDto(
        Integer id,
        LocalDateTime answeredAt,
        List<String> options
) {}
