package com.manhattan.service.user.impl;

import com.manhattan.dao.user.UserDAO;
import com.manhattan.domain.User;
import com.manhattan.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dam on 14-4-15.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;
    @Override
    public void findUserById(String userId) {
        userDAO.findOne(userId);
    }

    @Override
    public String findUserByFilter(String userName,String password) {
        return userDAO.findUserByUserNameAndPassword(userName, password);
    }

    @Override
    public User load(String userId) {
        return userDAO.findOne(userId);
    }

    @Override
    public User save(User user) {
        return userDAO.save(user);
    }

    @Override
    public User findUserByUserName(String tel) {
        return userDAO.findUserByUserName(tel);
    }
}
