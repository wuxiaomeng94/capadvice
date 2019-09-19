package com.capinfo.service;

import com.capinfo.base.BaseService;
import com.capinfo.entity.CapYesswCaseAnalyze;
import com.capinfo.entity.CapYesswCaseProcess;

import java.util.List;

public interface CapYesswCaseProcessService extends BaseService<CapYesswCaseProcess,String> {

    List<CapYesswCaseProcess> selectListByCondition(CapYesswCaseProcess capYesswCaseProcess);

    void saveByYesswCaseAnalyze(CapYesswCaseAnalyze capYesswCaseAnalyze, String acceptOffice);

    void saveProcess(CapYesswCaseProcess capYesswCaseProcess);

    void delProcess(String id);

}
