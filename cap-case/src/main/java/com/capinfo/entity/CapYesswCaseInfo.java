package com.capinfo.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.capinfo.base.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;

@Table(name = "cap_yessw_case_info")
@ToString
@EqualsAndHashCode
public class CapYesswCaseInfo extends BaseEntity {

    @Id
    @GeneratedValue(generator = "JDBC")
    private String id;

    @Excel(name = "序号", orderNum = "1")
    @Column(name = "number")
    private String number;
    @Excel(name = "工单编号", orderNum = "2")
    @Column(name = "yessw_number")
    private String yesswNumber;
    @Excel(name = "主要内容", orderNum = "3")
    @Column(name = "yessw_content")
    private String yesswContent;
    @Excel(name = "问题点位", orderNum = "4")
    @Column(name = "yessw_problem_place")
    private String yesswProblemPlace;
    @Excel(name = "来电人", orderNum = "5")
    @Column(name = "yessw_callperson")
    private String yesswCallperson;
    @Excel(name = "联系方式", orderNum = "6")
    @Column(name = "yessw_callperson_phone")
    private String yesswCallpersonPhone;
    @Excel(name = "被反映区", orderNum = "7")
    @Column(name = "yessw_asked_area")
    private String yesswAskedArea;
    @Excel(name = "被反映街道", orderNum = "8")
    @Column(name = "yessw_asked_street")
    private String yesswAskedStreet;
    @Excel(name = "问题标题", orderNum = "9")
    @Column(name = "yessw_title")
    private String yesswTitle;
    @Excel(name = "创建时间", orderNum = "10")
    @Column(name = "yessw_create_time")
    private String yesswCreateTime;
    @Excel(name = "派单时间", orderNum = "11")
    @Column(name = "yessw_send_time")
    private String yesswSendTime;
    @Excel(name = "问题分类", orderNum = "12")
    @Column(name = "yessw_problem_type")
    private String yesswProblemType;
    @Excel(name = "一级问题分类", orderNum = "13")
    @Column(name = "problem_type_level1")
    private String problemTypeLevel1;
    @Excel(name = "二级问题分类", orderNum = "14")
    @Column(name = "problem_type_level2")
    private String problemTypeLevel2;
    @Excel(name = "三级问题分类", orderNum = "15")
    @Column(name = "problem_type_level3")
    private String problemTypeLevel3;
    @Excel(name = "四级问题分类", orderNum = "16")
    @Column(name = "problem_type_level4")
    private String problemTypeLevel4;
    @Excel(name = "五级问题分类", orderNum = "17")
    @Column(name = "problem_type_level5")
    private String problemTypeLevel5;
    //@Excel(name = "承办单位", orderNum = "18")
    @Column(name = "yessw_accept_office")
    private String yesswAcceptOffice;
    @Excel(name = "承办单位", orderNum = "18")
    @Column(name = "accept_office")
    private String acceptOffice;


    @Excel(name = "工单状态", orderNum = "19")
    @Column(name = "yessw_status")
    private String yesswStatus;
    @Excel(name = "工单类型", orderNum = "20")
    @Column(name = "yessw_type")
    private String yesswType;
    @Excel(name = "处理结果", orderNum = "21")
    @Column(name = "yessw_result")
    private String yesswResult;
    @Excel(name = "办结时间", orderNum = "22")
    @Column(name = "yessw_end_time")
    private String yesswEndTime;


    //@Excel(name = "承办单位", orderNum = "18")//展示和导出用这个属性。group by之后可能会有多条的。只找最后一条的承办单位
    @Transient
    private String finalAcceptOffice;

    @Excel(name = "承办单位流转过程", orderNum = "23")
    @Column(name = "accept_office_process")
    private String acceptOfficeProcess;//承办单位的经过流转。转成一个完整的字符串作为导出excel的一列

