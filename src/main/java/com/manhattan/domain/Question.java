package com.manhattan.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2014/5/21 0021.
 */
@Entity
@Table(name = "t_mht_question")
public class Question {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
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
    @Column(name = "status")
    private String status;
    @Column(name = "answer")
    private String answer;
    @Column(name = "answer_pic")
    private String answerPic;
    @Column(name = "create_time")
    private String createTime;
    @Column(name = "answer_time")
    private String answerTime;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id",insertable = false,updatable = false)
    private User askUser;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "reply_user",insertable = false,updatable = false)
    private User repUser;

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(String answerTime) {
        this.answerTime = answerTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAnswerPic() {
        return answerPic;
    }

    public void setAnswerPic(String answerPic) {
        this.answerPic = answerPic;
    }

    public User getAskUser() {
        return askUser;
    }

    public void setAskUser(User askUser) {
        this.askUser = askUser;
    }

    public User getRepUser() {
        return repUser;
    }

    public void setRepUser(User repUser) {
        this.repUser = repUser;
    }
}
