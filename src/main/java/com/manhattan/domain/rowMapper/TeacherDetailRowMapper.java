package com.manhattan.domain.rowMapper;

import com.manhattan.domain.TeacherDetail;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by lk.zh on 2014/6/21 0021.
 */
public class TeacherDetailRowMapper implements RowMapper<TeacherDetail> {
    @Override
    public TeacherDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
        TeacherDetail teacherDetail=new TeacherDetail();
        teacherDetail.setUserId(rs.getString("user_id"));
        teacherDetail.setCourseCategory(rs.getString("course_category"));
        teacherDetail.setAuthentication(rs.getString("authentication"));
        teacherDetail.setCreditRate(rs.getString("credit_rate"));
        teacherDetail.setWechat(rs.getString("wechat"));
        teacherDetail.setSelfIntroduction(rs.getString("self_introduction"));
        teacherDetail.setVideoUrl(rs.getString("video_url"));
        teacherDetail.setAppointmentPhone(rs.getString("appointment_phone"));
        teacherDetail.setExpertScore(rs.getString("expert_score"));
        teacherDetail.setExpertComments(rs.getString("expert_comments"));
        teacherDetail.setTeachingArea(rs.getString("teaching_area"));
        teacherDetail.setTutoringWay(rs.getString("tutoring_way"));
        teacherDetail.setClassFees(rs.getString("class_fees"));
        teacherDetail.setTeachingTime(rs.getString("teaching_time"));
        teacherDetail.setFinalGraduateSchool(rs.getString("final_graduate_school"));
        teacherDetail.setSpecialty(rs.getString("specialty"));
        teacherDetail.setEducationCertificate(rs.getString("education_certificate"));
        teacherDetail.setExperienceExam(rs.getString("experience_exam"));
        teacherDetail.setExamScore(rs.getString("exam_score"));
        teacherDetail.setExamCertificate(rs.getString("exam_certificate"));
        teacherDetail.setTeachingExperience(rs.getString("teaching_experience"));
        teacherDetail.setTeachingCertificate(rs.getString("teaching_certificate"));
        teacherDetail.setStudentMaxScore(rs.getString("student_max_score"));
        teacherDetail.setStudentMaxScoreCertificate(rs.getString("student_max_score_certificate"));
        teacherDetail.setAuthenticationStatus(rs.getString("authentication_status"));
        return teacherDetail;
    }
}
