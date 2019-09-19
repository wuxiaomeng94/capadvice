package com.capinfo.vehicle.utilEntity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class ExcelEntity {
    //序号
    @Excel(name = "序号", orderNum = "1")
    private String index;
    //车牌号
    @Excel(name = "车牌号", orderNum = "2")
    private String plateNo;
    //入场时间
    @Excel(name = "入场时间", orderNum = "3")
    private String enterTime;
    //尾气检测次数
    @Excel(name = "尾气检测次数", orderNum = "4")
    private String gasTimes;
    //尾气检测总用时
    @Excel(name = "尾气检测总用时", orderNum = "5")
    private String gasAllSpend;
    //上线检测次数
    @Excel(name = "上线检测次数", orderNum = "6")
    private String onlineTimes;
    //灯光复检次数
    @Excel(name = "灯光复检次数", orderNum = "7")
    private String lightTimes;
    //上线检测总用时
    @Excel(name = "上线检测总用时", orderNum = "8")
    private String onlineAllSpend;
    //车辆检测总用时
    @Excel(name = "车辆检测总用时", orderNum = "9")
    private String checkSpend;
    //尾气检测入场时间
    @Excel(name = "尾气检测入场时间", orderNum = "10")
    private String gasTime;
    //尾气检测结果更新时间
    @Excel(name = "尾气检测结果更新时间", orderNum = "11")
    private String gasResultUpdateDate;
    //尾气检测用时
    @Excel(name = "尾气检测用时", orderNum = "12")
    private String gasSpend;
    //尾气一次复检入场时间
    @Excel(name = "尾气一次复检入场时间", orderNum = "13")
    private String gasOneTime;
    //尾气一次复检结果更新时间
    @Excel(name = "尾气一次复检结果更新时间", orderNum = "14")
    private String gasOneResultUpdateDate;
    //尾气一次复检用时
    @Excel(name = "尾气一次复检用时", orderNum = "15")
    private String gasOneSpend;
    //尾气二次复检入场时间
    @Excel(name = "尾气二次复检入场时间", orderNum = "16")
    private String gasTwoTime;
    //尾气二次复检结果更新时间
    @Excel(name = "尾气二次复检结果更新时间", orderNum = "17")
    private String gasTwoResultUpdateDate;
    //尾气二次复检用时
    @Excel(name = "尾气二次复检用时", orderNum = "18")
    private String gasTwoSpend;
    //上线检测入场时间
    @Excel(name = "上线检测入场时间", orderNum = "19")
    private String onlineTime;
    //上线检测结果更新时间
    @Excel(name = "上线检测结果更新时间", orderNum = "20")
    private String onlineResultUpdateDate;
    //上线检测用时
    @Excel(name = "上线检测用时", orderNum = "21")
    private String onlineSpend;
    //上线一次复检入场时间
    @Excel(name = "上线一次复检入场时间", orderNum = "22")
    private String onlineOneTime;
    //上线一次复检结果更新时间
    @Excel(name = "上线一次复检结果更新时间", orderNum = "23")
    private String onlineOneResultUpdateDate;
    //上线一次复检用时
    @Excel(name = "上线一次复检用时", orderNum = "24")
    private String onlineOneSpend;
    //上线二次复检入场时间
    @Excel(name = "上线二次复检入场时间", orderNum = "25")
    private String onlineTwoTime;
    //上线二次复检结果更新时间
    @Excel(name = "上线二次复检结果更新时间", orderNum = "26")
    private String onlineTwoResultUpdateDate;
    //上线二次复检用时
    @Excel(name = "上线二次复检用时", orderNum = "27")
    private String onlineTwoSpend;
    //灯光一次复检入场时间
    @Excel(name = "灯光一次复检入场时间", orderNum = "28")
    private String lightOneTime;
    //灯光一次复检结果更新时间
    @Excel(name = "灯光一次复检结果更新时间", orderNum = "29")
    private String lightOneResultUpdateDate;
    //灯光一次复检用时
    @Excel(name = "灯光一次复检用时", orderNum = "30")
    private String lightOneSpend;
    //灯光二次复检入场时间
    @Excel(name = "灯光二次复检入场时间", orderNum = "31")
    private String lightTwoTime;
    //灯光二次复检结果更新时间
    @Excel(name = "灯光二次复检结果更新时间", orderNum = "32")
    private String lightTwoResultUpdateDate;
    //灯光二次复检用时
    @Excel(name = "灯光二次复检用时", orderNum = "33")
    private String lightTwoSpend;



}