    //@Excel(name = "是否联系", orderNum = "24")
    @Column(name = "connect_flag")
    private String connectFlag;
    //@Excel(name = "是否解决", orderNum = "25")
    @Column(name = "solve_flag")
    private String solveFlag;
    //@Excel(name = "是否满意", orderNum = "26")
    @Column(name = "pleased_flag")
    private String pleasedFlag;

    @Column(name = "case_finish_time")
    private String caseFinishTime;//案件截止日期  立案时间加7天 yesswCreateTime+7



    //查询条件  案件时间起
    @Transient
    private String yesswCreatetimeStart;
    //查询条件  案件时间止
    @Transient
    private String yesswCreatetimeEnd;
    //呼叫时间  起   查询条件
    @Transient
    private String yesswSendtimeStart;
    //呼叫时间  止   查询条件
    @Transient
    private String yesswSendtimeEnd;
    @Transient
    private String groupByFlag;//查询条件中用。是否需要group by    1是2否
    @Transient
    private String yesswStatusNotQuery;//查询时查询条件用。12345案件状态不为。。。

    @Transient
    private String problemTypeLevel;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getYesswNumber() {
        return yesswNumber;
    }

    public void setYesswNumber(String yesswNumber) {
        this.yesswNumber = yesswNumber;
    }

    public String getYesswContent() {
        return yesswContent;
    }

    public void setYesswContent(String yesswContent) {
        this.yesswContent = yesswContent;
    }

    public String getYesswProblemPlace() {
        return yesswProblemPlace;
    }

    public void setYesswProblemPlace(String yesswProblemPlace) {
        this.yesswProblemPlace = yesswProblemPlace;
    }

    public String getYesswCallperson() {
        return yesswCallperson;
    }

    public void setYesswCallperson(String yesswCallperson) {
        this.yesswCallperson = yesswCallperson;
    }

    public String getYesswCallpersonPhone() {
        return yesswCallpersonPhone;
    }

    public void setYesswCallpersonPhone(String yesswCallpersonPhone) {
        this.yesswCallpersonPhone = yesswCallpersonPhone;
    }

    public String getYesswAskedArea() {
        return yesswAskedArea;
    }

    public void setYesswAskedArea(String yesswAskedArea) {
        this.yesswAskedArea = yesswAskedArea;
    }

    public String getYesswAskedStreet() {
        return yesswAskedStreet;
    }

    public void setYesswAskedStreet(String yesswAskedStreet) {
        this.yesswAskedStreet = yesswAskedStreet;
    }

    public String getYesswTitle() {
        return yesswTitle;
    }

    public void setYesswTitle(String yesswTitle) {
        this.yesswTitle = yesswTitle;
    }

    public String getYesswCreateTime() {
        return yesswCreateTime;
    }

    public void setYesswCreateTime(String yesswCreateTime) {
        this.yesswCreateTime = yesswCreateTime;
    }

    public String getYesswSendTime() {
        return yesswSendTime;
    }

    public void setYesswSendTime(String yesswSendTime) {
        this.yesswSendTime = yesswSendTime;
    }

    public String getYesswProblemType() {
        return yesswProblemType;
    }

    public void setYesswProblemType(String yesswProblemType) {
        this.yesswProblemType = yesswProblemType;
    }

    public String getProblemTypeLevel1() {
        return problemTypeLevel1;
    }

    public void setProblemTypeLevel1(String problemTypeLevel1) {
        this.problemTypeLevel1 = problemTypeLevel1;
    }

    public String getProblemTypeLevel2() {
        return problemTypeLevel2;
    }

    public void setProblemTypeLevel2(String problemTypeLevel2) {
        this.problemTypeLevel2 = problemTypeLevel2;
    }

    public String getProblemTypeLevel3() {
        return problemTypeLevel3;
    }

    public void setProblemTypeLevel3(String problemTypeLevel3) {
        this.problemTypeLevel3 = problemTypeLevel3;
    }

    public String getProblemTypeLevel4() {
        return problemTypeLevel4;
    }

    public void setProblemTypeLevel4(String problemTypeLevel4) {
        this.problemTypeLevel4 = problemTypeLevel4;
    }

