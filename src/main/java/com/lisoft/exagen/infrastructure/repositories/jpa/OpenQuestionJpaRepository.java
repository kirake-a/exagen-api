package com.lisoft.exagen.infrastructure.repositories.jpa;

import com.lisoft.exagen.infrastructure.schemas.OpenQuestionSchema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpenQuestionJpaRepository extends JpaRepository<OpenQuestionSchema, Integer> {}
