package com.lisoft.exagen.infrastructure.mappers;

import com.lisoft.exagen.application.dtos.OpenQuestionDto;
import com.lisoft.exagen.domain.models.OpenQuestion;
import com.lisoft.exagen.infrastructure.schemas.OpenQuestionSchema;

public class OpenQuestionMapper {
    private OpenQuestionMapper() {}

    public static OpenQuestion toModel(OpenQuestionSchema schema) {
        String userId = schema.getUser() != null ? schema.getUser().getUserId() : null;
        Integer categoryId = schema.getCategory() != null ? schema.getCategory().getId() : null;

        return new OpenQuestion(
                schema.getId(),
                schema.getStatement(),
                schema.getResponse(),
                userId,
                categoryId
        );
    }

    public static OpenQuestionDto toOpenQuestionDto(OpenQuestion model) {
        return new OpenQuestionDto(
                model.id(),
                model.statement(),
                model.response()
        );
    }
}
