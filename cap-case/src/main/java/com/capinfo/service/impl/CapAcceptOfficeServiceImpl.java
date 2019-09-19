package com.capinfo.service.impl;

import com.capinfo.base.BaseMapper;
import com.capinfo.base.CurrentUser;
import com.capinfo.base.impl.BaseServiceImpl;
import com.capinfo.entity.CapAcceptOffice;
import com.capinfo.entity.CapYesswCaseAnalyze;
import com.capinfo.mapper.CapAccpetOfficeMapper;
import com.capinfo.service.CapAcceptOfficeService;
import com.capinfo.service.CapYesswCaseAnalyzeService;
import com.capinfo.util.BeanUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CapAcceptOfficeServiceImpl extends BaseServiceImpl<CapAcceptOffice,String> implements CapAcceptOfficeService {

    @Autowired
    private CapAccpetOfficeMapper capAccpetOfficeMapper;

    @Override
    public BaseMapper<CapAcceptOffice, String> getMappser() {
        return capAccpetOfficeMapper;
    }


    @Override
    public List<CapAcceptOffice> selectListByCondition(CapAcceptOffice capAcceptOffice) {
        return capAccpetOfficeMapper.selectListByCondition(capAcceptOffice);
    }

    @Override
    public void save(CapAcceptOffice capAcceptOffice) {
        CurrentUser currentUser = (CurrentUser) SecurityUtils.getSubject().getSession().getAttribute("curentUser");
        String id = capAcceptOffice.getId();
        if (StringUtils.isNotBlank(id)) {
            CapAcceptOffice oldCapAcceptOffice = this.selectByPrimaryKey(id);
            BeanUtil.copyNotNullBean(capAcceptOffice, oldCapAcceptOffice);
            oldCapAcceptOffice.setUpdateBy(currentUser.getId());
            oldCapAcceptOffice.setUpdateDate(new Date());
            this.updateByPrimaryKeySelective(oldCapAcceptOffice);
        } else {
            capAcceptOffice.setId(UUID.randomUUID().toString().replaceAll("-", "").toLowerCase());
            capAcceptOffice.setCreateBy(currentUser.getId());
            capAcceptOffice.setCreateDate(new Date());
            capAcceptOffice.setUpdateBy(currentUser.getId());
            capAcceptOffice.setUpdateDate(new Date());
            this.insertSelective(capAcceptOffice);
        }

    }

    @Override
    public void delOffice(CapAcceptOffice capAcceptOffice) {
        capAcceptOffice.setDelFlag("1");
        this.updateByPrimaryKeySelective(capAcceptOffice);
    }
}
