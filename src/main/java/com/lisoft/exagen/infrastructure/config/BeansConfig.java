package com.lisoft.exagen.infrastructure.config;

import com.lisoft.exagen.application.services.*;
import com.lisoft.exagen.domain.templates.repositories.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {
    @Bean
    TestServiceImpl testService(
            TestReposity testRepository,
            TestCategoryRepository testCategoryRepository,
            OpenQuestionRepository openQuestionRepository,
            ClosedQuestionRepository closedQuestionRepository
    ) {
        return new TestServiceImpl(
                testRepository,
                testCategoryRepository,
                openQuestionRepository,
                closedQuestionRepository
        );
    }

    @Bean
    CategoryServiceImpl categoryService(
            CategoryRepository categoryRepository,
            OpenQuestionRepository openQuestionRepository,
            ClosedQuestionRepository closedQuestionRepository
    ) {
        return new CategoryServiceImpl(
                categoryRepository,
                openQuestionRepository,
                closedQuestionRepository
        );
    }

    @Bean
    QuestionServiceImpl questionService(
            OpenQuestionRepository openQuestionRepository,
            ClosedQuestionRepository closedQuestionRepository,
            CategoryRepository categoryRepository
    ) {
        return new QuestionServiceImpl(
                openQuestionRepository,
                closedQuestionRepository,
                categoryRepository
        );
    }

    @Bean
    TestCategoryServiceImpl testCategoryService(
            TestCategoryRepository testCategoryRepository,
            TestReposity testReposity
    ) {
        return new TestCategoryServiceImpl(
                testCategoryRepository,
                testReposity
        );
    }

    @Bean
    PublicSurveyServiceImpl publicSurveyService(
            PublicSurveyRepository publicSurveyRepository,
            PublicSurveyResponseRepository publicSurveyResponseRepository
    ) {
        return new PublicSurveyServiceImpl(
                publicSurveyRepository,
                publicSurveyResponseRepository
        );
    }
}
