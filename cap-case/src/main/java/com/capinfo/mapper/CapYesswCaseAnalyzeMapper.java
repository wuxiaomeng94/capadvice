package com.capinfo.mapper;

import com.capinfo.entity.CapYesswCaseAnalyze;

import java.util.List;

public interface CapYesswCaseAnalyzeMapper extends com.capinfo.base.BaseMapper<CapYesswCaseAnalyze,String> {

    List<CapYesswCaseAnalyze> selectListByCondition(CapYesswCaseAnalyze capYesswCaseAnalyze);

    List<String> selectAcceptOfficeByConditionGroupBy(CapYesswCaseAnalyze capYesswCaseAnalyze);

}
