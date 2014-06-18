package com.manhattan.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by lk.zh on 2014/6/12.
 */
@Entity
@Table(name = "t_mht_teacher_homework")
public class HomeWork {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column (name="homework_id")private String homeworkId;
    @Column (name="homework_title")private String homeworkTitle;
    @Column (name="teacher_id")private String teacherId;
    @Column (name="post_time")private String postTime;
    @Column (name="homework_file")private String homeworkFile;
    @Column (name="user_id")private String userId;

    public String getHomeworkTitle() {
        return homeworkTitle;
    }

    public void setHomeworkTitle(String homeworkTitle) {
        this.homeworkTitle = homeworkTitle;
    }

    public String getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(String homeworkId) {
        this.homeworkId = homeworkId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getHomeworkFile() {
        return homeworkFile;
    }

    public void setHomeworkFile(String homeworkFile) {
        this.homeworkFile = homeworkFile;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}