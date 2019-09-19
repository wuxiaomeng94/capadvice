package com.capinfo.enumUtils;

public enum SendCaseTypeEnum {


    SEND_CASE_TYPE_CITY("1", "市派单"),
    SEND_CASE_TYPE_BACK("2", "回退再派"),
    SEND_CASE_TYPE_DIRECTBACKCITY("3", "直退市中心"),
    SEND_CASE_TYPE_ROLLBACKCITY("4", "回退市中心"),
    SEND_CASE_TYPE_REPLYCITY("5", "回复市中心");


    private String type;
    private String name;

    SendCaseTypeEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
