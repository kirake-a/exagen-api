package com.lisoft.exagen.infrastructure.schemas;

import com.lisoft.exagen.domain.enums.SurveyStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "survey")
public class PublicSurveySchema {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SurveyStatusEnum status;

    @Embedded
    private UserReference user;

    @Column(name = "total_responses")
    private Integer totalResponses;

    @ManyToMany
    @JoinTable(
            name = "survey_closed_questions",
            joinColumns = @JoinColumn(name = "survey_id"),
            inverseJoinColumns = @JoinColumn(name = "closed_question_id")
    )
    private Set<ClosedQuestionSchema> closedQuestions = new HashSet<>();

    @OneToMany(
            mappedBy = "publicSurvey",
            cascade = CascadeType.ALL, fetch = FetchType.LAZY
    )
    private Set<PublicSurveyResponseSchema> responses = new HashSet<>();
}
