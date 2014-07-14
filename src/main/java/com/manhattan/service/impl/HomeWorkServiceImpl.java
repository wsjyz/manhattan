package com.manhattan.service.impl;

import com.manhattan.dao.HomeWorkDao;
import com.manhattan.domain.HomeWork;
import com.manhattan.domain.User;
import com.manhattan.service.HomeWorkService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.Date;

/**
 * Created by lk.zh on 2014/6/12.
 */
@Service
public class HomeWorkServiceImpl implements HomeWorkService {
    @Autowired
    private HomeWorkDao homeWorkDao;

    @Override
    public Page<HomeWork> getHomeworksByUser(Pageable pageAble,String userId) {
        return homeWorkDao.findByUserIdContainingOrderByPostTimeDesc(userId,pageAble);
    }

    @Override
    public Page<HomeWork> getHomeworksByTeacher(Pageable pageAble,String teacherId) {
        return homeWorkDao.findByTeacherIdOrderByPostTimeDesc(pageAble, teacherId);
    }

    @Override
    public HomeWork post(HomeWork homeWork) {
        homeWork.setPostTime(new Date());
        return homeWorkDao.saveAndFlush(homeWork);
    }

    @Override
    public Page findByPage(final Pageable pageable, final String userName) {
        return homeWorkDao.findAll(new Specification<HomeWork>() {
            @Override
            public Predicate toPredicate(Root<HomeWork> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                Join<HomeWork,User> userJoin =
                        root.join(root.getModel().getSingularAttribute("user_id",User.class),JoinType.LEFT);
                if (StringUtils.isNotBlank(userName)) {
                    predicate.getExpressions().add(cb.equal(userJoin.<String>get("user_name"), "%"+userName+"%"));
                }
                return predicate;
            }
        }, pageable);
    }
}
