package com.lisoft.exagen.application.services;

import com.lisoft.exagen.domain.exceptions.InvalidArgumentException;
import com.lisoft.exagen.domain.exceptions.ResourceNotFoundException;
import com.lisoft.exagen.domain.exceptions.UnauthorizedAccessException;
import com.lisoft.exagen.domain.models.PublicSurvey;
import com.lisoft.exagen.domain.models.PublicSurveyResponse;
import com.lisoft.exagen.domain.templates.repositories.PublicSurveyRepository;
import com.lisoft.exagen.domain.templates.repositories.PublicSurveyResponseRepository;
import com.lisoft.exagen.domain.templates.services.PublicSurveyService;
import com.lisoft.exagen.domain.utils.DataValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.lisoft.exagen.domain.utils.Constants.*;

public class PublicSurveyServiceImpl implements PublicSurveyService {
    private final PublicSurveyRepository publicSurveyRepository;
    private final PublicSurveyResponseRepository publicSurveyResponseRepository;

    private final Logger logger = LoggerFactory.getLogger(PublicSurveyServiceImpl.class);

    public PublicSurveyServiceImpl(
            PublicSurveyRepository publicSurveyRepository,
            PublicSurveyResponseRepository publicSurveyResponseRepository
    ) {
        this.publicSurveyRepository = publicSurveyRepository;
        this.publicSurveyResponseRepository = publicSurveyResponseRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PublicSurvey> getAllSurveysByUserId(String userId) {
        DataValidator.validateUserId(userId);

        logger.info("Getting all surveys by user id");
        return this.publicSurveyRepository.findAllByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public PublicSurvey getSurveyById(String surveyId, String userId) {
        DataValidator.validateUserId(userId);
        DataValidator.validateNoEmptyString("surveyId", surveyId);

        PublicSurvey survey = this.publicSurveyRepository.findSurveyId(surveyId)
                .orElseThrow(() -> new ResourceNotFoundException(SURVEY_NOT_FOUND_MESSAGE));

        if (!survey.userId().equals(userId)) {
            throw new UnauthorizedAccessException(UNAUTHORIZED_ACCESS_2_SURVEY);
        }

        return survey;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PublicSurveyResponse> getAllSurveyResponsesBySurveyId(String surveyId, String userId) {
        return List.of();
    }

    @Override
    @Transactional
    public PublicSurvey createSurvey(PublicSurvey survey) {
        if (Objects.isNull(survey)) {
            throw new InvalidArgumentException(SURVEY_OBJECT_IS_NULL);
        }

        logger.info("Creating survey");
        return this.publicSurveyRepository.savePublicSurvey(survey);
    }

    @Override
    @Transactional
    public PublicSurveyResponse createResponse(PublicSurveyResponse surveyResponse) {
        if (Objects.isNull(surveyResponse)) {
            throw new InvalidArgumentException(SURVEY_RESPONSE_CANNOT_BE_NUL_MESSAGE);
        }

        this.publicSurveyRepository.findSurveyId(surveyResponse.surveyId())
                .orElseThrow(() -> new ResourceNotFoundException(SURVEY_NOT_FOUND_MESSAGE));

        logger.info("Creating survey response");
        return this.publicSurveyResponseRepository.savePublicSurveyResponse(surveyResponse);
    }

    @Override
    @Transactional
    public PublicSurvey deleteSurveyById(String surveyId, String userId) {
        DataValidator.validateUserId(userId);
        DataValidator.validateNoEmptyString("surveyId", surveyId);

        PublicSurvey deletedSurvey = this.publicSurveyRepository.findSurveyId(surveyId)
                .orElseThrow(() -> new ResourceNotFoundException(SURVEY_NOT_FOUND_MESSAGE));

        if (!deletedSurvey.userId().equals(userId)) {
            throw new UnauthorizedAccessException(UNAUTHORIZED_ACCESS_2_SURVEY);
        }

        this.publicSurveyRepository.deleteSurveyById(surveyId);

        return deletedSurvey;
    }
}
