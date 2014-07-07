package com.manhattan.dao;

import com.manhattan.domain.TeacherDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2014/5/22 0022.
 */
public interface TeacherDetailDao extends JpaRepository<TeacherDetail,String>,JpaSpecificationExecutor<TeacherDetail> {

    TeacherDetail findByUserId(String userId);

    @Modifying
    @Transactional
    @Query(value = "update TeacherDetail t set t.authenticationStatus=:status where t.userId=:userId")
    void updateUserStatus(@Param("userId")String userId,@Param("status") String status);
}
