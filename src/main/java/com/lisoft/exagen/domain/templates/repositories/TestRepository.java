package com.lisoft.exagen.domain.templates.repositories;

import com.lisoft.exagen.domain.models.Test;

import java.util.List;
import java.util.Optional;

public interface TestRepository {
    List<Test> getAllTests();

    List<Test> getAllTestsByUserId(String userId);

    Optional<Test> getTestById(String id);

    Test createTest(Test test);

    Test deleteTest(String id);

    Test updateTest(String id, Test updatedTest);
}
