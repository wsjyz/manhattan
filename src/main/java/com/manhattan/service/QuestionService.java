package com.manhattan.service;

import com.manhattan.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Administrator on 2014/5/23 0023.
 */
public interface QuestionService {
    Question saveQuestion(Question question);

    void deleteQuestion(String questionId);

    Page<Question> findQuestionByPage(String userId, Pageable pageAble);

    Page<Question> findQuestionByPage(String userId, String type, Pageable pageAble);

    Page findByPage(Pageable pageable, String userName);
}
