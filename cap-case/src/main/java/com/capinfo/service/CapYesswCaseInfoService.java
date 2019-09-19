package com.capinfo.service;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.capinfo.base.BaseService;
import com.capinfo.entity.CapYesswCaseInfo;
import com.capinfo.utils.ResultData;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

public interface CapYesswCaseInfoService extends BaseService<CapYesswCaseInfo,String> {

    public ResultData saveCapYesswCaseInfo(String starttime, String endtime, String caseNum, String tokenStr) throws Exception;

    public Workbook bigExcel(int pageNum, CapYesswCaseInfo caseQuery, Workbook workbook, ExportParams exportParams);

    List<CapYesswCaseInfo> selectListByCondition(CapYesswCaseInfo capYesswCaseInfo);
}
