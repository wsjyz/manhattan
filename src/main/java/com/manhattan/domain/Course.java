package com.manhattan.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Administrator on 2014/6/12 0012.
 */
@Entity
@Table(name = "t_mht_course")
public class Course {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "course_id")
    private String courseId;
    @Column(name = "course_title")
    private String courseTitle;
    @Column(name = "course_subtitle")
    private String courseSubtitle;
    @Column(name = "course_pic")
    private String coursePic;
    @Column(name = "start_time")
    private Timestamp startTime;
    @Column(name = "end_time")
    private Timestamp endTime;
    @Column(name = "period")
    private BigDecimal period;
    @Column(name = "expense")
    private BigDecimal expense;
    @Column(name = "place")
    private String place;
    @Column(name = "course_intro")
    private String courseIntro;
    @Column(name = "appointment_phone")
    private String appointmentPhone;
    @Column(name = "teachers")
    private String teachers;
    @Column(name = "course_category")
    private String courseCategory;
    @Column(name = "post_teacher")
    private String postTeacher;
    @Column(name = "video_url")
    private String videoUrl;
    @Column(name = "teaching_time")
    private String teachingTime;

    @ManyToOne
    @JoinColumn(name="postTeacher",insertable = false,updatable = false)
    private TeacherDetail teacher;
    @OneToMany(cascade=CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy="resourceId")
    private List<UserAction> userActions;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseSubtitle() {
        return courseSubtitle;
    }

    public void setCourseSubtitle(String courseSubtitle) {
        this.courseSubtitle = courseSubtitle;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getPeriod() {
        return period;
    }

    public void setPeriod(BigDecimal period) {
        this.period = period;
    }

    public BigDecimal getExpense() {
        return expense;
    }

    public void setExpense(BigDecimal expense) {
        this.expense = expense;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getCourseIntro() {
        return courseIntro;
    }

    public void setCourseIntro(String courseIntro) {
        this.courseIntro = courseIntro;
    }

    public String getAppointmentPhone() {
        return appointmentPhone;
    }

    public void setAppointmentPhone(String appointmentPhone) {
        this.appointmentPhone = appointmentPhone;
    }

    public String getTeachers() {
        return teachers;
    }

    public void setTeachers(String teachers) {
        this.teachers = teachers;
    }

    public String getCourseCategory() {
        return courseCategory;
    }

    public void setCourseCategory(String courseCategory) {
        this.courseCategory = courseCategory;
    }

    public String getCoursePic() {
        return coursePic;
    }

    public void setCoursePic(String coursePic) {
        this.coursePic = coursePic;
    }

    public String getPostTeacher() {
        return postTeacher;
    }

    public void setPostTeacher(String postTeacher) {
        this.postTeacher = postTeacher;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getTeachingTime() {
        return teachingTime;
    }

    public void setTeachingTime(String teachingTime) {
        this.teachingTime = teachingTime;
    }

    public TeacherDetail getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherDetail teacher) {
        this.teacher = teacher;
    }

}