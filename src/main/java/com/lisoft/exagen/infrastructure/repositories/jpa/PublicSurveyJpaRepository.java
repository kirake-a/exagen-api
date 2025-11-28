package com.lisoft.exagen.infrastructure.repositories.jpa;

import com.lisoft.exagen.infrastructure.schemas.PublicSurveySchema;
import com.lisoft.exagen.infrastructure.schemas.UserReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicSurveyJpaRepository extends JpaRepository<PublicSurveySchema, String> {
    List<PublicSurveySchema> findByUserUserId(String userId);

    String user(UserReference user);
}
