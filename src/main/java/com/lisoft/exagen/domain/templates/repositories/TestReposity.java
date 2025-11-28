package com.lisoft.exagen.domain.templates.repositories;

import com.lisoft.exagen.domain.models.Test;

import java.util.List;
import java.util.Optional;

public interface TestReposity {
    List<Test> getAllTests();

    List<Test> getAllTestsByUserId(String userId);

    Optional<Test> getTestById(String id);

    List<Test> getAllTestsByCategoryId(Integer categoryId);

    Test createTest(Test test);

    void deleteTest(String id);
}
