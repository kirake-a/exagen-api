package com.lisoft.exagen.domain.templates.services;

import com.lisoft.exagen.application.dtos.TestResponseDto;
import com.lisoft.exagen.domain.models.TestCategory;

import java.util.List;

public interface TestCategoryService {
    List<TestCategory> getAllTestCategoriesByUserId(String userId);

    List<TestResponseDto> getAllTestsByCategoryId(Integer categoryId);

    TestCategory create(String categoryName, String userId);
}
