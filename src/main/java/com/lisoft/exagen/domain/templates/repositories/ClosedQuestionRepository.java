package com.lisoft.exagen.domain.templates.repositories;

import com.lisoft.exagen.domain.models.ClosedQuestion;

import java.util.List;
import java.util.Optional;

public interface ClosedQuestionRepository {
    List<ClosedQuestion> getAllClosedQuestions();

    List<ClosedQuestion> getAllClosedQuestionsByUserId(String userId);

    List<ClosedQuestion> getAllClosedQuestionsByCategoryId(Integer categoryId);

    Optional<ClosedQuestion> getClosedQuestionById(Integer id);

    ClosedQuestion createClosedQuestion(ClosedQuestion closedQuestion);

    ClosedQuestion updateClosedQuestionById(ClosedQuestion closedQuestion);
}
