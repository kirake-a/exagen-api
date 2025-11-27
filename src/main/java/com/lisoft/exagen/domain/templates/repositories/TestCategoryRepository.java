package com.lisoft.exagen.domain.templates.repositories;

import com.lisoft.exagen.domain.models.TestCategory;

import java.util.List;
import java.util.Optional;

public interface TestCategoryRepository {
    TestCategory save(TestCategory category);

    List<TestCategory> findAllByUserId(String userId);

    Optional<TestCategory> findByIdAndUserUserId(Integer categoryId, String userId);

    boolean existsByCategoryId(Integer categoryId);
}
