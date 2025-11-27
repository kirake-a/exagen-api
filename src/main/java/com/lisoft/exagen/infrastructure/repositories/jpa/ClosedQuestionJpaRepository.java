package com.lisoft.exagen.infrastructure.repositories.jpa;

import com.lisoft.exagen.infrastructure.schemas.ClosedQuestionSchema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClosedQuestionJpaRepository extends JpaRepository<ClosedQuestionSchema, Integer> {
    List<ClosedQuestionSchema> findByCategoryId(Integer categoryId);

    List<ClosedQuestionSchema> findByUserUserId(String userId);

    long countByIdIn(Iterable<Integer> ids);
}
