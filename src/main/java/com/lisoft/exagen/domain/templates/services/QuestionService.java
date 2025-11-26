package com.lisoft.exagen.domain.templates.services;

import com.lisoft.exagen.application.dtos.TestQuestionsDto;
import com.lisoft.exagen.domain.enums.QuestionTypeEnum;
import com.lisoft.exagen.domain.models.ClosedQuestion;
import com.lisoft.exagen.domain.models.OpenQuestion;

public interface QuestionService {
    TestQuestionsDto getQuestions(
            String userId,
            String statement,
            Integer categoryId,
            Integer questionsAmount,
            QuestionTypeEnum type
    );

    OpenQuestion getOpenQuestion(String userId, Integer questionId);

    ClosedQuestion getClosedQuestion(String userId, Integer questionId);

    String createQuestion(String userId, TestQuestionsDto questions, Integer categoryId);

    String deleteQuestion(String userId, Integer questionId, QuestionTypeEnum questionType);
}
