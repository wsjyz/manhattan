package com.manhattan.service.impl;

import com.manhattan.dao.UserDAO;
import com.manhattan.domain.User;
import com.manhattan.service.UserService;
import com.manhattan.util.MhtConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public String findUserIdByFilter(String mobile, String password) {
        User user = userDAO.findBymobileAndPassword(mobile, password);
        if (user != null) {
            return user.getUserId();
        }
        return null;
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
    public List<User> getTeachersByName(String searchKey) {
        return userDAO.findByUserNameLikeAndType(searchKey, MhtConstant.USER_TYPE_TEACHER);
    }

    @Override
    public Page<User> findTeacherByPage(Pageable pageAble) {
        return userDAO.findAll(pageAble);
    }
}
