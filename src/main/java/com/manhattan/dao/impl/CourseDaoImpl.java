package com.manhattan.dao.impl;

import com.manhattan.dao.BaseDAO;
import com.manhattan.dao.ICourseDao;
import com.manhattan.domain.Course;
import com.manhattan.domain.QueryParam;
import com.manhattan.domain.UserAction;
import com.manhattan.domain.rowMapper.CourseRowMapper;
import com.manhattan.util.OpenPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2014/6/21 0021.
 */
@Service("iCourseDao")
public class CourseDaoImpl extends BaseDAO implements ICourseDao {

    @Override
    public OpenPage<Course> findCourseByQueryParam(QueryParam qp, OpenPage<Course> page) {
        StringBuffer selectSql = new StringBuffer("select ");
        StringBuffer sql = new StringBuffer("");
        sql.append(" from t_mht_course c left join t_mht_teacher_detail td ");
        sql.append("on c.post_teacher=td.user_id left join t_mht_user tu on td.user_id=tu.user_id")
                .append(" where 1=1 ");
        List<Object> params = new ArrayList<Object>();
        if (StringUtils.isNotBlank(qp.getCourseCategory())) {
            sql.append(" and c.course_category=?");
            params.add(qp.getCourseCategory());
        }
        if (StringUtils.isNotBlank(qp.getPlace())) {
            sql.append(" and c.place=?");
            params.add(qp.getPlace());
        }
        if (qp.getAppointmentTime()!=null) {
            sql.append(" and c.start_time<=?");
            sql.append(" and c.end_time>=?");
            params.add(qp.getAppointmentTime());
            params.add(qp.getAppointmentTime());
        }
        if (StringUtils.isNotBlank(qp.getSex())) {
            sql.append(" and tu.sex=?");
            params.add(qp.getSex());
        }
        if (StringUtils.isNotBlank(qp.getTutoringWay())) {
            sql.append(" and c.tutoringWay=?");
            params.add(qp.getTutoringWay());
        }
        List<Course> courseList = new ArrayList<Course>();
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
        courseList=getJdbcTemplate().query(selectSql.append("c.*").append(sql).toString(), params.toArray(), new CourseRowMapper());
        page.setRows(courseList);
        return page;
    }

    @Override
    public OpenPage<Course> findCourseByUserId(OpenPage<Course> page, String userId, String actionType, Date startTime, Date endTime) {
        Course course = new Course();
        course.setStartTime(startTime);
        course.setEndTime(endTime);
        UserAction userAction=new UserAction();
        userAction.setUserId(userId);
        userAction.setActionType(actionType);
        return findCourseByUserId(page, course, userAction);
    }

    public OpenPage<Course> findCourseByUserId(OpenPage<Course> page, Course course,UserAction userAction) {
        StringBuffer selectSql = new StringBuffer("select ");
        StringBuffer sql = new StringBuffer("");
        sql.append(" from t_mht_course c left join t_mht_user_action ua ");
        sql.append("on c.course_id=ua.resource_id ")
                .append(" where 1=1 ");
        List<Object> params = new ArrayList<Object>();
        if (StringUtils.isNotBlank(userAction.getUserId())) {
            sql.append(" and ua.user_id=?");
            params.add(userAction.getUserId());
        }
        if (StringUtils.isNotBlank(userAction.getActionType())) {
            sql.append(" and ua.action_type=?");
            params.add(userAction.getActionType());
        }
        if (course.getStartTime()!=null) {
            sql.append(" and c.start_time<=?");
            params.add(course.getEndTime());
        }
        if (course.getEndTime()!=null) {
            sql.append(" and c.end_time>=?");
            params.add(course.getStartTime());
        }
        if (StringUtils.isNotBlank(course.getTeachers())) {
            sql.append(" and c.teachers like %?%");
            params.add(course.getTeachers());
        }
        List<Course> courseList = new ArrayList<Course>();
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
        courseList=getJdbcTemplate().query(selectSql.append("c.*").append(sql).toString(), params.toArray(), new CourseRowMapper());
        page.setRows(courseList);
        return page;
    }

    @Override
    public OpenPage<Course> finCoursesByTeacher(OpenPage<Course> page, String userId, String action) {
        Course course = new Course();
        UserAction userAction=new UserAction();
        userAction.setUserId(userId);
        userAction.setActionType(action);
        return findCourseByUserId(page, course, userAction);
    }

}
