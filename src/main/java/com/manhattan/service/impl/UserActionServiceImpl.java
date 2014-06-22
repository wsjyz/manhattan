package com.manhattan.service.impl;

import com.manhattan.dao.UserActionDao;
import com.manhattan.domain.Course;
import com.manhattan.domain.TeacherDetail;
import com.manhattan.domain.User;
import com.manhattan.domain.UserAction;
import com.manhattan.service.UserActionService;
import com.manhattan.util.MhtConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2014/5/23 0023.
 */
@Service
public class UserActionServiceImpl implements UserActionService {
    @Autowired
    private UserActionDao userActionDao;

    @Override
    public UserAction CollectTeacher(String userId, String teacherId) {
        UserAction userAction=new UserAction();
        userAction.setUserId(userId);
        userAction.setActionType(MhtConstant.USER_ACTION_COLLECT_TEACHER);
        userAction.setResourceId(teacherId);
        userAction.setActionTime(new Timestamp((new Date()).getTime()));
        return userActionDao.save(userAction);
    }

    @Override
    public int CancelCollectTeacher(String userId, String teacherId) {
        UserAction userAction=userActionDao.findByUserIdAndResourceIdAndActionType(userId,teacherId,MhtConstant.USER_ACTION_COLLECT_TEACHER);
        userActionDao.delete(userAction.getActionId());
        return userAction!=null?1:0;
    }

    @Override
    public UserAction save(String userId, String courseId, String type) {
        UserAction userAction=new UserAction();
        userAction.setUserId(userId);
        userAction.setResourceId(courseId);
        userAction.setActionType(type);
        userAction.setActionTime(new Timestamp(new Date().getTime()));
        return userActionDao.save(userAction);
    }

}
