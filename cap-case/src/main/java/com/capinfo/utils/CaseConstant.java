package com.capinfo.utils;

public class CaseConstant {


    public static String TOKEN_STR = "71785cf48c50b54008bf3bac6bee34c3";

    public static String getTokenStr() {
        return TOKEN_STR;
    }

    public static void setTokenStr(String tokenStr) {
        TOKEN_STR = tokenStr;
    }

    //===================================================================================================

    /**
     * 12345案件管理人员
     */
    public static final String YESSW_CASE_MANAGER = "cf2c4ecc5fd24191a8aa1cfd14a12f13";
    /**
     * 12345案件录入人员
     */
    public static final String YESSW_CASE_WORKER = "eb431e606e4b45079297752481bd2f21";


    /**
     * 12345案件状态是结案的    审核回复工单并完成
     */
    public static final String YESSW_STATUS_FINISH = "审核回复工单并完成";

}
