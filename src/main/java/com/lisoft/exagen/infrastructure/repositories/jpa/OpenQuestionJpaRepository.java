package com.lisoft.exagen.infrastructure.repositories.jpa;

import com.lisoft.exagen.infrastructure.schemas.OpenQuestionSchema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpenQuestionJpaRepository extends JpaRepository<OpenQuestionSchema, Integer> {
    List<OpenQuestionSchema> findByCategoryId(Integer categoryId);

    List<OpenQuestionSchema> findByUserUserId(String userId);
}
