package com.manhattan.dao.user;

import com.manhattan.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by dam on 14-4-15.
 */
public interface UserDAO extends CrudRepository<User,String>{

    String findUserByUserNameAndPassword(String userName,String password);

    User findUserByUserName(String tel);
}
