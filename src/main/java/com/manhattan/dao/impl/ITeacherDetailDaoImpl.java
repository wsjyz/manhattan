package com.manhattan.dao.impl;

import com.manhattan.dao.BaseDAO;
import com.manhattan.dao.ITeacherDetailDao;
import com.manhattan.dao.UserDAO;
import com.manhattan.domain.QueryParam;
import com.manhattan.domain.TeacherDetail;
import com.manhattan.domain.User;
import com.manhattan.domain.rowMapper.TeacherDetailRowMapper;
import com.manhattan.domain.rowMapper.UserRowMapper;
import com.manhattan.util.MhtConstant;
import com.manhattan.util.OpenPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lk.zh on 2014/6/21 0021.
 */
@Repository("iTeacherDetailDao")
public class ITeacherDetailDaoImpl extends BaseDAO implements ITeacherDetailDao  {
    @Autowired
    private UserDAO userDAO;

    @Override
    public OpenPage<TeacherDetail> findTeachers(OpenPage<TeacherDetail> page, String searchKey) {
        StringBuffer selectSql = new StringBuffer("select ");
        StringBuffer sql = new StringBuffer("");
        sql.append(" from t_mht_teacher_detail t left join t_mht_user u ");
        sql.append("on t.user_id=u.user_id ")
                .append(" where 1=1 ");
        List<Object> params = new ArrayList<Object>();
        if (StringUtils.isNotBlank(searchKey)) {
            sql.append(" and u.user_name like ?");
            params.add("%"+searchKey+"%");
        }
        sql.append(" and u.type=? ");
        params.add(MhtConstant.USER_TYPE_TEACHER);
        List<TeacherDetail> teacherDetails = new ArrayList<TeacherDetail>();
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
        teacherDetails=getJdbcTemplate().query(selectSql.append("*").append(sql).toString(), params.toArray(), new TeacherDetailRowMapper());
        setUsers(teacherDetails);
        page.setRows(teacherDetails);
        return page;
    }

    @Override
    public OpenPage<TeacherDetail> findTeachersByUserId(OpenPage<TeacherDetail> page, String userId, String resourceType) {
        StringBuffer selectSql = new StringBuffer("select ");
        StringBuffer sql = new StringBuffer("");
        sql.append(" from t_mht_teacher_detail t inner join t_mht_appointment ua ");
        sql.append("on t.user_id=ua.resource_id ")
                .append(" where 1=1 ");
        List<Object> params = new ArrayList<Object>();
        sql.append(" and ua.status=? ");
        params.add(MhtConstant.SUCCESS);
        if (StringUtils.isNotBlank(userId)) {
            sql.append(" and ua.user_id = ? ");
            params.add(userId);
        }
        if (StringUtils.isNotBlank(resourceType)) {
            sql.append(" and ua.resource_type=? ");
            params.add(resourceType);
        }
        List<TeacherDetail> teacherDetails = new ArrayList<TeacherDetail>();
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
        teacherDetails=getJdbcTemplate().query(selectSql.append("t.*").append(sql).toString(), params.toArray(), new TeacherDetailRowMapper());
        setUsers(teacherDetails);
        page.setRows(teacherDetails);
        return page;
    }

    @Override
    public TeacherDetail findByUserId(String userId) {
        List<TeacherDetail> teacherDetails=getJdbcTemplate().query("select * from t_mht_teacher_detail where user_id=? ", new Object[]{userId}, new TeacherDetailRowMapper());
        if (!CollectionUtils.isEmpty(teacherDetails)) {
            setUsers(teacherDetails);
            TeacherDetail detail = teacherDetails.get(0);
            Map<String, Object> extMap = new HashMap<String, Object>();
            OpenPage<TeacherDetail> page = new OpenPage<TeacherDetail>();
            page.setAutoCount(true);
            page.setAutoPaging(true);
            extMap.put("followCount", findTeachersByUserIdAndAction(page, null, MhtConstant.USER_ACTION_FOLLOW_TEACHER,userId).getTotal());
            extMap.put("commentCount", findTeachersByUserIdAndAction(page, null, MhtConstant.USER_ACTION_COMMENT_TEACHER,userId).getTotal());
            extMap.put("collectCount", findTeachersByUserIdAndAction(page, null, MhtConstant.USER_ACTION_COLLECT_TEACHER,userId).getTotal());
            detail.setExtMap(extMap);
            return detail;
        }
        return new TeacherDetail();
    }

    @Override
    public OpenPage<TeacherDetail> findTeachersByUserIdAndAction(OpenPage<TeacherDetail> page, String userId, String userAction) {
        return findTeachersByUserIdAndAction(page, userId, userAction, null);
    }

    public OpenPage<TeacherDetail> findTeachersByUserIdAndAction(OpenPage<TeacherDetail> page, String userId, String userAction ,String teacherId) {
        StringBuffer selectSql = new StringBuffer("select ");
        StringBuffer sql = new StringBuffer("");
        sql.append(" from t_mht_teacher_detail t inner join t_mht_user_action ua ");
        sql.append("on t.user_id=ua.resource_id ")
                .append(" where 1=1 ");
        List<Object> params = new ArrayList<Object>();
        if (StringUtils.isNotBlank(userId)) {
            sql.append(" and ua.user_id = ? ");
            params.add(userId);
        }
        if (StringUtils.isNotBlank(userAction)) {
            sql.append(" and ua.action_type=? ");
            params.add(userAction);
        }
        if (StringUtils.isNotBlank(teacherId)) {
            sql.append(" and t.user_id=? ");
            params.add(teacherId);
        }
        List<TeacherDetail> teacherDetails = new ArrayList<TeacherDetail>();
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
        teacherDetails=getJdbcTemplate().query(selectSql.append("t.*").append(sql).toString(), params.toArray(), new TeacherDetailRowMapper());
        setUsers(teacherDetails);
        page.setRows(teacherDetails);
        return page;
    }

