package com.lisoft.exagen.infrastructure.repositories.jpa;

import com.lisoft.exagen.infrastructure.schemas.ClosedQuestionSchema;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClosedQuestionJpaRepository extends JpaRepository<ClosedQuestionSchema, Integer> {
    List<ClosedQuestionSchema> findByUserUserId(String userId);

    List<ClosedQuestionSchema> findByCategoryId(Integer testId);
}
