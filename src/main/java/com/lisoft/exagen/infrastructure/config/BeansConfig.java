package com.lisoft.exagen.infrastructure.config;

import com.lisoft.exagen.application.services.TestServiceImpl;
import com.lisoft.exagen.domain.templates.repositories.TestReposity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {
    @Bean
    TestServiceImpl testService(TestReposity testRepository) {
        return new TestServiceImpl(testRepository);
    }
}
