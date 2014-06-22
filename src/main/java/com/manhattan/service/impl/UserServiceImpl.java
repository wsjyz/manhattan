package com.manhattan.service.impl;

import com.manhattan.dao.IUserDAO;
import com.manhattan.dao.UserDAO;
import com.manhattan.domain.*;
import com.manhattan.service.UserService;
import com.manhattan.util.MhtConstant;
import com.manhattan.util.OpenPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.List;

/**
 * Created by dam on 14-4-15.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private IUserDAO iUserDAO;

    @Override
    public User findUserById(String userId) {
        return userDAO.findOne(userId);
    }

    @Override
    public User findUserIdByFilter(String mobile, String password) {
        User user = userDAO.findBymobileAndPassword(mobile, password);
        return user;
    }

    @Override
    public User findUserByFilter(String mobile, String password) {
        return userDAO.findByMobileAndAuthCode(mobile, password);
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
    public User findUserByMobile(String tel) {
        return userDAO.findByMobile(tel);
    }

    @Override
    public int resetPassword(String tel, String newPassword) {
        return userDAO.updatePass(tel, newPassword);
    }

    @Override
    public int register(String userId, String password, String type) {
        return userDAO.updateUser(userId, password, type);
    }

    @Override
    public OpenPage<User> findUserByTeacherId(OpenPage<User> openPage, String teacherId, String actionType) {
        return iUserDAO.findUserByTeacherId(openPage,teacherId,actionType);
    }


}
