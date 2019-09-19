package com.capinfo.service;

import com.capinfo.base.BaseService;
import com.capinfo.entity.CapAcceptOffice;
import com.capinfo.entity.CapYesswCaseAnalyze;
import com.capinfo.entity.CapYesswCaseInfo;

import java.util.List;

public interface CapAcceptOfficeService extends BaseService<CapAcceptOffice,String> {


    List<CapAcceptOffice> selectListByCondition(CapAcceptOffice capAcceptOffice);

    void save(CapAcceptOffice capAcceptOffice);

    void delOffice(CapAcceptOffice capAcceptOffice);

}
