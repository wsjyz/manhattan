package com.manhattan.domain;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2014/5/21 0021.
 */
@Entity
@Table(name = "t_mht_question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "question_id")
    private String questionId;
    @Column(name = "question_title")
    private String questionTitle;
    @Column(name = "question_content")
    private String questionContent;
    @Column(name = "question_pic")
    private String questionPic;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "reply_user")
    private String replyUser;
    @Column(name = "assign_teacher")
    private String assignTeacher;
    @Column(name = "answer")
    private String answer;
    @Column(name = "create_time")
    private Timestamp createTime;
    @Column(name = "answer_time")
    private Timestamp answerTime;


    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getQuestionPic() {
        return questionPic;
    }

    public void setQuestionPic(String questionPic) {
        this.questionPic = questionPic;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReplyUser() {
        return replyUser;
    }

    public void setReplyUser(String replyUser) {
        this.replyUser = replyUser;
    }

    public String getAssignTeacher() {
        return assignTeacher;
    }

    public void setAssignTeacher(String assignTeacher) {
        this.assignTeacher = assignTeacher;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(Timestamp answerTime) {
        this.answerTime = answerTime;
    }
}
