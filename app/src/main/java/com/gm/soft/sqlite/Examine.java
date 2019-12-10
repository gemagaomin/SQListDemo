package com.gm.soft.sqlite;

import java.util.Date;

public class Examine {
    private String id;
    private String userId;
    private String type;
    private String detail;
    private String remark;
    private String status;
    private String problemId;
    private String phonePath;
    private Date startTime;
    private Date   endTime;
    private Date   appTime;
    private Date   startVerificationTime;
    private Date   endVerificationTime;
    private String other;
    private String verificationId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProblemId() {
        return problemId;
    }

    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }

    public String getPhonePath() {
        return phonePath;
    }

    public void setPhonePath(String phonePath) {
        this.phonePath = phonePath;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getAppTime() {
        return appTime;
    }

    public void setAppTime(Date appTime) {
        this.appTime = appTime;
    }

    public Date getStartVerificationTime() {
        return startVerificationTime;
    }

    public void setStartVerificationTime(Date startVerificationTime) {
        this.startVerificationTime = startVerificationTime;
    }

    public Date getEndVerificationTime() {
        return endVerificationTime;
    }

    public void setEndVerificationTime(Date endVerificationTime) {
        this.endVerificationTime = endVerificationTime;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getVerificationId() {
        return verificationId;
    }

    public void setVerificationId(String verificationId) {
        this.verificationId = verificationId;
    }
}
