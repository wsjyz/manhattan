package com.manhattan.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by lk.zh on 2014/5/20.
 */
@Entity
@Table(name = "t_mht_appointment")
public class Appointment {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name="appointment_id")
    private String appointmentId;
    @Column(name="user_id") private String userId;
    @Column(name="user_name") private String userName;
    @Column(name = "sex") private String sex;
    @Column(name = "course_category") private String courseCategory;
    @Column(name = "tutoring_way") private String tutoringWay;
    @Column(name = "area") private String area;
    @Column(name = "other_content") private String otherContent;
    @Column(name = "mobile") private String mobile;
    @Column(name = "phone") private String phone;
    @Column(name = "qq") private String qq;
    @Column(name = "address") private String address;
    @Column(name = "appointment_time") private String appointmentTime;
    @Column(name = "resource_type") private String resourceType;
    @Column(name = "resource_id")private String resourceId;
    @Column(name = "payment")private String payment;

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getCourseCategory() {
        return courseCategory;
    }

    public void setCourseCategory(String courseCategory) {
        this.courseCategory = courseCategory;
    }

    public String getTutoringWay() {
        return tutoringWay;
    }

    public void setTutoringWay(String tutoringWay) {
        this.tutoringWay = tutoringWay;
    }

    public String getOtherContent() {
        return otherContent;
    }

    public void setOtherContent(String otherContent) {
        this.otherContent = otherContent;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }
}
