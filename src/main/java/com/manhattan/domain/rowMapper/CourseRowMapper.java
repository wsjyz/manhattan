package com.manhattan.domain.rowMapper;

import com.manhattan.domain.Course;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 2014/6/21 0021.
 */
public class CourseRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Course course=new Course();
        course.setCourseId(rs.getString("course_id"));
        course.setCourseTitle(rs.getString("course_title"));
        course.setCourseSubtitle(rs.getString("course_subtitle"));
        course.setCoursePic(rs.getString("course_pic"));
        course.setClassNo(rs.getString("class_no"));
        course.setStartTime(rs.getTimestamp("start_time"));
        course.setEndTime(rs.getTimestamp("end_time"));
        course.setPeriod(rs.getBigDecimal("period"));
        course.setExpense(rs.getBigDecimal("expense"));
        course.setPlace(rs.getString("place"));
        course.setCourseIntro(rs.getString("course_intro"));
        course.setAppointmentPhone(rs.getString("appointment_phone"));
        course.setTeachers(rs.getString("teachers"));
        course.setCourseCategory(rs.getString("course_category"));
        course.setPostTeacher(rs.getString("post_teacher"));
        course.setVideoUrl(rs.getString("video_url"));
        course.setTeachingTime(rs.getString("teaching_time"));
        return course;
    }
}
