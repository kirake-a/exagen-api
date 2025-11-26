package com.lisoft.exagen.infrastructure.repositories.impl;

import com.lisoft.exagen.domain.models.Category;
import com.lisoft.exagen.domain.templates.repositories.CategoryRepository;
import com.lisoft.exagen.infrastructure.mappers.CategoryMapper;
import com.lisoft.exagen.infrastructure.repositories.jpa.CategoryJpaRepository;
import com.lisoft.exagen.infrastructure.schemas.CategorySchema;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {
    private final CategoryJpaRepository categoryJpaRepository;

    public CategoryRepositoryImpl(CategoryJpaRepository categoryJpaRepository) {
        this.categoryJpaRepository = categoryJpaRepository;
    }

    @Override
    public Category save(Category category) {
        CategorySchema savedCategory = this.categoryJpaRepository.save(CategoryMapper.toSchema(category));

        return CategoryMapper.toModel(savedCategory);
    }

    @Override
    public Optional<Category> getById(Integer id) {
        return this.categoryJpaRepository.findById(id)
                .map(CategoryMapper::toModel);
    }

    @Override
    public List<Category> findAllByUserId(String userId) {
        return this.categoryJpaRepository.findByUserIdUserId(userId)
                .stream()
                .map(CategoryMapper::toModel)
                .toList();
    }

    @Override
    public Optional<Category> findByIdAndUserIdUserId(Integer id, String userId) {
        return this.categoryJpaRepository.findByIdAndUserIdUserId(id, userId)
                .map(CategoryMapper::toModel);
    }

    @Override
    public void delete(Integer categoryId) {
        this.categoryJpaRepository.deleteById(categoryId);
    }
}
