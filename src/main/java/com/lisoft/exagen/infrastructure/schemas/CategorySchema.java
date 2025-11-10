package com.lisoft.exagen.infrastructure.schemas;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "category")
@Table(name = "category", uniqueConstraints = {
                @UniqueConstraint(columnNames = { "name", "user_id" })
})
public class CategorySchema {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column(name = "name", nullable = false, length = 100)
        private String name;

        @Embedded
        private UserReference userId;

        @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
        @Builder.Default
        private Set<OpenQuestionSchema> openQuestions = new HashSet<>();

        @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
        @Builder.Default
        private Set<ClosedQuestionSchema> closedQuestions = new HashSet<>();
}
