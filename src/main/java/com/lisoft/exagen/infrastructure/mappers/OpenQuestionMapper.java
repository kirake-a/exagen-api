package com.lisoft.exagen.infrastructure.mappers;

import com.lisoft.exagen.application.dtos.OpenQuestionRequestDto;
import com.lisoft.exagen.application.dtos.OpenQuestionResponseDto;
import com.lisoft.exagen.domain.models.OpenQuestion;
import com.lisoft.exagen.infrastructure.schemas.CategorySchema;
import com.lisoft.exagen.infrastructure.schemas.OpenQuestionSchema;
import com.lisoft.exagen.infrastructure.schemas.UserReference;

import java.util.List;

public class OpenQuestionMapper {

    private OpenQuestionMapper() {
    }

    public static OpenQuestion toModel(OpenQuestionSchema schema) {
        if (schema == null)
            return null;

        String userId = schema.getUser() != null ? schema.getUser().getUserId() : null;
        Integer categoryId = schema.getCategory() != null ? schema.getCategory().getId() : null;

        return new OpenQuestion(
                schema.getId(),
                schema.getStatement(),
                schema.getResponse(),
                userId,
                categoryId);
    }

    public static OpenQuestionSchema toSchema(OpenQuestion model) {
        if (model == null)
            return null;

        UserReference userReference = model.userId() != null ? new UserReference(model.userId()) : null;

        CategorySchema categorySchema = null;
        if (model.categoryId() != null) {
            categorySchema = new CategorySchema();
            categorySchema.setId(model.categoryId());
        }

        return OpenQuestionSchema.builder()
                .id(model.id())
                .statement(model.statement())
                .response(model.response())
                .user(userReference)
                .category(categorySchema)
                .build();
    }

    public static OpenQuestion toModel(OpenQuestionRequestDto dto) {
        if (dto == null)
            return null;

        return new OpenQuestion(
                null,
                dto.statement(),
                dto.response(),
                dto.userId(),
                dto.categoryId());
    }

    public static OpenQuestionResponseDto toResponseDto(OpenQuestion model) {
        if (model == null)
            return null;

        return new OpenQuestionResponseDto(
                model.id(),
                model.statement(),
                model.response(),
                model.userId(),
                model.categoryId());
    }

    public static List<OpenQuestionResponseDto> toResponseDtoList(List<OpenQuestion> questions) {
        return questions == null ? List.of()
                : questions.stream()
                        .map(OpenQuestionMapper::toResponseDto)
                        .toList();
    }

    public static List<OpenQuestion> toModelList(List<OpenQuestionSchema> schemas) {
        return schemas == null ? List.of()
                : schemas.stream()
                        .map(OpenQuestionMapper::toModel)
                        .toList();
    }
}
