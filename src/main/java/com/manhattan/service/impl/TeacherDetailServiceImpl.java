package com.manhattan.service.impl;

import com.manhattan.dao.ITeacherDetailDao;
import com.manhattan.dao.TeacherDetailDao;
import com.manhattan.domain.QueryParam;
import com.manhattan.domain.TeacherDetail;
import com.manhattan.domain.User;
import com.manhattan.domain.UserAction;
import com.manhattan.service.TeacherDetailService;
import com.manhattan.util.MhtConstant;
import com.manhattan.util.OpenPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;

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
        if (userAction.equals(MhtConstant.USER_ACTION_COLLECT_TEACHER)) {
            return iTeacherDetailDao.findTeachersByUserIdAndAction(page, userId, userAction);
        }
        return iTeacherDetailDao.findTeachersByUserId(page, userId, userAction);
    }

    @Override
    public OpenPage<TeacherDetail> findTeacherByPage(OpenPage<TeacherDetail> page,final String searchKey) {
        OpenPage<TeacherDetail> teacherDetailOpenPage = iTeacherDetailDao.findTeachers(page, searchKey);
        return teacherDetailOpenPage;
    }

    @Override
    public TeacherDetail postCourse(TeacherDetail teacherDetail) {
        if(StringUtils.isNotBlank(teacherDetail.getUserId())) {
            TeacherDetail detail=findTeacherDetail(teacherDetail.getUserId());
            if(detail!=null&&StringUtils.isNotBlank(detail.getUserId())){
                BeanUtils.copyProperties(teacherDetail,detail,"userId");
                return teacherDetailDao.saveAndFlush(detail);
            }else{
                return teacherDetailDao.saveAndFlush(teacherDetail);
            }
        }
        return null;
    }

    @Override
    public OpenPage<TeacherDetail> findTeacherByPage(OpenPage<TeacherDetail> page,QueryParam queryParam) {
        OpenPage<TeacherDetail> teacherDetailOpenPage = iTeacherDetailDao.findTeachers(page, queryParam);
        return teacherDetailOpenPage;
    }

    @Override
    public void updateUserStatus(String userId, String status) {
        teacherDetailDao.updateUserStatus(userId,status);
    }

    @Override
    public OpenPage findPostCourseTeachers(OpenPage page, String mobile, String userName) {
        return iTeacherDetailDao.findPostCourseTeachers(page,mobile,userName);
    }

    @Override
    public List<Date> findAppiontByUseIdAndTime(String userId, Date startTime, Date endTime) {
        return iTeacherDetailDao.findAppiontByUseIdAndTime(userId,startTime,endTime);
    }
}
