package com.manhattan.dao;

import com.manhattan.domain.TeacherDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Administrator on 2014/5/22 0022.
 */
public interface TeacherDetailDao extends JpaRepository<TeacherDetail,String>,JpaSpecificationExecutor<TeacherDetail> {

    TeacherDetail findByUserId(String userId);
}
