package com.capinfo.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.capinfo.base.BaseEntity;
import com.capinfo.mapper.SysUserMapper;
import com.capinfo.service.SysUserService;
import com.capinfo.utils.DateUtils;
import com.capinfo.utils.LoadBeanUtils;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.Date;

@Table(name = "cap_yessw_case_analyze")
@ToString
@EqualsAndHashCode
public class CapYesswCaseAnalyze extends BaseEntity {
    //SysUserMapper sysUserMapper
    //private SysUserMapper sysUserMapper = (SysUserMapper) LoadBeanUtils.getBean("sysUserMapper");

    @Id
    @GeneratedValue(generator = "JDBC")
    private String id;

    //@Excel(name = "序号", orderNum = "1")
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





    //@Excel(name = "承办单位", orderNum = "18")//展示和导出用这个属性。group by之后可能会有多条的。只找最后一条的承办单位
    @Transient
    private String finalAcceptOffice;

    @Excel(name = "承办单位流转过程", orderNum = "19")
    @Column(name = "accept_office_process")
    private String acceptOfficeProcess;//承办单位的经过流转。转成一个完整的字符串作为导出excel的一列

    @Excel(name = "承办单位转派时间", orderNum = "20")
    @Column(name = "accept_office_time")
    private String acceptOfficeTime;


    @Excel(name = "工单状态", orderNum = "21")
    @Column(name = "yessw_status")
    private String yesswStatus;
    @Excel(name = "工单类型", orderNum = "22")
    @Column(name = "yessw_type")
    private String yesswType;
    @Excel(name = "处理结果", orderNum = "23")
    @Column(name = "yessw_result")
    private String yesswResult;
    @Excel(name = "办结时间", orderNum = "24")
    @Column(name = "yessw_end_time")
    private String yesswEndTime;

    @Transient
    private String createDateStr;//填单时间。工作人员录入每条数据的时间

    @Column(name = "send_casetype")
    private String sendCaseType;
    @Excel(name = "派单类型", orderNum = "26")
    @Transient
    private String sendCaseTypeStr;

    @Column(name = "worker_num")
    private String workerNum;
    @Column(name = "send_case_time")
    private String sendCaseTime;

    @Excel(name = "提交人", orderNum = "27")
    @Transient
    private String createByStr;//创建人用户名


    //@Excel(name = "是否联系", orderNum = "24")
    @Column(name = "connect_flag")
    private String connectFlag;
    @Excel(name = "是否解决", orderNum = "28")
    @Column(name = "solve_flag")
    private String solveFlag;
    @Excel(name = "是否满意", orderNum = "29")
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
    //角色标识。查询时候区分是工作人员还是管理人员查看。如果是工作人员则只能看到自己录入的案件
    //工作人员:worker   管理人员admin
    @Transient
    private String roleFlag;
    //当前用户id
    @Transient
    private String currentUserId;
    @Transient
    private String createDateStart;
    @Transient
    private String createDateEnd;
    @Transient
    private String queryPageFlag;//是否是页面查询用的标记1是2否



    @Column(name = "finish_time")
    private Date finishTime;//到期时间

    @Transient
    private String finishTimeStr;//到期时间字符串。先这么弄


    //@Excel(name = "回复时间", orderNum = "30")
    @Column(name = "recovery_time")
    private String recoveryTime;//回复时间

    @Transient
    private String recoveryTimeStart;//回复时间开始   查询条件用

    @Transient
    private String recoveryTimeEnd;//回复时间结束     查询条件用



    @Excel(name = "是否经过指挥协调", orderNum = "31")
    @Column(name = "adjust_flag")
    private String adjustFlag;

    @Column(name = "reply_city_time")
    private Date replyCityTime;//回复市中心时间

    @Transient
    private String replyCityTimeStr;

    @Excel(name = "回复市中心时间", orderNum = "30")
    @Transient
    private String replyCityTimeExcel;

