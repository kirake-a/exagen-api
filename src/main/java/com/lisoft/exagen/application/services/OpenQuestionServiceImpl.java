package com.lisoft.exagen.application.services;

import com.lisoft.exagen.domain.exceptions.ResourceNotFoundException;
import com.lisoft.exagen.domain.models.OpenQuestion;
import com.lisoft.exagen.domain.templates.repositories.OpenQuestionRepository;
import com.lisoft.exagen.domain.templates.services.OpenQuestionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.lisoft.exagen.domain.utils.Constants.INVALID_ARGUMENT_MESSAGE;
import static com.lisoft.exagen.domain.utils.Constants.USER_ID_CANNOT_BE_NULL_MESSAGE;

public class OpenQuestionServiceImpl implements OpenQuestionService {
    private final OpenQuestionRepository repository;
    private final Logger logger = LoggerFactory.getLogger(OpenQuestionServiceImpl.class);

    public OpenQuestionServiceImpl(OpenQuestionRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OpenQuestion> getAllOpenQuestions() {
        logger.info("Getting all open questions");
        return repository.getAllOpenQuestions();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OpenQuestion> getAllOpenQuestionsByUserId(String userId) {
        if (Objects.isNull(userId)) {
            String errorMessage = INVALID_ARGUMENT_MESSAGE + USER_ID_CANNOT_BE_NULL_MESSAGE;
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        logger.info("Getting all open questions by user id {}", userId);
        return repository.getAllOpenQuestionsByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OpenQuestion> getAllOpenQuestionsByCategoryId(String categoryId) {
        if (Objects.isNull(categoryId)) {
            String errorMessage = INVALID_ARGUMENT_MESSAGE + "Category ID cannot be null";
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        logger.info("Getting all open questions by category id {}", categoryId);
        return repository.getAllOpenQuestionsByCategoryId(Integer.parseInt(categoryId));
    }

    @Override
    @Transactional(readOnly = true)
    public OpenQuestion getOpenQuestionById(String id) {
        if (Objects.isNull(id)) {
            String errorMessage = INVALID_ARGUMENT_MESSAGE + "OpenQuestion ID cannot be null";
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        logger.info("Getting open question by id {}", id);
        return repository.getOpenQuestionById(Integer.parseInt(id))
                .orElseThrow(() -> {
                    String msg = "Open question with id " + id + " not found";
                    logger.error(msg);
                    return new ResourceNotFoundException(msg);
                });
    }

    @Override
    @Transactional
    public OpenQuestion createOpenQuestion(OpenQuestion openQuestion) {
        if (Objects.isNull(openQuestion)) {
            String errorMessage = INVALID_ARGUMENT_MESSAGE + "OpenQuestion object cannot be null";
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        logger.info("Creating new open question for user {}", openQuestion.userId());
        OpenQuestion created = repository.createOpenQuestion(openQuestion);
        logger.info("Created open question with ID {}", created.id());
        return created;
    }

    @Override
    @Transactional
    public OpenQuestion updateOpenQuestion(OpenQuestion openQuestion) {
        if (Objects.isNull(openQuestion) || openQuestion.id() == null) {
            String errorMessage = INVALID_ARGUMENT_MESSAGE + "OpenQuestion or ID cannot be null";
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        logger.info("Updating open question with id {}", openQuestion.id());
        OpenQuestion updated = repository.updateOpenQuestion(openQuestion);
        logger.info("Updated open question with id {}", updated.id());
        return updated;
    }

    @Override
    @Transactional
    public OpenQuestion deleteOpenQuestion(Integer openQuestionId, String userId) {
        if (Objects.isNull(openQuestionId)) {
            String errorMessage = INVALID_ARGUMENT_MESSAGE + "OpenQuestion ID cannot be null";
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        if (Objects.isNull(userId)) {
            String errorMessage = INVALID_ARGUMENT_MESSAGE + USER_ID_CANNOT_BE_NULL_MESSAGE;
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        logger.info("Deleting open question with id {} for user {}", openQuestionId, userId);
        OpenQuestion deleted = repository.deleteOpenQuestion(openQuestionId);
        logger.info("Deleted open question with id {}", deleted.id());
        return deleted;
    }
}
