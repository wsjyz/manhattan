package com.manhattan.service.impl;

import com.manhattan.dao.QuestionDao;
import com.manhattan.domain.Question;
import com.manhattan.service.QuestionService;
import com.manhattan.util.MhtConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2014/5/23 0023.
 */
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionDao questionDao;

    @Override
    public Question saveQuestion(Question question) {
        return questionDao.save(question);
    }

    @Override
    public void deleteQuestion(String questionId) {
        questionDao.delete(questionId);
    }

    @Override
    public Page<Question> findQuestionByPage(String userId, Pageable pageAble) {
        return questionDao.findByUserId(userId,pageAble);
    }

    @Override
    public Page<Question> findQuestionByPage(String userId, String type, Pageable pageAble) {
        if (StringUtils.equals(type, "ASSIGN")) {
            return questionDao.findByAssignTeacher(userId,pageAble);
        }else if (StringUtils.equals(type, MhtConstant.QUESTION_STATUS_ANSWERED)) {
            return questionDao.findByAssignTeacherAndStatus(userId, MhtConstant.QUESTION_STATUS_ANSWERED, pageAble);
        }else if (StringUtils.equals(type, MhtConstant.QUESTION_STATUS_UNANSWERED)) {
            return questionDao.findByStatus(MhtConstant.QUESTION_STATUS_UNANSWERED, pageAble);
        }
        return null;
    }
}
