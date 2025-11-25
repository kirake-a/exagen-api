package com.lisoft.exagen.domain.templates.services;

import com.lisoft.exagen.application.dtos.TestQuestionsDto;
import com.lisoft.exagen.domain.enums.QuestionTypeEnum;
import com.lisoft.exagen.domain.models.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategoriesByUserId(String userId);

    TestQuestionsDto getQuestions(
            Integer categoryId,
            String userId,
            QuestionTypeEnum type
    );

    Category create(String categoryName, String userId);

    Category delete(Integer categoryId, String userId);
}
