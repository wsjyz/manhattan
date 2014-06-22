package com.manhattan.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2014/5/23 0023.
 */
@Entity
@Table(name = "t_mht_user_action")
public class UserAction {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "action_id")
    private String actionId;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "action_type")
    private String actionType;
    @Column(name = "action_Time")
    private Timestamp actionTime;
    @Column(name = "resource_id")
    private String resourceId;
    @Column(name = "status")
    private String status;

//    @ManyToOne(cascade=CascadeType.REFRESH,fetch = FetchType.LAZY)
//    @JoinColumn(name="courseId")
    @Transient
    private Course course;

//    @OneToOne
//    @JoinColumn(name="userId")
    @Transient
    private User user;

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public Timestamp getActionTime() {
        return actionTime;
    }

    public void setActionTime(Timestamp actionTime) {
        this.actionTime = actionTime;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
