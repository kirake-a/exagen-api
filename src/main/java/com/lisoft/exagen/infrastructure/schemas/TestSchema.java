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
@Entity(name = "test")
public class TestSchema {
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private String id;

        @Column(name = "title", nullable = false, length = 100)
        private String title;

        @Embedded
        private UserReference user;

        @ManyToMany
        @JoinTable(name = "test_open_questions", joinColumns = @JoinColumn(name = "test_id"), inverseJoinColumns = @JoinColumn(name = "open_question_id"))
        @Builder.Default
        private Set<OpenQuestionSchema> openQuestions = new HashSet<>();

        @ManyToMany
        @JoinTable(name = "test_closed_questions", joinColumns = @JoinColumn(name = "test_id"), inverseJoinColumns = @JoinColumn(name = "closed_question_id"))
        @Builder.Default
        private Set<ClosedQuestionSchema> closedQuestions = new HashSet<>();
}
