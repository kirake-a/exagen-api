package com.lisoft.exagen.infrastructure.mappers;

import com.lisoft.exagen.application.dtos.CategoryResponseDto;
import com.lisoft.exagen.domain.models.Category;
import com.lisoft.exagen.infrastructure.schemas.CategorySchema;
import com.lisoft.exagen.infrastructure.schemas.ClosedQuestionSchema;
import com.lisoft.exagen.infrastructure.schemas.OpenQuestionSchema;
import com.lisoft.exagen.infrastructure.schemas.UserReference;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CategoryMapper {
    private CategoryMapper() {}

    public static Category toModel(CategorySchema schema) {
        if (schema == null) return null;

        String userId = schema.getUserId() != null ? schema.getUserId().getUserId() : null;

        Set<Integer> openQuestions = schema.getOpenQuestions() != null ?
                schema.getOpenQuestions()
                        .stream()
                        .map(OpenQuestionSchema::getId)
                        .collect(Collectors.toSet()) :
                new HashSet<>();

        Set<Integer> closedQuestions = schema.getClosedQuestions() != null ?
                schema.getClosedQuestions()
                        .stream()
                        .map(ClosedQuestionSchema::getId)
                        .collect(Collectors.toSet()) :
                new HashSet<>();

        return new Category(
                schema.getId(),
                schema.getName(),
                userId,
                openQuestions,
                closedQuestions
        );
    }

    public static CategorySchema toSchema(Category category) {
        UserReference userReference = category.userId() != null ? new UserReference(category.userId()) : null;

        Set<OpenQuestionSchema> openQuestions = category.openQuestionIds() != null ?
                category.openQuestionIds().stream()
                        .map(id -> {
                            OpenQuestionSchema q = new OpenQuestionSchema();
                            q.setId(id);
                            return q;
                        })
                        .collect(Collectors.toSet()) :
                new HashSet<>();

        Set<ClosedQuestionSchema> closedQuestions = category.closedQuestionIds() != null ?
                category.closedQuestionIds().stream()
                        .map(id -> {
                            ClosedQuestionSchema q = new ClosedQuestionSchema();
                            q.setId(id);
                            return q;
                        })
                        .collect(Collectors.toSet()) :
                new HashSet<>();

        return new CategorySchema(
                category.id(),
                category.name(),
                userReference,
                openQuestions,
                closedQuestions
        );
    }

    public static CategoryResponseDto toCategoryResponseDto(Category category) {
        return new CategoryResponseDto(
                category.id(),
                category.name()
        );
    }
}