    public String getProblemTypeLevel5() {
        return problemTypeLevel5;
    }

    public void setProblemTypeLevel5(String problemTypeLevel5) {
        this.problemTypeLevel5 = problemTypeLevel5;
    }

    public String getYesswAcceptOffice() {
        return yesswAcceptOffice;
    }

    public void setYesswAcceptOffice(String yesswAcceptOffice) {
        this.yesswAcceptOffice = yesswAcceptOffice;
    }

    public String getAcceptOffice() {
        return acceptOffice;
    }

    public void setAcceptOffice(String acceptOffice) {
        this.acceptOffice = acceptOffice;
    }

    public String getYesswStatus() {
        return yesswStatus;
    }

    public void setYesswStatus(String yesswStatus) {
        this.yesswStatus = yesswStatus;
    }

    public String getYesswType() {
        return yesswType;
    }

    public void setYesswType(String yesswType) {
        this.yesswType = yesswType;
    }

    public String getYesswResult() {
        return yesswResult;
    }

    public void setYesswResult(String yesswResult) {
        this.yesswResult = yesswResult;
    }

    public String getYesswEndTime() {
        return yesswEndTime;
    }

    public void setYesswEndTime(String yesswEndTime) {
        this.yesswEndTime = yesswEndTime;
    }

    public String getFinalAcceptOffice() {
        return finalAcceptOffice;
    }

    public void setFinalAcceptOffice(String finalAcceptOffice) {
        this.finalAcceptOffice = finalAcceptOffice;
    }

    public String getAcceptOfficeProcess() {
        return acceptOfficeProcess;
    }

    public void setAcceptOfficeProcess(String acceptOfficeProcess) {
        this.acceptOfficeProcess = acceptOfficeProcess;
    }

    public String getConnectFlag() {
        return connectFlag;
    }

    public void setConnectFlag(String connectFlag) {
        this.connectFlag = connectFlag;
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

    public String getCaseFinishTime() {
        return caseFinishTime;
    }

    public void setCaseFinishTime(String caseFinishTime) {
        this.caseFinishTime = caseFinishTime;
    }

    public String getYesswCreatetimeStart() {
        return yesswCreatetimeStart;
    }

    public void setYesswCreatetimeStart(String yesswCreatetimeStart) {
        this.yesswCreatetimeStart = yesswCreatetimeStart;
    }

    public String getYesswCreatetimeEnd() {
        return yesswCreatetimeEnd;
    }

    public void setYesswCreatetimeEnd(String yesswCreatetimeEnd) {
        this.yesswCreatetimeEnd = yesswCreatetimeEnd;
    }

    public String getYesswSendtimeStart() {
        return yesswSendtimeStart;
    }

    public void setYesswSendtimeStart(String yesswSendtimeStart) {
        this.yesswSendtimeStart = yesswSendtimeStart;
    }

    public String getYesswSendtimeEnd() {
        return yesswSendtimeEnd;
    }

    public void setYesswSendtimeEnd(String yesswSendtimeEnd) {
        this.yesswSendtimeEnd = yesswSendtimeEnd;
    }

    public String getGroupByFlag() {
        return groupByFlag;
    }

    public void setGroupByFlag(String groupByFlag) {
        this.groupByFlag = groupByFlag;
    }

    public String getYesswStatusNotQuery() {
        return yesswStatusNotQuery;
    }

    public void setYesswStatusNotQuery(String yesswStatusNotQuery) {
        this.yesswStatusNotQuery = yesswStatusNotQuery;
    }

    public String getProblemTypeLevel() {
        String str = this.problemTypeLevel1+"=>"+this.problemTypeLevel2+"=>"+this.problemTypeLevel3+"=>"+this.problemTypeLevel4+"=>"+this.problemTypeLevel5;
        problemTypeLevel = str;
        return problemTypeLevel;
    }

    public void setProblemTypeLevel(String problemTypeLevel) {
        this.problemTypeLevel = problemTypeLevel;
    }

}
