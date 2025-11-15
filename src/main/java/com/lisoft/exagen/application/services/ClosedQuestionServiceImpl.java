package com.lisoft.exagen.application.services;

import com.lisoft.exagen.domain.exceptions.ResourceNotFoundException;
import com.lisoft.exagen.domain.models.ClosedQuestion;
import com.lisoft.exagen.domain.templates.repositories.ClosedQuestionRepository;
import com.lisoft.exagen.domain.templates.services.ClosedQuestionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.lisoft.exagen.domain.utils.Constants.INVALID_ARGUMENT_MESSAGE;
import static com.lisoft.exagen.domain.utils.Constants.USER_ID_CANNOT_BE_NULL_MESSAGE;

public class ClosedQuestionServiceImpl implements ClosedQuestionService {
    private final ClosedQuestionRepository repository;
    private final Logger logger = LoggerFactory.getLogger(ClosedQuestionServiceImpl.class);

    public ClosedQuestionServiceImpl(ClosedQuestionRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClosedQuestion> getAllClosedQuestions() {
        logger.info("Getting all closed questions");
        return repository.getAllClosedQuestions();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClosedQuestion> getAllClosedQuestionsByUserId(String userId) {
        if (Objects.isNull(userId)) {
            String errorMessage = INVALID_ARGUMENT_MESSAGE + USER_ID_CANNOT_BE_NULL_MESSAGE;
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        logger.info("Getting all closed questions by user id {}", userId);
        return repository.getAllClosedQuestionsByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClosedQuestion> getAllClosedQuestionsByCategoryId(String categoryId) {
        if (Objects.isNull(categoryId)) {
            String errorMessage = INVALID_ARGUMENT_MESSAGE + "Category ID cannot be null";
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        logger.info("Getting all closed questions by category id {}", categoryId);
        return repository.getAllClosedQuestionsByCategoryId(Integer.parseInt(categoryId));
    }

    @Override
    @Transactional(readOnly = true)
    public ClosedQuestion getClosedQuestionById(String id) {
        if (Objects.isNull(id)) {
            String errorMessage = INVALID_ARGUMENT_MESSAGE + "ClosedQuestion ID cannot be null";
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        logger.info("Getting closed question by id {}", id);
        return repository.getClosedQuestionById(Integer.parseInt(id))
                .orElseThrow(() -> {
                    String msg = "Closed question with id " + id + " not found";
                    logger.error(msg);
                    return new ResourceNotFoundException(msg);
                });
    }

    @Override
    @Transactional
    public ClosedQuestion createClosedQuestion(ClosedQuestion closedQuestion) {
        if (Objects.isNull(closedQuestion)) {
            String errorMessage = INVALID_ARGUMENT_MESSAGE + "ClosedQuestion object cannot be null";
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        logger.info("Creating new closed question for user {}", closedQuestion.userId());
        ClosedQuestion created = repository.createClosedQuestion(closedQuestion);
        logger.info("Created closed question with ID {}", created.id());
        return created;
    }

    @Override
    @Transactional
    public ClosedQuestion updateClosedQuestion(ClosedQuestion closedQuestion) {
        if (Objects.isNull(closedQuestion) || closedQuestion.id() == null) {
            String errorMessage = INVALID_ARGUMENT_MESSAGE + "ClosedQuestion or ID cannot be null";
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        logger.info("Updating closed question with id {}", closedQuestion.id());
        ClosedQuestion updated = repository.updateClosedQuestion(closedQuestion);
        logger.info("Updated closed question with id {}", updated.id());
        return updated;
    }

    @Override
    @Transactional
    public ClosedQuestion deleteClosedQuestion(Integer closedQuestionId, String userId) {
        if (Objects.isNull(closedQuestionId)) {
            String errorMessage = INVALID_ARGUMENT_MESSAGE + "ClosedQuestion ID cannot be null";
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        if (Objects.isNull(userId)) {
            String errorMessage = INVALID_ARGUMENT_MESSAGE + USER_ID_CANNOT_BE_NULL_MESSAGE;
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        logger.info("Deleting closed question with id {} for user {}", closedQuestionId, userId);
        ClosedQuestion deleted = repository.deleteClosedQuestion(closedQuestionId);
        logger.info("Deleted closed question with id {}", deleted.id());
        return deleted;
    }
}
