package com.manhattan.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by lk.zh on 2014/6/12.
 */
@Entity
@Table(name = "t_mht_homework_submit")
public class HomeworkSubmit {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name="homework_submit_id") private String homeworkSubmitId;
    @Column(name="homework_id") private String homeworkId;
    @Column(name="user_id") private String userId;
    @Column(name="homework_content") private String homeworkContent;
    @Column(name="submit_file") private String submitFile;//服务器上存放的文件名
    @Column(name="original_file_name")private String originalFileName;//原始文件名
    @Column(name="submit_time") private String submitTime;

    public String getHomeworkSubmitId() {
        return homeworkSubmitId;
    }

    public void setHomeworkSubmitId(String homeworkSubmitId) {
        this.homeworkSubmitId = homeworkSubmitId;
    }

    public String getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(String homeworkId) {
        this.homeworkId = homeworkId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSubmitFile() {
        return submitFile;
    }

    public void setSubmitFile(String submitFile) {
        this.submitFile = submitFile;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public String getHomeworkContent() {
        return homeworkContent;
    }

    public void setHomeworkContent(String homeworkContent) {
        this.homeworkContent = homeworkContent;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }
}
