package com.manhattan.service.impl;

import com.manhattan.dao.ITeacherDetailDao;
import com.manhattan.dao.TeacherDetailDao;
import com.manhattan.domain.TeacherDetail;
import com.manhattan.domain.User;
import com.manhattan.domain.UserAction;
import com.manhattan.service.TeacherDetailService;
import com.manhattan.util.MhtConstant;
import com.manhattan.util.OpenPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;

/**
 * Created by Administrator on 2014/5/22 0022.
 */
@Service
public class TeacherDetailServiceImpl implements TeacherDetailService {
    @Autowired
    private TeacherDetailDao teacherDetailDao;
    @Autowired
    private ITeacherDetailDao iTeacherDetailDao;

    @Override
    public TeacherDetail findTeacherDetail(String userId) {
        return iTeacherDetailDao.findByUserId(userId);
    }

    @Override
    public OpenPage<TeacherDetail> findTeachersByUserId(OpenPage<TeacherDetail> page,String userId,String userAction) {
//        Page<TeacherDetail> teacherDetails=teacherDetailDao.findAll(new Specification<TeacherDetail>() {
//            @Override
//            public Predicate toPredicate(Root<TeacherDetail> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//                Predicate predicate = cb.conjunction();
//                SetJoin<TeacherDetail,UserAction> userActionSetJoin=root.join(root.getModel().getSet("setUserActions", UserAction.class), JoinType.LEFT);
//                if (StringUtils.isNotBlank(userId)) {
//                    predicate.getExpressions().add(
//                            cb.equal(userActionSetJoin.<String>get("userId"), StringUtils.trim(userId))
//                    );
//                }
//                if (StringUtils.isNotBlank(userAction)) {
//                    predicate.getExpressions().add(
//                            cb.equal(userActionSetJoin.<String>get("actionType"), StringUtils.trim(userAction))
//                    );
//                }
//                return predicate;
//            }
//        },pageAble);
        return iTeacherDetailDao.findTeachersByUserId(page,userId,userAction);
    }

    @Override
    public OpenPage<TeacherDetail> findTeacherByPage(OpenPage<TeacherDetail> page,final String searchKey) {
        OpenPage<TeacherDetail> teacherDetailOpenPage = iTeacherDetailDao.findTeachers(page, searchKey);
        return teacherDetailOpenPage;
    }
}
