package com.manhattan.domain.rowMapper;

import com.manhattan.domain.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by lk.zh on 2014/6/22 0022.
 */
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user=new User();
        user.setUserId(rs.getString("user_id"));
        user.setUserName(rs.getString("user_name"));
        user.setPassword(rs.getString("password"));
        user.setAuthCode(rs.getString("auth_code"));
        user.setType(rs.getString("type"));
        user.setAvatar(rs.getString("avatar"));
        user.setSex(rs.getString("sex"));
        user.setMobile(rs.getString("mobile"));
        user.setEmail(rs.getString("email"));
        user.setAddress(rs.getString("address"));
        user.setCredits(rs.getInt("credit"));
        user.setWallet(rs.getBigDecimal("wallet"));
        user.setVipExpiredTime(rs.getDate("vip_end_time"));
        user.setEvaluation(rs.getString("evaluation"));
        user.setArea(rs.getString("area"));
        user.setLastLoginTime(rs.getDate("last_login_time"));
        user.setStatus(rs.getString("status"));
        return user;
    }
}
