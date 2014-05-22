package com.manhattan.dao;

import com.manhattan.domain.UserAction;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Administrator on 2014/5/23 0023.
 */
public interface UserActionDao extends CrudRepository<UserAction,String>{

    UserAction findByUserIdAndResourceIdAndActionType(String userId, String teacherId, String actionType);
}
