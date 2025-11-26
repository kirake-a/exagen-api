package com.lisoft.exagen.infrastructure.repositories.jpa;

import com.lisoft.exagen.infrastructure.schemas.TestCategorySchema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestCategoryJpaRepository extends JpaRepository<TestCategorySchema, Integer> {
    Optional<TestCategorySchema> findByIdAndUserUserId(Integer id, String userId);

    List<TestCategorySchema> findByUserUserId(String userId);
}
