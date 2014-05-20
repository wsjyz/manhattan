package com.manhattan.domain;

import javax.persistence.*;

/**
 * Created by lk.zh on 2014/5/20.
 */
@Entity
@Table(name = "t_mht_information")
public class Information {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String informationid;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "post_time")
    private String postTime;
    @Column(name = "status")
    private String status;

    public String getInformationid() {
        return informationid;
    }

    public void setInformationid(String informationid) {
        this.informationid = informationid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