    @Override
    public OpenPage<TeacherDetail> findTeachers(OpenPage<TeacherDetail> page, QueryParam queryParam) {
        StringBuffer selectSql = new StringBuffer("select ");
        StringBuffer sql = new StringBuffer("");
        sql.append(" from t_mht_teacher_detail t left join t_mht_user u ");
        sql.append("on t.user_id=u.user_id ")
                .append(" where 1=1 ");
        List<Object> params = new ArrayList<Object>();
        if (StringUtils.isNotBlank(queryParam.getKeyword())) {
            sql.append(" and u.user_name like ?");
            params.add("%"+queryParam.getKeyword()+"%");
        }
        if (StringUtils.isNotBlank(queryParam.getSex())) {
            sql.append(" and u.sex = ?");
            params.add(queryParam.getSex());
        }
        if (StringUtils.isNotBlank(queryParam.getTutoringWay())) {
            sql.append(" and t.tutoring_way = ?");
            params.add(queryParam.getTutoringWay());
        }
        if (StringUtils.isNotBlank(queryParam.getPlace())) {
            sql.append(" and t.teaching_area = ?");
            params.add(com.manhattan.util.StringUtils.formatInStr(queryParam.getPlace()));
        }
        sql.append(" and u.type=? ");
        params.add(MhtConstant.USER_TYPE_TEACHER);
        List<TeacherDetail> teacherDetails = new ArrayList<TeacherDetail>();
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
        teacherDetails=getJdbcTemplate().query(selectSql.append("*").append(sql).toString(), params.toArray(), new TeacherDetailRowMapper());
        setUsers(teacherDetails);
        page.setRows(teacherDetails);
        return page;
    }

    @Override
    public OpenPage findPostCourseTeachers(OpenPage page, String mobile, String userName) {
        StringBuffer selectSql = new StringBuffer("select ");
        StringBuffer sql = new StringBuffer("");
        sql.append(" from t_mht_teacher_detail t inner join t_mht_user u");
        sql.append(" on t.user_id=u.user_id ");
        sql.append(" where 1=1 ");
        List<Object> params = new ArrayList<Object>();
        if (StringUtils.isNotBlank(userName)) {
            sql.append(" and u.user_name like ?");
            params.add("%"+userName+"%");
        }
        if (StringUtils.isNotBlank(mobile)) {
            sql.append(" and u.mobile like ?");
            params.add("%"+mobile+"%");
        }
        sql.append(" and u.type=? ");
        params.add(MhtConstant.USER_TYPE_TEACHER);
        List<TeacherDetail> teacherDetails = new ArrayList<TeacherDetail>();
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
        teacherDetails=getJdbcTemplate().query(selectSql.append("*").append(sql).toString(), params.toArray(), new TeacherDetailRowMapper());
        setUsers(teacherDetails);
        page.setRows(teacherDetails);
        return page;
    }

    @Override
    public List<Date> findAppiontByUseIdAndTime(String userId, Date startTime, Date endTime) {
        StringBuffer selectSql = new StringBuffer("select ");
        StringBuffer sql = new StringBuffer("");
        sql.append(" from t_mht_teacher_detail t inner join t_mht_appointment ua ");
        sql.append("on t.user_id=ua.resource_id ")
                .append(" where 1=1 ");
        List<Object> params = new ArrayList<Object>();

        sql.append(" and ua.status = ? ");
        params.add(MhtConstant.SUCCESS);
        if (StringUtils.isNotBlank(userId)) {
            sql.append(" and ua.user_id = ? ");
            params.add(userId);
        }
        if (startTime != null) {
            sql.append(" and ua.appointment_time >=? ");
            params.add(startTime);
        }
        if (endTime != null) {
            sql.append(" and ua.appointment_time <=? ");
            params.add(endTime);
        }
        sql.append(" and (ua.resource_type=? or ua.resource_type=?) ");
        params.add(MhtConstant.USER_ACTION_APPOINTMENT_TEACHER);
        params.add(MhtConstant.USER_ACTION_LISTEN_TEACHER);
        List<String> times = new ArrayList<String>();
        times=getJdbcTemplate().queryForList(selectSql.append("ua.appointment_time").append(sql).toString(), params.toArray(), String.class);
        List<Date> dates = new ArrayList<Date>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(!CollectionUtils.isEmpty(times)) {
            try {
                for (String str : times) {
                    dates.add(sdf.parse(str));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return dates;
    }

    private void setUsers(List<TeacherDetail> teacherDetails){
        if (!CollectionUtils.isEmpty(teacherDetails)) {
            for (TeacherDetail teacherDetail : teacherDetails) {
                User user=getJdbcTemplate().queryForObject("select * from t_mht_user where user_id=? ",new Object[]{teacherDetail.getUserId()},new UserRowMapper());
                teacherDetail.setUser(user);
            }
        }
    }
}
