package com.lisoft.exagen.infrastructure.mappers;

import com.lisoft.exagen.application.dtos.ClosedQuestionDto;
import com.lisoft.exagen.domain.models.ClosedQuestion;
import com.lisoft.exagen.infrastructure.schemas.CategorySchema;
import com.lisoft.exagen.infrastructure.schemas.ClosedQuestionSchema;
import com.lisoft.exagen.infrastructure.schemas.UserReference;

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

    public static ClosedQuestionSchema toSchema(ClosedQuestion model) {
        if (model == null) return null;

        List<String> opts = model.options();
        String firstResponse = (opts != null && opts.size() > 0) ? opts.get(0) : null;
        String secondResponse = (opts != null && opts.size() > 1) ? opts.get(1) : null;
        String thirdResponse = (opts != null && opts.size() > 2) ? opts.get(2) : null;
        String fourthResponse = (opts != null && opts.size() > 3) ? opts.get(3) : null;

        UserReference userReference = model.userId() != null ? new UserReference(model.userId()) : null;
        CategorySchema category = null;

        if (model.categoryId() != null) {
            category = CategorySchema.builder()
                    .id(model.categoryId())
                    .build();
        }

        return ClosedQuestionSchema.builder()
                .id(model.id())
                .statement(model.statement())
                .firstResponse(firstResponse)
                .secondResponse(secondResponse)
                .thirdResponse(thirdResponse)
                .fourthResponse(fourthResponse)
                .correctAnswer(model.correctAnswer())
                .user(userReference)
                .category(category)
                .build();
    }

    public static ClosedQuestionDto toClosedQuestionDto(ClosedQuestion model) {
        return new  ClosedQuestionDto(
                model.id(),
                model.statement(),
                model.options(),
                model.correctAnswer(),
                model.categoryId()
        );
    }

    public static ClosedQuestion closedQuestionDtoToModel(
            ClosedQuestionDto dto,
            String userId,
            Integer categoryId
    ) {
        return new ClosedQuestion(
                dto.id(),
                dto.statement(),
                dto.options(),
                dto.correctAnswer(),
                userId,
                categoryId
        );
    }
}
