package com.lisoft.exagen.domain.templates.services;

import com.lisoft.exagen.domain.models.Test;

import java.util.List;

public interface TestService {
    List<Test> getAllTests(
            String userId,
            String title,
            Integer categoryId
    );

    List<Test> getAllTestsByUserId(String userId);

    Test getTestById(String testId, String userId);

    Test createTest(Test test);

    Test deleteTest(String testId, String userId);
}
