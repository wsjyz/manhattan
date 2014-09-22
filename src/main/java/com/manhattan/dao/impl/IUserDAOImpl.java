package com.manhattan.dao.impl;

import com.manhattan.dao.BaseDAO;
import com.manhattan.dao.IUserDAO;
import com.manhattan.domain.Information;
import com.manhattan.domain.TeacherDetail;
import com.manhattan.domain.User;
import com.manhattan.domain.rowMapper.TeacherDetailRowMapper;
import com.manhattan.domain.rowMapper.UserRowMapper;
import com.manhattan.util.MhtConstant;
import com.manhattan.util.OpenPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lk.zh on 2014/6/22 0022.
 */
@Repository("iUserDao")
public class IUserDAOImpl extends BaseDAO implements IUserDAO {
    @Override
    public OpenPage<User> findUserByTeacherId(OpenPage<User> page, String teacherId, String actionType) {
        StringBuffer selectSql = new StringBuffer("select ");
        StringBuffer sql = new StringBuffer("");
        sql.append(" from t_mht_user t left join t_mht_appointment ua ");
        sql.append("on t.user_id=ua.user_id ")
                .append(" where 1=1 ");
        List<Object> params = new ArrayList<Object>();
        if (StringUtils.isNotBlank(teacherId)) {
            sql.append(" and ua.resource_id = ? ");
            params.add(teacherId);
        }
        if (StringUtils.isNotBlank(actionType)) {
            sql.append(" and ua.resource_type=? ");
            params.add(actionType);
        }
        sql.append(" and ua.status=? ");
        params.add(MhtConstant.SUCCESS);
        List<User> users = new ArrayList<User>();
        if (page.isAutoCount()) {
            long count = getJdbcTemplate().queryForObject(selectSql.append("count(*) ").append(sql).toString(), params.toArray(),Long.class);
            page.setTotal(count);
            selectSql=new StringBuffer("select ");
        }
        if (page.isAutoPaging()) {
            sql.append("limit ? offset ? ");
            params.add(page.getPageSize());
            params.add(page.getPageNo() - 1);
        }
        users=getJdbcTemplate().query(selectSql.append("t.*").append(sql).toString(), params.toArray(), new UserRowMapper());
        page.setRows(users);
        return page;
    }

    @Override
    public User load(String userId) {
        User user=getJdbcTemplate().queryForObject("select * from t_mht_user where user_id=? ",new Object[]{userId},new UserRowMapper());
        return user;
    }

}
