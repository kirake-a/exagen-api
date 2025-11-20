package com.lisoft.exagen.infrastructure.config;

import com.lisoft.exagen.domain.templates.repositories.TestRepository;
import com.lisoft.exagen.domain.templates.repositories.ClosedQuestionRepository;
import com.lisoft.exagen.domain.templates.repositories.OpenQuestionRepository;
import com.lisoft.exagen.domain.templates.repositories.SurveyRepository;
import com.lisoft.exagen.application.services.TestServiceImpl;
import com.lisoft.exagen.application.services.ClosedQuestionServiceImpl;
import com.lisoft.exagen.application.services.OpenQuestionServiceImpl;
import com.lisoft.exagen.application.services.SurveyServiceImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {
    @Bean
    TestServiceImpl testService(TestRepository testRepository) {
        return new TestServiceImpl(testRepository);
    }

    @Bean
    OpenQuestionServiceImpl openQuestionService(OpenQuestionRepository openQuestionRepository) {
        return new OpenQuestionServiceImpl(openQuestionRepository);
    }

    @Bean
    ClosedQuestionServiceImpl closedQuestionService(ClosedQuestionRepository closedQuestionRepository) {
        return new ClosedQuestionServiceImpl(closedQuestionRepository);
    }

    /*
     * @Bean
     * SurveyServiceImpl surveyService(SurveyRepository surveyRepository) {
     * return new SurveyServiceImpl(surveyRepository);
     * }
     */
}
