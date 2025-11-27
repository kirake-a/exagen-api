package com.lisoft.exagen.infrastructure.mappers;

import com.lisoft.exagen.application.dtos.CreateSurveyRequestDto;
import com.lisoft.exagen.domain.enums.SurveyStatusEnum;
import com.lisoft.exagen.domain.models.PublicSurvey;
import com.lisoft.exagen.infrastructure.schemas.ClosedQuestionSchema;
import com.lisoft.exagen.infrastructure.schemas.PublicSurveyResponseSchema;
import com.lisoft.exagen.infrastructure.schemas.PublicSurveySchema;
import com.lisoft.exagen.infrastructure.schemas.UserReference;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class PublicSurveyMapper {
    private PublicSurveyMapper() {}

    public static PublicSurvey toModel(PublicSurveySchema schema) {
        String status = schema.getStatus() != null ? schema.getStatus().getValue() : null;
        String userId = schema.getUser() != null ? schema.getUser().getUserId() : null;

        Set<Integer> closedQuestions = schema.getClosedQuestions() != null ?
                schema.getClosedQuestions()
                        .stream()
                        .map(ClosedQuestionSchema::getId)
                        .collect(Collectors.toSet()) :
                new HashSet<>();

        Set<String> responses = schema.getResponses() != null ?
                schema.getResponses()
                        .stream()
                        .map(PublicSurveyResponseSchema::getId)
                        .collect(Collectors.toSet()) :
                new HashSet<>();

        return new PublicSurvey(
                schema.getId(),
                schema.getTitle(),
                status,
                userId,
                schema.getTotalResponses(),
                closedQuestions,
                responses
        );
    }

    public static PublicSurveySchema toSchema(PublicSurvey model) {
        SurveyStatusEnum status = SurveyStatusEnum.fromValue(model.status());

        UserReference user = model.userId() != null ?
                new UserReference(model.userId()) : null;

        Set<ClosedQuestionSchema> closedQuestions = new HashSet<>();
        if (model.closedQuestions() != null) {
            closedQuestions = model.closedQuestions()
                    .stream()
                    .map(id -> ClosedQuestionSchema.builder()
                            .id(id)
                            .build()
                    )
                    .collect(Collectors.toSet());
        }

        Set<PublicSurveyResponseSchema> responses = new HashSet<>();
        if (model.responses() != null) {
            responses = model.responses()
                    .stream()
                    .map(id -> PublicSurveyResponseSchema.builder()
                            .id(id)
                            .build()
                    )
                    .collect(Collectors.toSet());
        }

        return new PublicSurveySchema(
                model.id(),
                model.title(),
                status,
                user,
                model.totalResponses(),
                closedQuestions,
                responses
        );
    }

    public static PublicSurvey createSurveyRequestDto2Model(
            CreateSurveyRequestDto requestDto,
            String userId
    ) {
        String status = requestDto.surveyStatus().getValue();
        return new PublicSurvey(
                null,
                requestDto.title(),
                status,
                userId,
                0,
                null,
                null
        );
    }
}
