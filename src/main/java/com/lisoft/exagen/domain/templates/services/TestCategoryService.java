package com.lisoft.exagen.domain.templates.services;

import com.lisoft.exagen.domain.models.Test;
import com.lisoft.exagen.domain.models.TestCategory;

import java.util.List;

public interface TestCategoryService {
    List<TestCategory> getAllTestCategoriesByUserId(String userId);

    List<Test> getAllTestsByCategoryId(Integer categoryId, String userId);

    TestCategory create(String categoryName, String userId);
}
