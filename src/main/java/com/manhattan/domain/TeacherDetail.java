package com.manhattan.domain;

import javax.persistence.*;

/**
 * Created by Administrator on 2014/5/21 0021.
 */
@Entity
@Table(name = "t_mht_teacher_detail")
public class TeacherDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private String userId;
    @Column(name = "course_category")
    private String course_category;
    @Column(name = "authentication")
    private String authentication;
    @Column(name = "credit_rate")
    private String creditRate;
    @Column(name = "wechat")
    private String wechat;
    @Column(name = "self_introduction")
    private String selfIntroduction;
    @Column(name = "video_url")
    private String videoUrl;
    @Column(name = "appointment_phone")
    private String appointmentPhone;
    @Column(name = "expert_score")
    private String expertScore;
    @Column(name = "expert_comments")
    private String expertComments;
    @Column(name = "teaching_area")
    private String teachingArea;
    @Column(name = "tutoring_way")
    private String tutoringWay;
    @Column(name = "class_fees")
    private String classFees;
    @Column(name = "teaching_time")
    private String teachingTime;
    @Column(name = "final_graduate_school")
    private String finalGraduateSchool;
    @Column(name = "specialty")
    private String specialty;
    @Column(name = "education_certificate")
    private String educationCertificate;
    @Column(name = "experience_exam")
    private String experienceExam;
    @Column(name = "exam_score")
    private String examScore;
    @Column(name = "exam_certificate")
    private String examCertificate;
    @Column(name = "teaching_experience")
    private String teachingExperience;
    @Column(name = "teaching_certificate")
    private String teachingCertificate;
    @Column(name = "student_max_score")
    private String studentMaxScore;
    @Column(name = "authentication_status")
    private String authenticationStatus;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCourse_category() {
        return course_category;
    }

    public void setCourse_category(String course_category) {
        this.course_category = course_category;
    }

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    public String getCreditRate() {
        return creditRate;
    }

    public void setCreditRate(String creditRate) {
        this.creditRate = creditRate;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getSelfIntroduction() {
        return selfIntroduction;
    }

    public void setSelfIntroduction(String selfIntroduction) {
        this.selfIntroduction = selfIntroduction;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getAppointmentPhone() {
        return appointmentPhone;
    }

    public void setAppointmentPhone(String appointmentPhone) {
        this.appointmentPhone = appointmentPhone;
    }

    public String getExpertScore() {
        return expertScore;
    }

    public void setExpertScore(String expertScore) {
        this.expertScore = expertScore;
    }

    public String getExpertComments() {
        return expertComments;
    }

    public void setExpertComments(String expertComments) {
        this.expertComments = expertComments;
    }

    public String getTeachingArea() {
        return teachingArea;
    }

    public void setTeachingArea(String teachingArea) {
        this.teachingArea = teachingArea;
    }

    public String getTutoringWay() {
        return tutoringWay;
    }

    public void setTutoringWay(String tutoringWay) {
        this.tutoringWay = tutoringWay;
    }

    public String getClassFees() {
        return classFees;
    }

    public void setClassFees(String classFees) {
        this.classFees = classFees;
    }

    public String getTeachingTime() {
        return teachingTime;
    }

    public void setTeachingTime(String teachingTime) {
        this.teachingTime = teachingTime;
    }

    public String getFinalGraduateSchool() {
        return finalGraduateSchool;
    }

    public void setFinalGraduateSchool(String finalGraduateSchool) {
        this.finalGraduateSchool = finalGraduateSchool;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getEducationCertificate() {
        return educationCertificate;
    }

    public void setEducationCertificate(String educationCertificate) {
        this.educationCertificate = educationCertificate;
    }

    public String getExperienceExam() {
        return experienceExam;
    }

    public void setExperienceExam(String experienceExam) {
        this.experienceExam = experienceExam;
    }

    public String getExamScore() {
        return examScore;
    }

    public void setExamScore(String examScore) {
        this.examScore = examScore;
    }

    public String getExamCertificate() {
        return examCertificate;
    }

    public void setExamCertificate(String examCertificate) {
        this.examCertificate = examCertificate;
    }

    public String getTeachingExperience() {
        return teachingExperience;
    }

    public void setTeachingExperience(String teachingExperience) {
        this.teachingExperience = teachingExperience;
    }

    public String getTeachingCertificate() {
        return teachingCertificate;
    }

    public void setTeachingCertificate(String teachingCertificate) {
        this.teachingCertificate = teachingCertificate;
    }

    public String getStudentMaxScore() {
        return studentMaxScore;
    }

    public void setStudentMaxScore(String studentMaxScore) {
        this.studentMaxScore = studentMaxScore;
    }

    public String getAuthenticationStatus() {
        return authenticationStatus;
    }

    public void setAuthenticationStatus(String authenticationStatus) {
        this.authenticationStatus = authenticationStatus;
    }
}
