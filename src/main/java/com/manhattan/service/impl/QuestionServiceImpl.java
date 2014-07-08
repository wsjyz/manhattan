package com.manhattan.service.impl;

import com.manhattan.dao.QuestionDao;
import com.manhattan.domain.Question;
import com.manhattan.service.QuestionService;
import com.manhattan.util.MhtConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2014/5/23 0023.
 */
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionDao questionDao;

    @Override
    public Question saveQuestion(Question question) {
        Question oldQuestion = questionDao.findByQuestionId(question.getQuestionId());
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(oldQuestion != null){
            oldQuestion.setAnswerTime(sdf.format(new Date().getTime()));
            oldQuestion.setStatus(MhtConstant.QUESTION_STATUS_ANSWERED);
            BeanUtils.copyProperties(question,oldQuestion,"questionId","questionTitle","questionContent",
                    "questionPic","userId","createTime","assignTeacher","status");
            return questionDao.save(oldQuestion);
        }
//        else{
//            question.setCreateTime(sdf.format(new Date().getTime()));
//            question.setStatus(MhtConstant.QUESTION_STATUS_UNANSWERED);
//            return questionDao.save(question);
//        }
        return null;

    }

    @Override
    public void deleteQuestion(String questionId) {
        questionDao.delete(questionId);
    }

    @Override
    public Page<Question> findQuestionByPage(String userId, Pageable pageAble) {
        return questionDao.findByUserIdOrderByCreateTimeDesc(userId, pageAble);
    }

    @Override
    public Page<Question> findQuestionByPage(String userId, String type, Pageable pageAble) {
        if (StringUtils.equals(type, "ASSIGN")) {
            return questionDao.findByAssignTeacherOrderByCreateTimeDesc(userId, pageAble);
        }else if (StringUtils.equals(type, MhtConstant.QUESTION_STATUS_ANSWERED)) {
            return questionDao.findByReplyUserAndStatusOrderByCreateTimeDesc(userId, MhtConstant.QUESTION_STATUS_ANSWERED, pageAble);
        }else if (StringUtils.equals(type, MhtConstant.QUESTION_STATUS_UNANSWERED)) {
            return questionDao.findByStatusOrderByCreateTimeDesc(MhtConstant.QUESTION_STATUS_UNANSWERED, pageAble);
        }
        return null;
    }
}
