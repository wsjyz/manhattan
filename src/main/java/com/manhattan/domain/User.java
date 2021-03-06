package com.manhattan.domain;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by lk.zh on 2014/5/19.
 */
@Entity
@Table(name = "t_mht_user")
public class User {


    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid") 
    @Column(name="user_id")
    private String userId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "password")
    private String password;
    @Column(name = "auth_code")
    private String authCode;
    @Column(name = "type")
    private String type;
    @Column(name = "avatar")
    private String avatar;
    @Column(name = "sex")
    private String sex;
    @Column(name = "mobile")
    private String mobile;
    @Column(name = "email")
    private String email;
    @Column(name = "address")
    private String address;
    @Column(name = "credit")
    private Integer credits;
    @Column(name = "wallet")
    private BigDecimal wallet;
    @Column(name = "vip_end_time")
    private Date vipExpiredTime;
    @Column
    private String evaluation;
    @Column private String area;
    @Column(name = "last_login_time") private Date lastLoginTime;
    @Column private String status;
    @Column(name = "payment_method") private String paymentMethod;
//    @OneToMany(cascade=CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy="postTeacher")
//    private List<Course> courses;
//    @OneToOne(fetch = FetchType.LAZY,optional = false)
//    @PrimaryKeyJoinColumn

    @Transient
    private TeacherDetail teacherDetail;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Date getVipExpiredTime() {
        return vipExpiredTime;
    }

    public void setVipExpiredTime(Date vipExpiredTime) {
        this.vipExpiredTime = vipExpiredTime;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getWallet() {
        return wallet;
    }

    public void setWallet(BigDecimal wallet) {
        this.wallet = wallet;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TeacherDetail getTeacherDetail() {
        return teacherDetail;
    }

    public void setTeacherDetail(TeacherDetail teacherDetail) {
        this.teacherDetail = teacherDetail;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
