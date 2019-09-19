package com.capinfo.entity;

import com.capinfo.base.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Table(name = "cap_yessw_case_process")
@ToString
@EqualsAndHashCode
public class CapYesswCaseProcess extends BaseEntity {

    @Id
    @GeneratedValue(generator = "JDBC")
    private String id;
    @Column(name = "yessw_number")
    private String yesswNumber;
    @Column(name = "accept_office")
    private String acceptOffice;
    @Column(name = "send_time")
    private String sendTime;
    @Column(name = "yessw_status")
    private String yesswStatus;
    @Column(name = "end_time")
    private String endTime;
    @Column(name = "yessw_case_id")
    private String yesswCaseId;

    @Transient
    private String orderFlag;

    @Column(name = "remark")
    private String remark;

    @Column(name = "solve_flag")
    private String solveFlag;//是否解决

    @Column(name = "pleased_flag")
    private String pleasedFlag;//是否满意

    @Column(name = "adjust_flag")
    private String adjustFlag;//是否经过指挥协调


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYesswNumber() {
        return yesswNumber;
    }

    public void setYesswNumber(String yesswNumber) {
        this.yesswNumber = yesswNumber;
    }

    public String getAcceptOffice() {
        return acceptOffice;
    }

    public void setAcceptOffice(String acceptOffice) {
        this.acceptOffice = acceptOffice;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getYesswStatus() {
        return yesswStatus;
    }

    public void setYesswStatus(String yesswStatus) {
        this.yesswStatus = yesswStatus;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getYesswCaseId() {
        return yesswCaseId;
    }

    public void setYesswCaseId(String yesswCaseId) {
        this.yesswCaseId = yesswCaseId;
    }

    public String getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(String orderFlag) {
        this.orderFlag = orderFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSolveFlag() {
        return solveFlag;
    }

    public void setSolveFlag(String solveFlag) {
        this.solveFlag = solveFlag;
    }

    public String getPleasedFlag() {
        return pleasedFlag;
    }

    public void setPleasedFlag(String pleasedFlag) {
        this.pleasedFlag = pleasedFlag;
    }

    public String getAdjustFlag() {
        return adjustFlag;
    }

    public void setAdjustFlag(String adjustFlag) {
        this.adjustFlag = adjustFlag;
    }
}
