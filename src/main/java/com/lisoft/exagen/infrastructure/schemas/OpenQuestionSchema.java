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
@Entity(name = "open_question")
public class OpenQuestionSchema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "statement", nullable = false, length = 150)
    private String statement;

    @Column(name = "response")
    private String response;

    @Embedded
    private UserReference user;

    @ManyToMany(mappedBy = "openQuestions", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<TestSchema> tests = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategorySchema category;
}
