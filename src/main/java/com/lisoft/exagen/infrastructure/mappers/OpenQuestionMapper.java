package com.lisoft.exagen.infrastructure.mappers;

import com.lisoft.exagen.application.dtos.OpenQuestionDto;
import com.lisoft.exagen.domain.models.OpenQuestion;
import com.lisoft.exagen.infrastructure.schemas.CategorySchema;
import com.lisoft.exagen.infrastructure.schemas.OpenQuestionSchema;
import com.lisoft.exagen.infrastructure.schemas.UserReference;

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

    public static OpenQuestionSchema toSchema(OpenQuestion model) {
        if (model == null) return null;

        UserReference user = model.userId() != null ? new UserReference(model.userId()) : null;
        CategorySchema category = null;

        if (model.categoryId() != null) {
            category = CategorySchema.builder()
                    .id(model.categoryId())
                    .build();
        }

        return OpenQuestionSchema.builder()
                .id(model.id())
                .statement(model.statement())
                .response(model.response())
                .user(user)
                .category(category)
                .build();
    }

    public static OpenQuestionDto toOpenQuestionDto(OpenQuestion model) {
        return new OpenQuestionDto(
                model.id(),
                model.statement(),
                model.response(),
                model.categoryId()
        );
    }

    public static OpenQuestion openQuestionDtoToModel(OpenQuestionDto dto, String userId, Integer categoryId) {
        return new OpenQuestion(
                dto.id(),
                dto.statement(),
                dto.response(),
                userId,
                categoryId
        );
    }
}
