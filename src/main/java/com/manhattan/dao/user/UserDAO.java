package com.manhattan.dao.user;

import com.manhattan.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by dam on 14-4-15.
 */
public interface UserDAO extends CrudRepository<User, String> {

    String findUserByUserNameAndPassword(String userName, String password);

    User findUserByUserName(String tel);

    @Query(value = "update User u set u.password=:newPassword where u.mobile=:tel")
    int updatePass(@Param("tel") String tel, @Param("newPassword") String newPassword);
}
