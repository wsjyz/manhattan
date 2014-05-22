package com.manhattan.service.impl;

import com.manhattan.dao.UserActionDao;
import com.manhattan.domain.UserAction;
import com.manhattan.service.UserActionService;
import com.manhattan.util.MhtConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

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
        userAction.setActionType(MhtConstant.ACTION_COLLECT);
        userAction.setResourceId(teacherId);
        userAction.setActionTime(new Timestamp((new Date()).getTime()));
        return userActionDao.save(userAction);
    }

    @Override
    public int CancelCollectTeacher(String userId, String teacherId) {
        UserAction userAction=userActionDao.findByUserIdAndResourceIdAndActionType(userId,teacherId,MhtConstant.ACTION_COLLECT);
        userActionDao.delete(userAction.getActionId());
        return userAction!=null?1:0;
    }
}
