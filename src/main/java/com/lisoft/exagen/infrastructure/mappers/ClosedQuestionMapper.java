package com.lisoft.exagen.infrastructure.mappers;

import com.lisoft.exagen.application.dtos.ClosedQuestionDto;
import com.lisoft.exagen.domain.models.ClosedQuestion;
import com.lisoft.exagen.infrastructure.schemas.ClosedQuestionSchema;

import java.util.ArrayList;
import java.util.List;

public class ClosedQuestionMapper {
    private ClosedQuestionMapper() {}

    public static ClosedQuestion toModel(ClosedQuestionSchema schema) {
        List<String> options = new ArrayList<>();
        options.add(schema.getFirstResponse());
        options.add(schema.getSecondResponse());
        options.add(schema.getThirdResponse());
        options.add(schema.getFourthResponse());

        String userId = schema.getUser() != null ? schema.getUser().getUserId() : null;
        Integer categoryId = schema.getCategory() != null ? schema.getCategory().getId() : null;

        return new ClosedQuestion(
                schema.getId(),
                schema.getStatement(),
                options,
                schema.getCorrectAnswer(),
                userId,
                categoryId
        );
    }

    public static ClosedQuestionDto toClosedQuestionDto(ClosedQuestion model) {
        return new  ClosedQuestionDto(
                model.id(),
                model.statement(),
                model.options(),
                model.correctAnswer()
        );
    }
}
