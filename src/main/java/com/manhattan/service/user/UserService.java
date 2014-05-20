package com.manhattan.service.user;


import com.manhattan.domain.User;

/**
 * Created by dam on 14-4-15.
 */
public interface UserService {
    /**
     *根据id查找
     * @param userId
     */
    void findUserById(String userId);

    String findUserByFilter(String userName,String password);

    User load(String userId);

    User save(User user);

    User findUserByUserName(String tel);
}
