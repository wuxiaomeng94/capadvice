package com.capinfo.service.impl;

import com.capinfo.base.BaseMapper;
import com.capinfo.base.CurrentUser;
import com.capinfo.base.impl.BaseServiceImpl;
import com.capinfo.entity.CapAcceptOffice;
import com.capinfo.entity.CapYesswCaseAnalyze;
import com.capinfo.entity.CapYesswCaseInfo;
import com.capinfo.entity.CapYesswCaseProcess;
import com.capinfo.mapper.CapYesswCaseProcessMapper;
import com.capinfo.service.CapAcceptOfficeService;
import com.capinfo.service.CapYesswCaseAnalyzeService;
import com.capinfo.service.CapYesswCaseInfoService;
import com.capinfo.service.CapYesswCaseProcessService;
import com.capinfo.util.BeanUtil;
import com.capinfo.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CapYesswCaseProcessServiceImpl extends BaseServiceImpl<CapYesswCaseProcess,String> implements CapYesswCaseProcessService {

    @Autowired
    private CapYesswCaseProcessMapper capYesswCaseProcessMapper;
    @Autowired
    private CapYesswCaseAnalyzeService capYesswCaseAnalyzeService;
    @Autowired
    private CapYesswCaseInfoService capYesswCaseInfoService;


    @Override
    public BaseMapper<CapYesswCaseProcess, String> getMappser() {
        return capYesswCaseProcessMapper;
    }

    @Override
    public List<CapYesswCaseProcess> selectListByCondition(CapYesswCaseProcess capYesswCaseProcess) {
        return capYesswCaseProcessMapper.selectListByCondition(capYesswCaseProcess);
    }

    @Override
    public void saveByYesswCaseAnalyze(CapYesswCaseAnalyze capYesswCaseAnalyze, String acceptOffice) {
        CurrentUser currentUser = (CurrentUser) SecurityUtils.getSubject().getSession().getAttribute("curentUser");
        CapYesswCaseProcess process = new CapYesswCaseProcess();
        process.setId(UUID.randomUUID().toString().replaceAll("-", "").toLowerCase());
        process.setAcceptOffice(acceptOffice);
        process.setYesswNumber(capYesswCaseAnalyze.getYesswNumber());
        process.setYesswCaseId(capYesswCaseAnalyze.getId());
        process.setCreateBy(currentUser.getId());
        process.setCreateDate(new Date());
        process.setUpdateBy(currentUser.getId());
        process.setUpdateDate(new Date());
        this.insertSelective(process);

        //这里要拿到最终承办单位和承办单位的list拼接出来的字符串，放回到对应的analyze表里和12345信息表里
        String yesswCaseId = process.getYesswCaseId();
        CapYesswCaseProcess capYesswCaseProcess = new CapYesswCaseProcess();
        capYesswCaseProcess.setYesswCaseId(yesswCaseId);
        //capYesswCaseProcess.setOrderFlag("asc");
        List<CapYesswCaseProcess> processList = capYesswCaseProcessMapper.selectListByCondition(capYesswCaseProcess);
        saveYesswCaseAnalyzeByProcessList(yesswCaseId, processList);
    }

    @Override
    public void saveProcess(CapYesswCaseProcess capYesswCaseProcess) {
        String id = capYesswCaseProcess.getId();
        CurrentUser currentUser = (CurrentUser) SecurityUtils.getSubject().getSession().getAttribute("curentUser");
        if (StringUtils.isNotBlank(id)) {
            /*CapYesswCaseProcess newProcess = capYesswCaseProcessMapper.selectByPrimaryKey(id);
            newProcess.setUpdateDate(new Date());
            newProcess.setUpdateBy(currentUser.getId());
            newProcess.setAcceptOffice(capYesswCaseProcess.getAcceptOffice());
            capYesswCaseProcessMapper.updateByPrimaryKey(newProcess);*/
            CapYesswCaseProcess oldProcess = capYesswCaseProcessMapper.selectByPrimaryKey(id);
            BeanUtil.copyNotNullBean(capYesswCaseProcess, oldProcess);
            oldProcess.setUpdateBy(currentUser.getId());
            oldProcess.setUpdateDate(new Date());
            capYesswCaseProcessMapper.updateByPrimaryKey(oldProcess);
        } else {
            capYesswCaseProcess.setId(UUID.randomUUID().toString().replaceAll("-", "").toLowerCase());
            capYesswCaseProcess.setCreateBy(currentUser.getId());
            capYesswCaseProcess.setCreateDate(new Date());
            capYesswCaseProcess.setUpdateDate(new Date());
            capYesswCaseProcess.setUpdateBy(currentUser.getId());
            this.insertSelective(capYesswCaseProcess);
        }

        //这里要拿到最终承办单位和承办单位的list拼接出来的字符串，放回到对应的analyze表里和12345信息表里
        String yesswCaseId = capYesswCaseProcess.getYesswCaseId();
        CapYesswCaseProcess process = new CapYesswCaseProcess();
        process.setYesswCaseId(yesswCaseId);
        //process.setOrderFlag("asc");
        List<CapYesswCaseProcess> processList = capYesswCaseProcessMapper.selectListByCondition(process);
        saveYesswCaseAnalyzeByProcessList(yesswCaseId, processList);
    }

    @Override
    public void delProcess(String id) {
        CapYesswCaseProcess process = capYesswCaseProcessMapper.selectByPrimaryKey(id);
        process.setDelFlag("1");
        capYesswCaseProcessMapper.updateByPrimaryKeySelective(process);
        //这里需要再查一下现在对应的承办单位放到对应那两张表里
        CapYesswCaseProcess processQuery = new CapYesswCaseProcess();
        processQuery.setYesswCaseId(process.getYesswCaseId());
        List<CapYesswCaseProcess> processList = capYesswCaseProcessMapper.selectListByCondition(process);
        saveYesswCaseAnalyzeByProcessList(process.getYesswCaseId(), processList);
    }


    public void saveYesswCaseAnalyzeByProcessList(String yesswCaseId, List<CapYesswCaseProcess> processList) {

        CapYesswCaseAnalyze analyze = capYesswCaseAnalyzeService.selectByPrimaryKey(yesswCaseId);
        String str = joinAcceptOfficeStr(processList, processList.size(), "", "");
        String processDateStr = joinAcceptOfficeDateStr(processList, processList.size(), "", "");
        if (processList!=null && !processList.isEmpty()) {
            analyze.setAcceptOffice(processList.get(0).getAcceptOffice());
            analyze.setSolveFlag(processList.get(0).getSolveFlag());
            analyze.setPleasedFlag(processList.get(0).getPleasedFlag());
            analyze.setRecoveryTime(DateUtils.formatDateTime(processList.get(0).getCreateDate()));
            analyze.setAdjustFlag(processList.get(0).getAdjustFlag());
        } else {
            analyze.setAcceptOffice(analyze.getYesswAcceptOffice());
            analyze.setSolveFlag(analyze.getSolveFlag());
            analyze.setPleasedFlag(analyze.getPleasedFlag());
        }
        analyze.setAcceptOfficeProcess(str);
        analyze.setAcceptOfficeTime(processDateStr);
        capYesswCaseAnalyzeService.updateByPrimaryKeySelective(analyze);
        //还需要保存对应的12345案件信息表里的这两个字段
        CapYesswCaseInfo caseInfo = new CapYesswCaseInfo();
        caseInfo.setYesswNumber(analyze.getYesswNumber());
        List<CapYesswCaseInfo> caseInfoList = capYesswCaseInfoService.selectListByCondition(caseInfo);
        if (caseInfoList!=null && !caseInfoList.isEmpty()) {
            CapYesswCaseInfo capYesswCaseInfo = caseInfoList.get(0);
            capYesswCaseInfo.setAcceptOffice(analyze.getAcceptOffice());
            capYesswCaseInfo.setAcceptOfficeProcess(str);
            capYesswCaseInfoService.updateByPrimaryKeySelective(capYesswCaseInfo);
        }
    }



    public String joinAcceptOfficeStr(List<CapYesswCaseProcess> processList, int size, String lastOffice, String processStr) {
        if (size != 0) {
            CapYesswCaseProcess process = processList.get(size-1);
            String tempOffice = process.getAcceptOffice();
            if (StringUtils.isBlank(processStr)) {
                processStr = tempOffice;
            } else {
                if (lastOffice.equals(tempOffice)) {

                } else {
                    if (size == processList.size()) {
                        processStr = tempOffice;
                    } else {
                        if (StringUtils.isNotBlank(tempOffice)) {
                            processStr = processStr + "、" + tempOffice;
                        }
                    }
                }
            }
            return joinAcceptOfficeStr(processList, size-1, tempOffice, processStr);
        } else {
            return processStr;
        }
    }



    public String joinAcceptOfficeDateStr(List<CapYesswCaseProcess> processList, int size, String lastOfficeDate, String processStr) {
        if (size != 0) {
            CapYesswCaseProcess process = processList.get(size-1);
            //String tempOffice = process.getAcceptOffice();
            String tempProcessDate = DateUtils.formatDateTime(process.getCreateDate());
            if (StringUtils.isBlank(processStr)) {
                processStr = tempProcessDate;
            } else {
                if (lastOfficeDate.equals(tempProcessDate)) {

                } else {
                    if (size == processList.size()) {
                        processStr = tempProcessDate;
                    } else {
                        processStr = processStr + "、" + tempProcessDate;
                    }
                }
            }
            return joinAcceptOfficeDateStr(processList, size-1, tempProcessDate, processStr);
        } else {
            return processStr;
        }
    }




}
