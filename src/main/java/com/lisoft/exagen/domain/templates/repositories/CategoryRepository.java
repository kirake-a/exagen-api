package com.lisoft.exagen.domain.templates.repositories;

import com.lisoft.exagen.domain.models.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    Category save(Category category);

    Optional<Category> getById(Integer id);

    List<Category> findAllByUserId(String userId);

    Optional<Category> findByIdAndUserIdUserId(Integer id, String userId);

    void delete(Integer categoryId);
}
