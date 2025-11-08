package com.lisoft.exagen.infrastructure.mappers;

import com.lisoft.exagen.application.dtos.TestResponseDto;
import com.lisoft.exagen.domain.models.Test;
import com.lisoft.exagen.infrastructure.schemas.ClosedQuestionSchema;
import com.lisoft.exagen.infrastructure.schemas.OpenQuestionSchema;
import com.lisoft.exagen.infrastructure.schemas.TestSchema;
import com.lisoft.exagen.infrastructure.schemas.UserReference;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TestMapper {
    private TestMapper() {}

    public static Test toModel(TestSchema schema) {
        if (schema == null) return null;

        String userId = schema.getUser() != null ? schema.getUser().getUserId() : null;

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

        return new Test(
                schema.getId(),
                schema.getTitle(),
                userId,
                openQuestions,
                closedQuestions
        );
    }

    public static TestSchema toSchema(Test test) {
        if (test == null) return null;

        UserReference userReference = test.userId() != null ? new UserReference(test.userId()) : null;

        Set<OpenQuestionSchema> openQuestions = test.openQuestionIds() != null ?
                test.openQuestionIds().stream()
                        .map(id -> {
                            OpenQuestionSchema q = new OpenQuestionSchema();
                            q.setId(id);
                            return q;
                        })
                        .collect(Collectors.toSet()) :
                new HashSet<>();

        Set<ClosedQuestionSchema> closedQuestions = test.closedQuestionIds() != null ?
                test.closedQuestionIds().stream()
                        .map(id -> {
                            ClosedQuestionSchema q = new ClosedQuestionSchema();
                            q.setId(id);
                            return q;
                        })
                        .collect(Collectors.toSet()) :
                new HashSet<>();

        return new TestSchema(
                test.id(),
                test.title(),
                userReference,
                openQuestions,
                closedQuestions
        );
    }

    public static TestResponseDto toResponseDto(Test test) {
        return  new TestResponseDto(
                        test.id(),
                        test.title(),
                        test.userId(),
                        test.openQuestionIds(),
                        test.closedQuestionIds());
    }
}
