package com.manhattan.dao;

import com.manhattan.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Administrator on 2014/5/23 0023.
 */
public interface QuestionDao extends JpaRepository<Question,String> {

    Page<Question> findByUserIdOrderByCreateTimeDesc(String userId, Pageable pageAble);

    Question findByQuestionId(String questionId);

    Page<Question> findByAssignTeacherOrderByCreateTimeDesc(String userId, Pageable pageAble);

    Page<Question> findByReplyUserAndStatusOrderByCreateTimeDesc(String replyUser, String status, Pageable pageAble);

    Page<Question> findByStatusOrderByCreateTimeDesc(String status, Pageable pageAble);
}
