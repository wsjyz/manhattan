package com.manhattan.dao;

import com.manhattan.domain.User;
import com.manhattan.util.OpenPage;
import org.springframework.stereotype.Service;

/**
 * Created by lk.zh on 2014/6/22 0022.
 */
public interface IUserDAO {
    OpenPage<User> findUserByTeacherId(OpenPage<User> openPage, String teacherId, String actionType);

}
