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

    Page<Question> findByUserId(String userId, Pageable pageAble);

    Page<Question> findByAssignTeacher(String userId, Pageable pageAble);

    Page<Question> findByAssignTeacherAndStatus(String userId, String status, Pageable pageAble);

    Page<Question> findByStatus(String status, Pageable pageAble);
}
