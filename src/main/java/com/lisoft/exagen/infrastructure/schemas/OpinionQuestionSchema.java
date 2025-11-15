package com.lisoft.exagen.infrastructure.schemas;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "survey_question")
public class OpinionQuestionSchema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "statement", nullable = false, length = 200)
    private String statement;

    @Column(name = "type", nullable = false, length = 30)
    private String type;
    // text, textarea, single-choice, multi-choice, rating

    @ElementCollection
    @CollectionTable(name = "survey_question_options", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "option_value")
    private Set<String> options;

    @Embedded
    private UserReference user;
}
