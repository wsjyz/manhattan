package com.manhattan.dao;

import com.manhattan.domain.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dam on 14-4-15.
 */
public interface UserDAO extends JpaRepository<User, String>,JpaSpecificationExecutor<User> {

    User findBymobileAndPassword(String mobile, String password);

    User findByMobileAndAuthCode(String mobile, String authCode);

    User findByMobile(String mobile);
    
    @Modifying
    @Transactional
    @Query(value = "update User u set u.password=:newPassword where u.mobile=:tel")
    int updatePass(@Param("tel") String tel, @Param("newPassword") String newPassword);
    
    @Modifying
    @Transactional
    @Query(value = "update User u set u.password=?2,u.type=?3,u.status='ENABLE' where u.userId=?1")
    int updateUser(String userId, String password, String type);

    Page<User> findByUserNameLikeAndType(String userName, String teacher,Pageable pageAble);

    Page findByMobileLikeAndUserNameLike(String mobile,String userName, Pageable pageAble);

    @Modifying
    @Transactional
    @Query(value = "update User u set u.status=:status where u.userId=:userId")
    void updateUserStatus(@Param("userId")String userId,@Param("status") String status);

    @Modifying
    @Transactional
    @Query(value = "update User u set u.userName=?2,u.sex=?3,u.email=?4,u.address=?5,u.area=?6 where u.userId=?1")
    int updateUser(String userId, String userName, String sex, String email, String address, String area);

    @Modifying
    @Transactional
    @Query(value = "update User u set u.password=?2 where u.userId=?1")
    int changePassword(String userId, String changePass);
}
