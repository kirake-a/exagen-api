package com.lisoft.exagen.domain.templates.services;

import com.lisoft.exagen.domain.models.OpenQuestion;

import java.util.List;

public interface OpenQuestionService {
    List<OpenQuestion> getAllOpenQuestions();

    List<OpenQuestion> getAllOpenQuestionsByUserId(String userId);

    List<OpenQuestion> getAllOpenQuestionsByCategoryId(String categoryId);

    OpenQuestion getOpenQuestionById(String testId);

    OpenQuestion createOpenQuestion(OpenQuestion test);

    OpenQuestion updateOpenQuestion(OpenQuestion openQuestion);
}
