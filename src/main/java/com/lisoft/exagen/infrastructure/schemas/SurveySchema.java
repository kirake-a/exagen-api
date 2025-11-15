package com.lisoft.exagen.infrastructure.schemas;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "survey")
public class SurveySchema {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "description", length = 250)
    private String description;

    @Embedded
    private UserReference user;

    @ManyToMany
    @JoinTable(name = "survey_questions", joinColumns = @JoinColumn(name = "survey_id"), inverseJoinColumns = @JoinColumn(name = "question_id"))
    @Builder.Default
    private Set<OpinionQuestionSchema> questions = new HashSet<>();
}
