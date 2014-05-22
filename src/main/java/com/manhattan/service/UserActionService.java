package com.manhattan.service;

import com.manhattan.domain.UserAction;

/**
 * Created by Administrator on 2014/5/23 0023.
 */
public interface UserActionService {
    UserAction CollectTeacher(String userId, String teacherId);

    int CancelCollectTeacher(String userId, String teacherId);
}
