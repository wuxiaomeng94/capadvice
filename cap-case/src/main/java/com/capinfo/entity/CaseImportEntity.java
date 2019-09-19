package com.capinfo.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

import java.util.Date;

@Data
@ExcelTarget("CaseImportEntity")
public class CaseImportEntity {

    @Excel(name = "工单编号", isImportField="工单编号")
    private String yesswNumber;
    @Excel(name = "问题标题")
    private String yesswTitle;
    @Excel(name = "承办单位")
    private String acceptOffice;
    @Excel(name = "是否联系", isImportField="是否联系")
    private String connectFlag;
    @Excel(name = "是否解决", isImportField="是否解决")
    private String solveFlag;
    @Excel(name = "是否满意", isImportField="是否满意")
    private String pleasedFlag;
    /*@Excel(name = "派单时间")
    private String sendCaseTime;*/
    @Excel(name = "工号")
    private String workerNum;

    @Excel(name = "填单时间")
    private String createDate;

    @Excel(name = "回复市中心时间")
    private String replyCityTime;

}
