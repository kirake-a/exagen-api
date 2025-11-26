package com.lisoft.exagen.infrastructure.repositories.jpa;

import com.lisoft.exagen.infrastructure.schemas.TestSchema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestJpaRepository extends JpaRepository<TestSchema, String> {
    List<TestSchema> findByUserUserId(String userId);

    List<TestSchema> findByTestCategoryId(Integer categoryId);
}
