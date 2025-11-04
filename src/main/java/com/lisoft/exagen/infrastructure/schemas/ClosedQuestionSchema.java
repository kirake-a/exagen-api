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
@Entity(name = "closed_question")
public class ClosedQuestionSchema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "statement", nullable = false)
    private String statement;

    @Column(name = "firstResponse")
    private String firstResponse;

    @Column(name = "secondResponse")
    private String secondResponse;

    @Column(name = "thirdResponse")
    private String thirdResponse;

    @Column(name = "fourthResponse")
    private String fourthResponse;

    @Column(name = "correct_answer")
    private String correctAnswer;

    @Embedded
    private UserReference user;

    @ManyToMany(mappedBy = "closedQuestions", fetch = FetchType.LAZY)
    private Set<TestSchema> tests = new HashSet<>();
}
