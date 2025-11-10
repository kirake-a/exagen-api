package com.lisoft.exagen.domain.templates.services;

import com.lisoft.exagen.domain.models.ClosedQuestion;

import java.util.List;

public interface ClosedQuestionService {
    List<ClosedQuestion> getAllClosedQuestions();

    List<ClosedQuestion> getAllClosedQuestionsByUserId(String userId);

    List<ClosedQuestion> getAllClosedQuestionsByCategoryId(String categoryId);

    ClosedQuestion getClosedQuestionById(String testId);

    ClosedQuestion createClosedQuestion(ClosedQuestion test);

    ClosedQuestion updateClosedQuestionById(ClosedQuestion closedQuestion);
}