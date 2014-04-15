package com.manhattan.service.user.impl;

import com.manhattan.dao.user.UserDAO;
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
        userDAO.findUserById(userId);
    }
}
