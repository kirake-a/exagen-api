package com.lisoft.exagen.application.repositories;

import com.lisoft.exagen.domain.models.ClosedQuestion;

import java.util.List;

public interface ClosedQuestionRepository {
    List<ClosedQuestion> getAllClosedQuestions(String userId);
}
