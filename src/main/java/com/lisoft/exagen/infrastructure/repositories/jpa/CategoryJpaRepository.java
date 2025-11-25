package com.lisoft.exagen.infrastructure.repositories.jpa;

import com.lisoft.exagen.infrastructure.schemas.CategorySchema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryJpaRepository extends JpaRepository<CategorySchema, Integer> {
    List<CategorySchema> findByUserIdUserId(String userId);

    Optional<CategorySchema> findByIdAndUserIdUserId(Integer id, String userId);
}
