package com.manhattan.service.impl;

import com.manhattan.dao.QuestionDao;
import com.manhattan.domain.Question;
import com.manhattan.domain.User;
import com.manhattan.service.QuestionService;
import com.manhattan.util.MhtConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
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
                    "questionPic","userId","createTime","assignTeacher","status","answerTime");
            return questionDao.save(oldQuestion);
        }
        else{
            question.setCreateTime(sdf.format(new Date().getTime()));
            question.setStatus(MhtConstant.QUESTION_STATUS_UNANSWERED);
            return questionDao.save(question);
        }

    }

    @Override
    public void deleteQuestion(String questionId) {
        questionDao.delete(questionId);
    }

    @Override
    public Page<Question> findQuestionByPage(String userId, Pageable pageAble) {
        if (StringUtils.isBlank(userId)) {
            return questionDao.findAll(pageAble);
        }
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

    @Override
    public Page findByPage(final Pageable pageable, final String userName) {
        return questionDao.findAll(new Specification<Question>() {
            @Override
            public Predicate toPredicate(Root<Question> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                Join<Question,User> userJoin =
                        root.join(root.getModel().getSingularAttribute("askUser",User.class),JoinType.LEFT);
                if (StringUtils.isNotBlank(userName)) {
                    predicate.getExpressions().add(cb.like(userJoin.<String>get("userName"), "%"+userName+"%"));
                }
                return predicate;
            }
        }, pageable);
    }

    @Override
    public Question loadById(String questionId) {
        return questionDao.findOne(questionId);
    }

    @Override
    public void deleteById(String questionId) {
        questionDao.delete(questionId);
    }
}
