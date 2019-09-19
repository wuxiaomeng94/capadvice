package com.capinfo.mapper;

import com.capinfo.entity.CapAcceptOffice;
import com.capinfo.entity.CapYesswCaseAnalyze;

import java.util.List;

public interface CapAccpetOfficeMapper extends com.capinfo.base.BaseMapper<CapAcceptOffice,String> {

    List<CapAcceptOffice> selectListByCondition(CapAcceptOffice capAcceptOffice);
}
