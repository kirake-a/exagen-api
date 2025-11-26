package com.lisoft.exagen.application.services;

import com.lisoft.exagen.application.dtos.TestResponseDto;
import com.lisoft.exagen.domain.models.TestCategory;
import com.lisoft.exagen.domain.templates.services.TestCategoryService;

import java.util.List;

public class TestCategoryServiceImpl implements TestCategoryService {
    @Override
    public List<TestCategory> getAllTestCategoriesByUserId(String userId) {
        return List.of();
    }

    @Override
    public List<TestResponseDto> getAllTestsByCategoryId(Integer categoryId) {
        return List.of();
    }

    @Override
    public TestCategory create(String categoryName, String userId) {
        return null;
    }
}
