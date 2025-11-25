package com.lisoft.exagen.infrastructure.config;

import com.lisoft.exagen.application.services.CategoryServiceImpl;
import com.lisoft.exagen.application.services.TestServiceImpl;
import com.lisoft.exagen.domain.templates.repositories.CategoryRepository;
import com.lisoft.exagen.domain.templates.repositories.ClosedQuestionRepository;
import com.lisoft.exagen.domain.templates.repositories.OpenQuestionRepository;
import com.lisoft.exagen.domain.templates.repositories.TestReposity;
import com.lisoft.exagen.domain.templates.services.CategoryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {
    @Bean
    TestServiceImpl testService(TestReposity testRepository) {
        return new TestServiceImpl(testRepository);
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
}
