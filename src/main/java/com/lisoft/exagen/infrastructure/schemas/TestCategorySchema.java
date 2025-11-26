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
@Entity(name = "testCategory")
@Table(name  = "testCategory", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "user_id"})
})
public class TestCategorySchema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Embedded
    private UserReference user;

    @OneToMany(
            mappedBy = "testCategory",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    private Set<TestSchema> tests = new HashSet<>();

    public void addTest(TestSchema test) {
        this.tests.add(test);
        test.setTestCategory(this);
    }
}
