package com.lisoft.exagen.infrastructure.mappers;

import com.lisoft.exagen.application.dtos.TestCategoryResponseDto;
import com.lisoft.exagen.domain.models.TestCategory;
import com.lisoft.exagen.infrastructure.schemas.TestCategorySchema;
import com.lisoft.exagen.infrastructure.schemas.TestSchema;
import com.lisoft.exagen.infrastructure.schemas.UserReference;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class TestCategoryMapper {
    private TestCategoryMapper() {}

    public static TestCategory toModel(TestCategorySchema schema) {
        if (schema == null) return null;

        String userId = schema.getUser() != null ? schema.getUser().getUserId() : null;

        Set<String> tests = schema.getTests() != null ?
                schema.getTests()
                        .stream()
                        .map(TestSchema::getId)
                        .collect(Collectors.toSet()) :
                new HashSet<>();

        return new TestCategory(
                schema.getId(),
                schema.getName(),
                userId,
                tests
        );
    }

    public static TestCategorySchema toSchema(TestCategory model) {
        if (model == null) return null;

        UserReference userReference = model.userId() != null ? new UserReference(model.userId()) : null;

        Set<TestSchema> tests = model.tests() != null ?
                model.tests()
                        .stream()
                        .map(id -> {
                            TestSchema t = new TestSchema();
                            t.setId(id);
                            return t;
                        })
                        .collect(Collectors.toSet()) :
                new HashSet<>();

        return TestCategorySchema.builder()
                .id(model.id())
                .name(model.name())
                .user(userReference)
                .tests(tests)
                .build();
    }

    public static TestCategoryResponseDto toTestCategoryResponseDto(TestCategory model) {
        if (model == null) return null;

        return new TestCategoryResponseDto(
                model.id(),
                model.name()
        );
    }
}
