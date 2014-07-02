package com.manhattan.dao;

import com.manhattan.domain.HomeWork;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by lk.zh on 2014/6/12.
 */
public interface HomeWorkDao extends JpaRepository<HomeWork,String>,JpaSpecificationExecutor<HomeWork> {

    Page<HomeWork> findByUserIdContainingOrderByPostTimeDesc(String userId,Pageable pageAble);

    Page<HomeWork> findByTeacherIdOrderByPostTimeDesc(Pageable pageAble,String teacherId);
}
