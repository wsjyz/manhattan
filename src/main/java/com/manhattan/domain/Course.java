package com.manhattan.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/6/12 0012.
 */
//@TypeDefs(
//    {
//        @TypeDef(
//        name = "teacherDetailList",
//        defaultForType = List.class,
//        typeClass = TeacherList.class
//        )
//    }
//)

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
    @Column(name = "class_no")
    private String classNo;
    @Column(name = "start_time")
    private Date startTime;
    @Column(name = "end_time")
    private Date endTime;
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

    @Transient
    private Map<String,Object> extMap;

//    @ManyToOne(cascade=CascadeType.REFRESH,fetch=FetchType.LAZY)
//    @JoinColumn(name="post_teacher",insertable = false,updatable = false)
//    private TeacherDetail teacher;
//    @Type(type = "teacherDetailList")
    @Transient
    private List<TeacherDetail> teacherDetailList;

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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
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

//    public TeacherDetail getTeacher() {
//        return teacher;
//    }
//
//    public void setTeacher(TeacherDetail teacher) {
//        this.teacher = teacher;
//    }

    public List<TeacherDetail> getTeacherDetailList() {
        return teacherDetailList;
    }

    public void setTeacherDetailList(List<TeacherDetail> teacherDetailList) {
        this.teacherDetailList = teacherDetailList;
    }

    public String getClassNo() {
        return classNo;
    }

    public void setClassNo(String classNo) {
        this.classNo = classNo;
    }

    public Map<String, Object> getExtMap() {
        return extMap;
    }

    public void setExtMap(Map<String, Object> extMap) {
        this.extMap = extMap;
    }
}