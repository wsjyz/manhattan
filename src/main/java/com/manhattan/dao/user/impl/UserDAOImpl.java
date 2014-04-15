package com.manhattan.dao.user.impl;

import com.manhattan.dao.BaseDAO;
import com.manhattan.dao.user.UserDAO;
import org.springframework.stereotype.Repository;

/**
 * Created by dam on 14-4-15.
 */
@Repository("userDAO")
public class UserDAOImpl extends BaseDAO implements UserDAO {
    @Override
    public void findUserById(String userId) {
        StringBuilder sql = new StringBuilder("select count(*) from ")
                .append("user");
        getJdbcTemplate().queryForInt(sql.toString());
    }
}
