package com.capinfo.entity;

import com.capinfo.base.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "cap_accept_office")
@ToString
@EqualsAndHashCode
public class CapAcceptOffice extends BaseEntity {

    @Id
    @GeneratedValue(generator = "JDBC")
    private String id;
    @Column(name = "pid")
    private String pid;
    @Column(name = "area_id")
    private String areaId;
    @Column(name = "name")
    private String name;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "sort")
    private String sort;
    @Column(name = "dept")
    private String dept;
    @Column(name = "deal_problem")
    private String dealProblem;
    @Column(name = "remark")
    private String remark;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getDealProblem() {
        return dealProblem;
    }

    public void setDealProblem(String dealProblem) {
        this.dealProblem = dealProblem;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
