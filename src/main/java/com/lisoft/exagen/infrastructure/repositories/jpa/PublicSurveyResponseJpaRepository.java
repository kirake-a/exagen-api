package com.lisoft.exagen.infrastructure.repositories.jpa;

import com.lisoft.exagen.infrastructure.schemas.PublicSurveyResponseSchema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicSurveyResponseJpaRepository extends JpaRepository<PublicSurveyResponseSchema, String> {
    List<PublicSurveyResponseSchema> findByPublicSurveyId(String publicSurveyId);
}
