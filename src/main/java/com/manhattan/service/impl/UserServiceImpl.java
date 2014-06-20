package com.manhattan.service.impl;

import com.manhattan.dao.UserDAO;
import com.manhattan.domain.Course;
import com.manhattan.domain.TeacherDetail;
import com.manhattan.domain.User;
import com.manhattan.domain.UserAction;
import com.manhattan.service.UserService;
import com.manhattan.util.MhtConstant;
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
    public Page<User> getTeachersByName(Pageable pageAble,String searchKey) {
        return userDAO.findByUserNameLikeAndType(searchKey, MhtConstant.USER_TYPE_TEACHER,pageAble);
    }

    @Override
    public Page<User> findTeacherByPage(Pageable pageAble,final String searchKey) {
        return userDAO.findAll(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                Join<User,TeacherDetail> teacherDetailJoin=root.join(root.getModel().getSingularAttribute("teacherDetail",TeacherDetail.class),JoinType.INNER);
                if (StringUtils.isNotBlank(searchKey)) {
                    predicate.getExpressions().add(
                            cb.like(root.<String>get("userName"), "%" + StringUtils.trim(searchKey) + "%")
                    );
                }
                predicate.getExpressions().add(
                        cb.equal(root.<String>get("type"), MhtConstant.USER_TYPE_TEACHER)
                );
                return predicate;
            }
        }, pageAble);
    }
}
