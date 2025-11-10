package com.lisoft.exagen.infrastructure.mappers;

import com.lisoft.exagen.application.dtos.ClosedQuestionRequestDto;
import com.lisoft.exagen.application.dtos.ClosedQuestionResponseDto;
import com.lisoft.exagen.domain.models.ClosedQuestion;
import com.lisoft.exagen.infrastructure.schemas.CategorySchema;
import com.lisoft.exagen.infrastructure.schemas.ClosedQuestionSchema;
import com.lisoft.exagen.infrastructure.schemas.UserReference;

import java.util.ArrayList;
import java.util.List;

public class ClosedQuestionMapper {

    private ClosedQuestionMapper() {
    }

    public static ClosedQuestion toModel(ClosedQuestionSchema schema) {
        if (schema == null)
            return null;

        String userId = schema.getUser() != null ? schema.getUser().getUserId() : null;
        Integer categoryId = schema.getCategory() != null ? schema.getCategory().getId() : null;

        List<String> options = new ArrayList<>();
        if (schema.getFirstResponse() != null)
            options.add(schema.getFirstResponse());
        if (schema.getSecondResponse() != null)
            options.add(schema.getSecondResponse());
        if (schema.getThirdResponse() != null)
            options.add(schema.getThirdResponse());
        if (schema.getFourthResponse() != null)
            options.add(schema.getFourthResponse());

        return new ClosedQuestion(
                schema.getId(),
                schema.getStatement(),
                options,
                schema.getCorrectAnswer(),
                userId,
                categoryId);
    }

    public static ClosedQuestionSchema toSchema(ClosedQuestion model) {
        if (model == null)
            return null;

        UserReference userReference = model.userId() != null ? new UserReference(model.userId()) : null;

        CategorySchema categorySchema = null;
        if (model.categoryId() != null) {
            categorySchema = new CategorySchema();
            categorySchema.setId(model.categoryId());
        }

        List<String> options = model.options() != null ? model.options() : List.of();

        return ClosedQuestionSchema.builder()
                .id(model.id())
                .statement(model.statement())
                .firstResponse(options.size() > 0 ? options.get(0) : null)
                .secondResponse(options.size() > 1 ? options.get(1) : null)
                .thirdResponse(options.size() > 2 ? options.get(2) : null)
                .fourthResponse(options.size() > 3 ? options.get(3) : null)
                .correctAnswer(model.correctAnswer())
                .user(userReference)
                .category(categorySchema)
                .build();
    }

    public static ClosedQuestion toModel(ClosedQuestionRequestDto dto) {
        if (dto == null)
            return null;

        return new ClosedQuestion(
                null,
                dto.statement(),
                dto.options(),
                dto.correctAnswer(),
                dto.userId(),
                dto.categoryId());
    }

    public static ClosedQuestionResponseDto toResponseDto(ClosedQuestion model) {
        if (model == null)
            return null;

        return new ClosedQuestionResponseDto(
                model.id(),
                model.statement(),
                model.options(),
                model.correctAnswer(),
                model.userId(),
                model.categoryId());
    }

    public static List<ClosedQuestionResponseDto> toResponseDtoList(List<ClosedQuestion> questions) {
        return questions == null ? List.of()
                : questions.stream()
                        .map(ClosedQuestionMapper::toResponseDto)
                        .toList();
    }

    public static List<ClosedQuestion> toModelList(List<ClosedQuestionSchema> schemas) {
        return schemas == null ? List.of()
                : schemas.stream()
                        .map(ClosedQuestionMapper::toModel)
                        .toList();
    }
}
