package com.lisoft.exagen.domain.templates.repositories;

import com.lisoft.exagen.domain.models.ClosedQuestion;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ClosedQuestionRepository {
    List<ClosedQuestion> getAllClosedQuestionsByUserId(String userId);

    List<ClosedQuestion> getAllClosedQuestionsByCategoryId(Integer categoryId);

    Optional<ClosedQuestion> getClosedQuestionById(Integer id);

    ClosedQuestion createClosedQuestion(ClosedQuestion closedQuestion);

    ClosedQuestion updateClosedQuestionById(ClosedQuestion closedQuestion);

    void deleteClosedQuestionById(Integer id);

    boolean doQuestionsExist(Set<Integer> ids);
}
