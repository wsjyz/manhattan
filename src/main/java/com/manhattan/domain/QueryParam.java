package com.manhattan.domain;

import java.util.Date;

/**
 * Created by Administrator on 2014/6/15 0015.
 */
public class QueryParam {

    String courseCategory;
    String place;
    String sex;
    Date appointmentTime;
    String tutoringWay;
    String keyword;

    public String getCourseCategory() {
        return courseCategory;
    }

    public void setCourseCategory(String courseCategory) {
        this.courseCategory = courseCategory;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTutoringWay() {
        return tutoringWay;
    }

    public void setTutoringWay(String tutoringWay) {
        this.tutoringWay = tutoringWay;
    }

    public Date getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Date appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
