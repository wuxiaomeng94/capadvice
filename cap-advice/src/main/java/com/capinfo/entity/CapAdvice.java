package com.capinfo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Table(name = "cap_advice")
@ToString
@EqualsAndHashCode
public class CapAdvice {

    @Id
    @GeneratedValue(generator = "JDBC")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "reportedperson")
    private String reportedperson;

    @Column(name = "reporteddept")
    private String reporteddept;

    //@NotEmpty(message = "角色名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "file")
    private String file;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "type")
    private String type;

    @Column(name = "report_type")
    private String reportType;

    @Column(name = "status")
    private String status;

    @Column(name = "advice_code")
    private String adviceCode;

    @Column(name = "remark")
    private String remark;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "del_flag")
    private String delFlag;

    @Transient
    private String createDateStart;
    @Transient
    private String createDateEnd;

    @Transient
    private String typeStr;
    @Transient
    private String reportTypeStr;

    @Transient
    private String updateDateStr;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReportedperson() {
        return reportedperson;
    }

    public void setReportedperson(String reportedperson) {
        this.reportedperson = reportedperson;
    }

    public String getReporteddept() {
        return reporteddept;
    }

    public void setReporteddept(String reporteddept) {
        this.reporteddept = reporteddept;
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

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAdviceCode() {
        return adviceCode;
    }

    public void setAdviceCode(String adviceCode) {
        this.adviceCode = adviceCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
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

    public String getTypeStr() {
        String str = "";
        if ("1".equals(this.type)) {
            str = "举报问题";
        } else if ("2".equals(this.type)) {
            str = "意见建议";
        }
        typeStr = str;
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public String getReportTypeStr() {
        String str = "";
        if ("1".equals(this.reportType)) {
            str = "实名举报";
        } else if ("2".equals(this.reportType)) {
            str = "匿名举报";
        }
        reportTypeStr = str;
        return reportTypeStr;
    }

    public void setReportTypeStr(String reportTypeStr) {
        this.reportTypeStr = reportTypeStr;
    }

    public String getUpdateDateStr() {
        if (updateDate!=null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            this.updateDateStr = sdf.format(updateDate);
        }
        return updateDateStr;
    }

    public void setUpdateDateStr(String updateDateStr) {
        this.updateDateStr = updateDateStr;
    }
}
