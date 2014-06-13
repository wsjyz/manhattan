package com.manhattan.service;


import com.manhattan.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by dam on 14-4-15.
 */
public interface UserService {
    /**
     * 根据id查找
     *
     * @param userId
     */
    User findUserById(String userId);

    User findUserIdByFilter(String userName, String password);

    User findUserByFilter(String userName, String password);

    User load(String userId);

    User save(User user);

    User findUserByMobile(String tel);

    int resetPassword(String tel, String newPassword);

    int register(String userId, String password,String type);

    List<User> getTeachersByName(String searchKey);

    Page<User> findTeacherByPage(Pageable pageAble,String searchKey);
}
