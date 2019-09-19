package com.capinfo.service.impl;

import com.capinfo.base.BaseMapper;
import com.capinfo.base.CurrentUser;
import com.capinfo.base.impl.BaseServiceImpl;
import com.capinfo.entity.*;
import com.capinfo.mapper.CapYesswCaseAnalyzeMapper;
import com.capinfo.mapper.CapYesswCaseInfoMapper;
import com.capinfo.mapper.SysUserMapper;
import com.capinfo.service.CapYesswCaseAnalyzeService;
import com.capinfo.service.CapYesswCaseInfoService;
import com.capinfo.service.CapYesswCaseProcessService;
import com.capinfo.service.RoleService;
import com.capinfo.util.BeanUtil;
import com.capinfo.utils.BeanUtilsCopyNotNull;
import com.capinfo.utils.CaseConstant;
import com.capinfo.utils.DateUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CapYesswCaseAnalyzeServeImpl extends BaseServiceImpl<CapYesswCaseAnalyze,String> implements CapYesswCaseAnalyzeService {

    @Autowired
    private CapYesswCaseAnalyzeMapper capYesswCaseAnalyzeMapper;
    @Autowired
    private CapYesswCaseProcessService capYesswCaseProcessService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private CapYesswCaseInfoService capYesswCaseInfoService;
    @Autowired
    SysUserMapper sysUserMapper;


    @Override
    public BaseMapper<CapYesswCaseAnalyze, String> getMappser() {
        return capYesswCaseAnalyzeMapper;
    }

    @Override
    public List<CapYesswCaseAnalyze> selectListByCondition(CapYesswCaseAnalyze capYesswCaseAnalyze) {
        return capYesswCaseAnalyzeMapper.selectListByCondition(capYesswCaseAnalyze);
    }

    /**
     * String yesswNumber, String acceptOffice, Date finishTime, String sendCaseType
     */
    @Override
    public void saveYesswNumberAndAcceptOffice(CapYesswCaseAnalyze analyze) {
        String id = UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
        CurrentUser currentUser = (CurrentUser) SecurityUtils.getSubject().getSession().getAttribute("curentUser");
        CapYesswCaseAnalyze capYesswCaseAnalyze = new CapYesswCaseAnalyze();
        capYesswCaseAnalyze.setId(id);
        capYesswCaseAnalyze.setYesswNumber(analyze.getYesswNumber());
        //capYesswCaseAnalyze.setAcceptOffice(acceptOffice);
        capYesswCaseAnalyze.setYesswAcceptOffice(analyze.getAcceptOffice());
        //案件到期时间
        capYesswCaseAnalyze.setFinishTime(analyze.getFinishTime());
        capYesswCaseAnalyze.setSendCaseType(analyze.getSendCaseType());

        capYesswCaseAnalyze.setCreateBy(currentUser.getId());
        capYesswCaseAnalyze.setCreateDate(new Date());
        capYesswCaseAnalyze.setUpdateBy(currentUser.getId());
        capYesswCaseAnalyze.setUpdateDate(new Date());

        Date createCaseDate = analyze.getCreateCaseTime()!=null?analyze.getCreateCaseTime():new Date();
        capYesswCaseAnalyze.setCreateCaseTime(createCaseDate);
        this.insertSelective(capYesswCaseAnalyze);
        //存子表
        capYesswCaseProcessService.saveByYesswCaseAnalyze(capYesswCaseAnalyze, analyze.getAcceptOffice());

        //如果是在caseinfo里有，应该需要把caseinfo里对应的数据项带过来？
        CapYesswCaseInfo caseInfoQuery = new CapYesswCaseInfo();
        caseInfoQuery.setYesswNumber(analyze.getYesswNumber());
        List<CapYesswCaseInfo> caseInfoList = capYesswCaseInfoService.selectListByCondition(caseInfoQuery);
        if (caseInfoList!=null && !caseInfoList.isEmpty()) {
            CapYesswCaseInfo info = caseInfoList.get(0);
            saveInfoByYesswCaseInfo(info);
        }

    }

    @Override
    public void saveInfoByYesswCaseInfo(CapYesswCaseInfo capYesswCaseInfo) {
        String yesswNumber = capYesswCaseInfo.getYesswNumber();
        CapYesswCaseAnalyze query = new CapYesswCaseAnalyze();
        query.setYesswNumber(yesswNumber);
        List<CapYesswCaseAnalyze> capYesswCaseAnalyzeList = capYesswCaseAnalyzeMapper.selectListByCondition(query);
        if (capYesswCaseAnalyzeList!=null && !capYesswCaseAnalyzeList.isEmpty()) {
            CapYesswCaseAnalyze oldAnalyze = capYesswCaseAnalyzeList.get(0);
            /*CapAdvice oldCapAdvice = capAdviceService.selectByPrimaryKey(capAdvice.getId());
            BeanUtil.copyNotNullBean(capAdvice, oldCapAdvice);*/
            CapYesswCaseAnalyze newAnalyze = new CapYesswCaseAnalyze();
            //BeanUtil.copyNotNullBean(newAnalyze, capYesswCaseInfo);
            try {


                BeanUtilsCopyNotNull.copyNotNullProperties(newAnalyze,capYesswCaseInfo);
                newAnalyze.setId(oldAnalyze.getId());
                newAnalyze.setYesswAcceptOffice(oldAnalyze.getYesswAcceptOffice());
                newAnalyze.setCreateBy(oldAnalyze.getCreateBy());
                newAnalyze.setCreateDate(oldAnalyze.getCreateDate());
                newAnalyze.setUpdateBy(oldAnalyze.getUpdateBy());
                newAnalyze.setUpdateDate(oldAnalyze.getUpdateDate());
                newAnalyze.setFinishTime(oldAnalyze.getFinishTime());
                newAnalyze.setSendCaseType(oldAnalyze.getSendCaseType());
                this.updateByPrimaryKeySelective(newAnalyze);


                /*CapYesswCaseProcess oldProcess = capYesswCaseProcessMapper.selectByPrimaryKey(id);
                BeanUtil.copyNotNullBean(capYesswCaseProcess, oldProcess);
                oldProcess.setUpdateBy(currentUser.getId());
                oldProcess.setUpdateDate(new Date());
                capYesswCaseProcessMapper.updateByPrimaryKey(oldProcess);*/


            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void setCapYesswCaseAnalyzeRole(CapYesswCaseAnalyze capYesswCaseAnalyze) {
        CurrentUser currentUser = (CurrentUser) SecurityUtils.getSubject().getSession().getAttribute("curentUser");
        List<SysRole> roleList = roleService.getRoleListByUser(currentUser.getId());
        roleList.forEach((role) -> {
            if (CaseConstant.YESSW_CASE_WORKER.equals(role.getId())) {
                //录入人员。这个时候，只能看到自己录入的案件
                capYesswCaseAnalyze.setRoleFlag("worker");
                capYesswCaseAnalyze.setCurrentUserId(currentUser.getId());
            }
        });
    }

    @Override
    public void delAnalyze(String id) {
        CapYesswCaseAnalyze analyze = capYesswCaseAnalyzeMapper.selectByPrimaryKey(id);
        analyze.setDelFlag("1");
        capYesswCaseAnalyzeMapper.updateByPrimaryKeySelective(analyze);
        //找到对应的process子表数据。把子表的也都删掉
        CapYesswCaseProcess process = new CapYesswCaseProcess();
        process.setYesswCaseId(analyze.getId());
        List<CapYesswCaseProcess> processList = capYesswCaseProcessService.selectListByCondition(process);
        processList.forEach((processObj) -> {
            capYesswCaseProcessService.delProcess(processObj.getId());
        });
    }

    @Override
    public String saveYesswNumberAndAcceptOfficeBackId(String yesswNumber, String acceptOffice) {
        String id = UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
        CurrentUser currentUser = (CurrentUser) SecurityUtils.getSubject().getSession().getAttribute("curentUser");
        CapYesswCaseAnalyze capYesswCaseAnalyze = new CapYesswCaseAnalyze();
        capYesswCaseAnalyze.setId(id);
        capYesswCaseAnalyze.setYesswNumber(yesswNumber);
        //capYesswCaseAnalyze.setAcceptOffice(acceptOffice);
        capYesswCaseAnalyze.setYesswAcceptOffice(acceptOffice);
        capYesswCaseAnalyze.setCreateBy(currentUser.getId());
        capYesswCaseAnalyze.setCreateDate(new Date());
        capYesswCaseAnalyze.setUpdateBy(currentUser.getId());
        capYesswCaseAnalyze.setUpdateDate(new Date());
        //看一下这个单号是不是已经有了。如果没有再这样insert
        CapYesswCaseAnalyze analyzyQuery = new CapYesswCaseAnalyze();
        analyzyQuery.setYesswNumber(yesswNumber);
        List<CapYesswCaseAnalyze> analyzeList = capYesswCaseAnalyzeMapper.selectListByCondition(analyzyQuery);
        if (analyzeList!=null && !analyzeList.isEmpty()) {
            capYesswCaseAnalyze.setId(analyzeList.get(0).getId());
            this.updateByPrimaryKeySelective(capYesswCaseAnalyze);
        } else {
            this.insertSelective(capYesswCaseAnalyze);
        }
        //存子表
        capYesswCaseProcessService.saveByYesswCaseAnalyze(capYesswCaseAnalyze, acceptOffice);

        //如果是在caseinfo里有，应该需要把caseinfo里对应的数据项带过来？
        CapYesswCaseInfo caseInfoQuery = new CapYesswCaseInfo();
        caseInfoQuery.setYesswNumber(yesswNumber);
        List<CapYesswCaseInfo> caseInfoList = capYesswCaseInfoService.selectListByCondition(caseInfoQuery);
        if (caseInfoList!=null && !caseInfoList.isEmpty()) {
            CapYesswCaseInfo info = caseInfoList.get(0);
            saveInfoByYesswCaseInfo(info);
        }
        return id;
    }


    /**
     * 导入excel时候保存对应的工号，派单时间等
     * @param importEntity
     */
    @Override
    public void saveWorkerNum(CaseImportEntity importEntity, String id) {

        try {
            CapYesswCaseAnalyze capYesswCaseAnalyze = this.selectByPrimaryKey(id);
            capYesswCaseAnalyze.setWorkerNum(importEntity.getWorkerNum());
            //capYesswCaseAnalyze.setSendCaseTime(importEntity.getSendCaseTime());
            capYesswCaseAnalyze.setCreateDate(DateUtils.SDF_DATETIME.parse(importEntity.getCreateDate()));
            capYesswCaseAnalyze.setReplyCityTime(DateUtils.SDF_DATETIME.parse(importEntity.getReplyCityTime()));

            capYesswCaseAnalyze.setSolveFlag(importEntity.getSolveFlag());
            capYesswCaseAnalyze.setPleasedFlag(importEntity.getPleasedFlag());
            SysUser user = new SysUser();
            user.setUsername(importEntity.getWorkerNum());
            List<SysUser> sysUsers = sysUserMapper.selectListByPage(user);
            if (sysUsers!=null && !sysUsers.isEmpty()) {
                capYesswCaseAnalyze.setCreateBy(sysUsers.get(0).getId());
            }
            this.updateByPrimaryKeySelective(capYesswCaseAnalyze);

            //对应的process表最后一条，最好也把是否解决和是否满意的字段内容添进去
            CapYesswCaseProcess processQuery = new CapYesswCaseProcess();
            processQuery.setYesswCaseId(id);
            List<CapYesswCaseProcess> processList = capYesswCaseProcessService.selectListByCondition(processQuery);
            if (processList!=null && !processList.isEmpty()) {
                CapYesswCaseProcess process = processList.get(0);
                process.setSolveFlag(importEntity.getSolveFlag());
                process.setPleasedFlag(importEntity.getPleasedFlag());
                capYesswCaseProcessService.updateByPrimaryKeySelective(process);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<String> selectAcceptOfficeByConditionGroupBy(CapYesswCaseAnalyze capYesswCaseAnalyze) {
        return capYesswCaseAnalyzeMapper.selectAcceptOfficeByConditionGroupBy(capYesswCaseAnalyze);
    }


}
