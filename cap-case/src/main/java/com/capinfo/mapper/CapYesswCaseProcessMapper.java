package com.capinfo.mapper;

import com.capinfo.entity.CapAcceptOffice;
import com.capinfo.entity.CapYesswCaseInfo;
import com.capinfo.entity.CapYesswCaseProcess;

import java.util.List;

public interface CapYesswCaseProcessMapper extends com.capinfo.base.BaseMapper<CapYesswCaseProcess,String> {

    List<CapYesswCaseProcess> selectListByCondition(CapYesswCaseProcess capYesswCaseProcess);

}
