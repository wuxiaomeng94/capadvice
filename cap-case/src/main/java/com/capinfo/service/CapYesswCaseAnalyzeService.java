package com.capinfo.service;

import com.capinfo.base.BaseService;
import com.capinfo.entity.CapYesswCaseAnalyze;
import com.capinfo.entity.CapYesswCaseInfo;
import com.capinfo.entity.CaseImportEntity;

import java.util.Date;
import java.util.List;

public interface CapYesswCaseAnalyzeService extends BaseService<CapYesswCaseAnalyze,String> {

    List<CapYesswCaseAnalyze> selectListByCondition(CapYesswCaseAnalyze capYesswCaseAnalyze);

    void saveYesswNumberAndAcceptOffice(CapYesswCaseAnalyze analyze);

    void saveInfoByYesswCaseInfo(CapYesswCaseInfo capYesswCaseInfo);

    void setCapYesswCaseAnalyzeRole(CapYesswCaseAnalyze capYesswCaseAnalyze);

    void delAnalyze(String id);

    String saveYesswNumberAndAcceptOfficeBackId(String yesswNumber, String acceptOffice);

    void saveWorkerNum(CaseImportEntity importEntity, String id);

    List<String> selectAcceptOfficeByConditionGroupBy(CapYesswCaseAnalyze capYesswCaseAnalyze);

}
