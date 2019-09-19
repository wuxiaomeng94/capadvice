package com.capinfo.mapper;

import com.capinfo.entity.CapYesswCaseInfo;

import java.util.List;

public interface CapYesswCaseInfoMapper extends com.capinfo.base.BaseMapper<CapYesswCaseInfo,String> {

    List<CapYesswCaseInfo> selectListByCondition(CapYesswCaseInfo capYesswCaseInfo);
}
