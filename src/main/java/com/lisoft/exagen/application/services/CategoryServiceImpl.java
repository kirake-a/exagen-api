package com.lisoft.exagen.application.services;

import com.lisoft.exagen.application.dtos.ClosedQuestionDto;
import com.lisoft.exagen.application.dtos.OpenQuestionDto;
import com.lisoft.exagen.application.dtos.TestQuestionsDto;
import com.lisoft.exagen.domain.enums.QuestionTypeEnum;
import com.lisoft.exagen.domain.exceptions.InvalidArgumentException;
import com.lisoft.exagen.domain.exceptions.ResourceNotFoundException;
import com.lisoft.exagen.domain.models.Category;
import com.lisoft.exagen.domain.models.ClosedQuestion;
import com.lisoft.exagen.domain.models.OpenQuestion;
import com.lisoft.exagen.domain.templates.repositories.CategoryRepository;
import com.lisoft.exagen.domain.templates.repositories.ClosedQuestionRepository;
import com.lisoft.exagen.domain.templates.repositories.OpenQuestionRepository;
import com.lisoft.exagen.domain.templates.services.CategoryService;
import com.lisoft.exagen.domain.utils.DataValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final OpenQuestionRepository openQuestionRepository;
    private final ClosedQuestionRepository closedQuestionRepository;

    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    public CategoryServiceImpl(
            CategoryRepository categoryRepository,
            OpenQuestionRepository openQuestionRepository,
            ClosedQuestionRepository closedQuestionRepository
    ) {
        this.categoryRepository = categoryRepository;
        this.openQuestionRepository = openQuestionRepository;
        this.closedQuestionRepository = closedQuestionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> getAllCategoriesByUserId(String userId) {
        logger.info("Getting all categories by user id {}", userId);

        DataValidator.validateUserId(userId);

        return this.categoryRepository.findAllByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public TestQuestionsDto getQuestions(Integer categoryId, String userId, QuestionTypeEnum type) {
        if (Objects.isNull(categoryId)) {
           logger.error("Category Id can't be null");
           throw new InvalidArgumentException("Category Id can't be null");
        }

        DataValidator.validateUserId(userId);

        Category category = this.categoryRepository.findByIdAndUserIdUserId(categoryId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found or access denied"));

        List<ClosedQuestion> closedQuestions = List.of();
        List<OpenQuestion> openQuestions = List.of();

        boolean includeClosed = type == null || type ==  QuestionTypeEnum.CLOSED;
        boolean includeOpen = type == null || type == QuestionTypeEnum.OPEN;

        if (includeClosed && category.closedQuestionIds() != null) {
            logger.info("Getting closed questions by user id {}", userId);
            closedQuestions = this.closedQuestionRepository.getAllClosedQuestionsByCategoryId(categoryId);
        }

        if (includeOpen && category.openQuestionIds() != null) {
            logger.info("Getting open questions by user id {}", userId);
            openQuestions = this.openQuestionRepository.getAllOpenQuestionsByCategoryId(categoryId);
        }

        return new TestQuestionsDto(
                closedQuestions.stream()
                        .map(q -> new ClosedQuestionDto(
                                q.id(),
                                q.statement(),
                                q.options(),
                                q.correctAnswer()
                        ))
                        .toList(),
                openQuestions.stream()
                        .map(q -> new OpenQuestionDto(
                                q.id(),
                                q.statement(),
                                q.response()
                        ))
                        .toList()
        );
    }

    @Override
    @Transactional
    public Category create(String categoryName, String userId) {
        logger.info("Creating Category {}", categoryName);

        if (Objects.isNull(categoryName) || categoryName.isEmpty()) {
            String message  = "Category name is null or empty";
            logger.error(message);
            throw new InvalidArgumentException(message);
        }

        DataValidator.validateUserId(userId);

        return this.categoryRepository.save(new Category(
                null,
                categoryName,
                userId,
                null,
                null
        ));
    }

    @Override
    @Transactional
    public Category delete(Integer categoryId, String userId) {
        logger.info("Starting Category elimination: {}", categoryId);

        DataValidator.validateUserId(userId);

        if (Objects.isNull(categoryId)) {
            logger.error("Invalid category id provided");
            throw new InvalidArgumentException("Invalid category id provided");
        }

        Category category2Delete = this.categoryRepository.findByIdAndUserIdUserId(categoryId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found or access denied"));

        this.categoryRepository.delete(categoryId);

        logger.info("Category {} deleted successfully by user {}", categoryId, userId);

        return category2Delete;
    }
}
