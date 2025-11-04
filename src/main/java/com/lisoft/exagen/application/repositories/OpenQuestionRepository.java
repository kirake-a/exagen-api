package com.lisoft.exagen.application.repositories;

import com.lisoft.exagen.domain.models.OpenQuestion;

import java.util.List;

public interface OpenQuestionRepository {
    List<OpenQuestion> getAllOpenQuestions(String userId);
}