    /*@Excel(name = "首次派遣单位", orderNum = "32")
    @Transient
    private String officeStrOne;*/

    @Excel(name = "序号", orderNum = "1")
    @Transient
    private String indexStr;//导出excel中的序号字段

    @Excel(name = "是否接近到期时间", orderNum = "32")
    @Transient
    private String closeFinishTimeFlag;//是否接近到期时间标识。导出的excel中标出用到

    @Column(name = "create_case_time")
    private Date createCaseTime;//创建案件时间。原本用创建时间字段。需要可修改就别这么搞了。最开始录进去的时间还是留一个记录

    @Excel(name = "填单时间", orderNum = "25")
    @Transient
    private String createCaseTimeStr;



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

    public String getRoleFlag() {
        return roleFlag;
    }

    public void setRoleFlag(String roleFlag) {
        this.roleFlag = roleFlag;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public String getCreateDateStart() {
        return createDateStart;
    }

    public void setCreateDateStart(String createDateStart) {
        this.createDateStart = createDateStart;
    }

    public String getCreateDateEnd() {
        return createDateEnd;
    }

    public void setCreateDateEnd(String createDateEnd) {
        this.createDateEnd = createDateEnd;
    }

    public String getQueryPageFlag() {
        return queryPageFlag;
    }

    public void setQueryPageFlag(String queryPageFlag) {
        this.queryPageFlag = queryPageFlag;
    }

    public String getCreateByStr() {
        /*if ("1".equals(this.queryPageFlag)) {
            SysUser sysUser = sysUserMapper.selectByPrimaryKey(this.getCreateBy());
            if (sysUser!=null) {
                createByStr = sysUser.getUsername();
            }
        }*/
        return createByStr;
    }

    public void setCreateByStr(String createByStr) {
        this.createByStr = createByStr;
    }

    public String getCreateDateStr() {
        /*if (this.getUpdateDate()!=null) {//瞎搞嘛，赶紧改过来
            String str = DateUtils.SDF_DATETIME.format(this.getUpdateDate());
            createDateStr = str;
        }*/
        if (this.getCreateDate() != null) {
            String str = DateUtils.SDF_DATETIME.format(this.getCreateDate());
            createDateStr = str;
        }
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

    public String getSendCaseType() {
        return sendCaseType;
    }

    public void setSendCaseType(String sendCaseType) {
        this.sendCaseType = sendCaseType;
    }

    public String getSendCaseTypeStr() {
        if (StringUtils.isNotBlank(this.sendCaseType)) {
            if ("1".equals(this.sendCaseType)) {
                sendCaseTypeStr = "市派单";
            } else if ("2".equals(this.sendCaseType)) {
                sendCaseTypeStr = "回退再派";
            } else if ("3".equals(this.sendCaseType)) {
                sendCaseTypeStr = "直退市中心";
            } else if ("4".equals(this.sendCaseType)) {
                sendCaseTypeStr = "回退市中心";
            } else if ("5".equals(this.sendCaseType)) {
                sendCaseTypeStr = "回复市中心";
            }
        }
        return sendCaseTypeStr;
    }

    public void setSendCaseTypeStr(String sendCaseTypeStr) {
        this.sendCaseTypeStr = sendCaseTypeStr;
    }

    public String getWorkerNum() {
        return workerNum;
    }

    public void setWorkerNum(String workerNum) {
        this.workerNum = workerNum;
    }

    public String getSendCaseTime() {
        return sendCaseTime;
    }

    public void setSendCaseTime(String sendCaseTime) {
        this.sendCaseTime = sendCaseTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getRecoveryTime() {
        return recoveryTime;
    }

    public void setRecoveryTime(String recoveryTime) {
        this.recoveryTime = recoveryTime;
    }

    public String getAcceptOfficeTime() {
        return acceptOfficeTime;
    }

    public void setAcceptOfficeTime(String acceptOfficeTime) {
        this.acceptOfficeTime = acceptOfficeTime;
    }


    public String getFinishTimeStr() {
        if (this.finishTime != null) {
            finishTimeStr = DateUtils.SDF_DATETIME.format(this.finishTime);
        }
        return finishTimeStr;
    }

    public void setFinishTimeStr(String finishTimeStr) {
        this.finishTimeStr = finishTimeStr;
    }

    public String getAdjustFlag() {
        return adjustFlag;
    }

    public void setAdjustFlag(String adjustFlag) {
        this.adjustFlag = adjustFlag;
    }


    /*public String getOfficeStrOne() {
        if (StringUtils.isNotBlank(this.acceptOfficeProcess)) {
            String[] split = this.acceptOfficeProcess.split("、");
            if (split.length>=1) {
                String officeStrOneStr = split[1];
                officeStrOne = officeStrOneStr;
            }
        }
        return officeStrOne;
    }

    public void setOfficeStrOne(String officeStrOne) {
        this.officeStrOne = officeStrOne;
    }*/


    public Date getReplyCityTime() {
        return replyCityTime;
    }

    public void setReplyCityTime(Date replyCityTime) {
        this.replyCityTime = replyCityTime;
    }

    public String getReplyCityTimeStr() {
        if (this.replyCityTime != null) {
            replyCityTimeStr = DateUtils.SDF_DATETIME.format(this.replyCityTime);
        }
        return replyCityTimeStr;
    }

    public String getReplyCityTimeExcel() {
        if (this.replyCityTime != null) {
            replyCityTimeExcel = DateUtils.SDF_DATETIME.format(this.replyCityTime);
        } else {
            //如果回复时中心时间（人工手动填写的回复时间）为空，显示每条转派过程的创建时间
            if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(recoveryTime)) {
                replyCityTimeExcel = recoveryTime;
            }
        }
        return replyCityTimeExcel;
    }

    public void setReplyCityTimeExcel(String replyCityTimeExcel) {
        this.replyCityTimeExcel = replyCityTimeExcel;
    }

    public void setReplyCityTimeStr(String replyCityTimeStr) {
        this.replyCityTimeStr = replyCityTimeStr;
    }

    public String getRecoveryTimeStart() {
        return recoveryTimeStart;
    }

    public void setRecoveryTimeStart(String recoveryTimeStart) {
        this.recoveryTimeStart = recoveryTimeStart;
    }

    public String getRecoveryTimeEnd() {
        return recoveryTimeEnd;
    }

    public void setRecoveryTimeEnd(String recoveryTimeEnd) {
        this.recoveryTimeEnd = recoveryTimeEnd;
    }

    public String getIndexStr() {
        return indexStr;
    }

    public void setIndexStr(String indexStr) {
        this.indexStr = indexStr;
    }


    public String getCloseFinishTimeFlag() {
        Date nowDate = new Date();
        Date finishDate = this.getFinishTime();
        Date replyCityTime = this.replyCityTime;
        if (finishDate != null) {
            //int days = DateUtils.differentDaysByMillisecond(finishDate, nowDate);
            int days = DateUtils.differentDaysByMillisecond(nowDate, finishDate);
            if (days < 1 && replyCityTime == null) {
                closeFinishTimeFlag = "是";
            }
        }
        
        return closeFinishTimeFlag;
    }

    public void setCloseFinishTimeFlag(String closeFinishTimeFlag) {
        this.closeFinishTimeFlag = closeFinishTimeFlag;
    }

    public Date getCreateCaseTime() {
        return createCaseTime;
    }

    public void setCreateCaseTime(Date createCaseTime) {
        this.createCaseTime = createCaseTime;
    }

    public String getCreateCaseTimeStr() {
        if (this.createCaseTime != null) {
            String str = DateUtils.SDF_DATETIME.format(this.createCaseTime);
            createCaseTimeStr = str;
        }
        return createCaseTimeStr;
    }

    public void setCreateCaseTimeStr(String createCaseTimeStr) {
        this.createCaseTimeStr = createCaseTimeStr;
    }
}
