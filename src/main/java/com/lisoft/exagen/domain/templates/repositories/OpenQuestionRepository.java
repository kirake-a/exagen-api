package com.lisoft.exagen.domain.templates.repositories;

import com.lisoft.exagen.domain.models.OpenQuestion;

import java.util.List;
import java.util.Optional;

public interface OpenQuestionRepository {
    List<OpenQuestion> getAllOpenQuestions();

    List<OpenQuestion> getAllOpenQuestionsByUserId(String userId);

    List<OpenQuestion> getAllOpenQuestionsByCategoryId(Integer categoryId);

    Optional<OpenQuestion> getOpenQuestionById(Integer id);

    OpenQuestion createOpenQuestion(OpenQuestion openQuestion);

    OpenQuestion updateOpenQuestion(OpenQuestion openQuestion);

    OpenQuestion deleteOpenQuestion(Integer id);
}
