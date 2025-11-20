package com.lisoft.exagen.application.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lisoft.exagen.domain.templates.repositories.SurveyRepository;
import com.lisoft.exagen.domain.templates.services.SurveyService;

public class SurveyServiceImpl implements SurveyService {
    private final SurveyRepository repository;
    private final Logger logger = LoggerFactory.getLogger(OpenQuestionServiceImpl.class);

    public SurveyServiceImpl(SurveyRepository repository) {
        this.repository = repository;
    }
}
