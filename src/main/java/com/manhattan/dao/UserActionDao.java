package com.manhattan.dao;

import com.manhattan.domain.Course;
import com.manhattan.domain.UserAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Administrator on 2014/5/23 0023.
 */
public interface UserActionDao extends JpaRepository<UserAction,String>,JpaSpecificationExecutor<UserAction> {

    UserAction findByUserIdAndResourceIdAndActionType(String userId, String teacherId, String actionType);
}
