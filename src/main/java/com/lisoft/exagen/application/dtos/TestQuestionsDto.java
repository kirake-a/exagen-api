package com.lisoft.exagen.application.dtos;

import java.util.List;

public record TestQuestionsDto(
        List<ClosedQuestionDto> closedQuestions,
        List<OpenQuestionDto> openQuestions
) {}
