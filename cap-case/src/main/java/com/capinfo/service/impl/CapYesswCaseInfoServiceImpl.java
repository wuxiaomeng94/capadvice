package com.capinfo.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.capinfo.base.BaseMapper;
import com.capinfo.base.impl.BaseServiceImpl;
import com.capinfo.entity.CapYesswCaseAnalyze;
import com.capinfo.entity.CapYesswCaseInfo;
import com.capinfo.mapper.CapYesswCaseInfoMapper;
import com.capinfo.service.CapYesswCaseAnalyzeService;
import com.capinfo.service.CapYesswCaseInfoService;
import com.capinfo.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.*;

@Service
public class CapYesswCaseInfoServiceImpl extends BaseServiceImpl<CapYesswCaseInfo,String> implements CapYesswCaseInfoService {

    @Autowired
    private CapYesswCaseInfoMapper capYesswCaseInfoMapper;
    @Autowired
    private CapYesswCaseAnalyzeService capYesswCaseAnalyzeService;

    public static final String yesswCaseUrl="http://172.26.54.61/admin/api/order/list";

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public BaseMapper<CapYesswCaseInfo, String> getMappser() {
        return capYesswCaseInfoMapper;
    }


    public ResultData saveCapYesswCaseInfo(String starttime, String endtime, String caseNum, String tokenStr) throws Exception {
        //模拟登陆用的token。如果过期了则需要从页面中手动输入，输入之后反射把常量池里的token值改掉
        //String token = StringUtils.isBlank(tokenStr)?Constant.TOKEN_STR:tokenStr;
        String token = "";
        if (StringUtils.isBlank(tokenStr)) {
            token = CaseConstant.TOKEN_STR;
        } else {
            token = tokenStr;
            //Constant.TOKEN_STR = tokenStr;
            Class constClazz = CaseConstant.class;
            Method setTokenStrMethod = constClazz.getMethod("setTokenStr", String.class);
            Object obj = constClazz.getConstructor().newInstance();
            setTokenStrMethod.invoke(obj, tokenStr);
        }
        Date nowDate = new Date();
        String beginStarttime = DateUtils.SDF_DATE.format(DateUtils.getBeforeDate(nowDate, 90)) + " 00:00:00";
        //String beginStarttime = "2016-01-01 00:00:00";
        String endStarttime = DateUtils.SDF_DATE.format(nowDate) + " 23:59:59";
        Integer startPage=1;
        Integer totalPage=1;
        QueryParam queryParam=new QueryParam();
        queryParam.setBeginStartTime(beginStarttime);
        queryParam.setEndStartTime(endStarttime);
        String beginCalltime = StringUtils.isBlank(starttime)?DateUtils.SDF_DATE.format(nowDate)+ " 00:00:00":starttime;
        String endCalltime = StringUtils.isBlank(endtime)?DateUtils.SDF_DATE.format(nowDate)+ " 23:59:59":endtime;
        setQueryParam(queryParam, starttime, endtime, caseNum, beginCalltime, endCalltime, startPage);
        try {
            List<CapYesswCaseInfo> infoList = new ArrayList<CapYesswCaseInfo>();
            while(totalPage>=startPage){
                queryParam.setPageNo(startPage);
                String param = JsonUtil.objectToJsonStr(queryParam);
                String result = HttpUtils.sendPost12345ByTokenStr(param, yesswCaseUrl, token);
                if (StringUtils.isBlank(result)) {
                    logger.error("=============="+result+"====================");
                    return ResultData.error("获取信息为空");
                }
                Result res = JsonUtil.jsonStrToPo(result, Result.class);
                if (res.getStatus() != 200) {
                    logger.error("==============="+res.getMsg()+"============");
                    return ResultData.error(res.getMsg());
                }
                if (res == null) {
                    logger.error("===========解析数据出现问题==================");
                    return ResultData.error("解析数据出现问题");
                }
                DataBean dataBean = null;
                try {
                    dataBean = res.getData();
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("============解析数据出现问题================");
                    return ResultData.error("解析数据出现问题");
                }
                logger.info("====================ok======================");
                totalPage = dataBean.getTotalPage();
                List<ListBean> list=dataBean.getList();
                list.forEach((listBean) -> {
                    CapYesswCaseInfo info = null;
                    try {
                        info = changeBeanToCapYesswCaseInfo(listBean);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    infoList.add(info);
                });
                startPage++;
                System.out.println("startPage============="+startPage);
                System.out.println("totalPage============="+totalPage);
            }
            //还未结案的案件       好像应该取这个字段   YesswSendTime   YesswSendTime   这个是案件呼叫时间   YesswCreateTime 这个是案件开始时间
            //呼叫时间是反应问题的人打电话来的时间。比案件开始的实际时间更早   用这个做范围查查出来的更多更全
            //用YesswCreateTime来比较重新获取的数据和原本库里的数据是否完全一样才行
            // 用呼叫时间是有问题的。不管回退了还是怎么着了呼叫时间都还是一样的这样就只会把数据改成后来的
            CapYesswCaseInfo query = new CapYesswCaseInfo();
            query.setYesswSendtimeStart(beginCalltime);
            query.setYesswSendtimeEnd(endCalltime);
            //List<CapYesswCaseInfo> caseList = capYesswCaseInfoDao.findByYesswSendTimeAfterAndYesswSendTimeBefore(beginCalltime, endCalltime);
            List<CapYesswCaseInfo> caseList = capYesswCaseInfoMapper.selectListByCondition(query);
            //判断出来需要操作update或者insert的数据，放到这个新的集合里。统一一起操作数据库
            List<CapYesswCaseInfo> batchList = new ArrayList<CapYesswCaseInfo>();
            infoList.forEach((infoBean) -> {
                boolean flag = false;
                for (CapYesswCaseInfo caseInfo : caseList) {
                    if (infoBean.getYesswNumber().equals(caseInfo.getYesswNumber()) && infoBean.getYesswCreateTime().equals(caseInfo.getYesswCreateTime())) {
                        //说明在表里已经存在抓取的数据了
                        flag = true;
                        infoBean.setId(caseInfo.getId());
                        //这些字段保持原本的不要变
                        infoBean.setAcceptOffice(caseInfo.getAcceptOffice());
                        infoBean.setAcceptOfficeProcess(caseInfo.getAcceptOfficeProcess());
                        infoBean.setConnectFlag(caseInfo.getConnectFlag());
                        infoBean.setSolveFlag(caseInfo.getSolveFlag());
                        infoBean.setPleasedFlag(caseInfo.getPleasedFlag());
                        break;
                    }
                }
                /*if (flag) {
                    batchList.add(infoBean);
                } else {
                    infoBean.setId(UUID.randomUUID().toString().replaceAll("-",""));
                    batchList.add(infoBean);
                }*/
                batchList.add(infoBean);
            });
            //capYesswCaseInfoDao.saveAll(batchList);
            //在这里，save之前要加一部，把这个list导成excel存到磁盘中的一个地方


            this.saveAll(batchList);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultData.sucess("获取数据成功");
    }


    public void saveAll(List<CapYesswCaseInfo> list) {
        //全导完数据之后，每条数据对应    CapYesswCaseAnalyze表，录入的那张表=》查一下有没有对应的数据，有的话把对应的字段项copy过去
        list.forEach((caseInfo) -> {
            if (StringUtils.isNotBlank(caseInfo.getId())) {
                //应该update
                //capYesswCaseInfoMapper.updateByPrimaryKey(caseInfo);
                caseInfo.setUpdateDate(new Date());
                capYesswCaseInfoMapper.updateByPrimaryKey(caseInfo);
                capYesswCaseAnalyzeService.saveInfoByYesswCaseInfo(caseInfo);
            } else {
                caseInfo.setId(UUID.randomUUID().toString().replaceAll("-",""));
                //capYesswCaseInfoMapper.insert(caseInfo);
                caseInfo.setCreateDate(new Date());
                capYesswCaseInfoMapper.insert(caseInfo);
                capYesswCaseAnalyzeService.saveInfoByYesswCaseInfo(caseInfo);
            }
        });
    }




    public CapYesswCaseInfo changeBeanToCapYesswCaseInfo(ListBean bean) throws ParseException {
        CapYesswCaseInfo caseInfo = new CapYesswCaseInfo();
        caseInfo.setYesswNumber(bean.getOrderId());
        caseInfo.setYesswCreateTime(bean.getStartTime());
        caseInfo.setYesswSendTime(bean.getCallDate());
        caseInfo.setYesswStatus(bean.getHandleName());
        caseInfo.setYesswAskedArea(bean.getDistrictName());
        caseInfo.setYesswAskedStreet(bean.getStreetName());
        caseInfo.setNumber(bean.getId()+"");

        caseInfo.setYesswAcceptOffice(bean.getOrderUnitName());
        caseInfo.setAcceptOffice(bean.getOrderUnitName());

        caseInfo.setYesswProblemType(bean.getCustomRequestTypeName());
        caseInfo.setProblemTypeLevel1(bean.getOneRequestTypeName());
        caseInfo.setProblemTypeLevel2(bean.getTwoRequestTypeName());
        caseInfo.setProblemTypeLevel3(bean.getThreeRequestTypeName());
        caseInfo.setProblemTypeLevel4(bean.getFourRequestTypeName());
        caseInfo.setProblemTypeLevel5(bean.getFiveRequestTypeName());
        caseInfo.setYesswResult(bean.getHandleResult());
        caseInfo.setYesswProblemPlace(bean.getRequestAddress());
        caseInfo.setYesswCallperson(bean.getUserName());
        caseInfo.setYesswCallpersonPhone(bean.getCallerNumber());
        caseInfo.setYesswEndTime(bean.getFinishTime());
        caseInfo.setYesswTitle(bean.getTitle());
        caseInfo.setYesswType(bean.getOrderTypeName());
        Date afterDate = DateUtils.getAfterDate(DateUtils.SDF_DATETIME.parse(bean.getStartTime()), 7);
        //案件截止日期
        caseInfo.setCaseFinishTime(DateUtils.SDF_DATETIME.format(afterDate));
        //承办单位流转过程
        caseInfo.setAcceptOfficeProcess(bean.getOrderUnitName());

        int length = bean.getContent().length();
        if (length > 800) {
            caseInfo.setYesswContent(bean.getContent().substring(0,800));
        } else {
            caseInfo.setYesswContent(bean.getContent());
        }
        return caseInfo;
    }


    private void setQueryParam(QueryParam queryParam, String starttime, String endtime, String caseNum, String beginCalltime, String endCalltime, Integer startPage) {
        if (StringUtils.isNotBlank(caseNum)) {
            queryParam.setOrderId(caseNum);
            if (StringUtils.isNotBlank(starttime)) {
                queryParam.setBeginCallTime(starttime);
            }
            if (StringUtils.isNotBlank(endtime)) {
                queryParam.setEndCallTime(endtime);
            }
        } else {
            queryParam.setBeginCallTime(beginCalltime);
            queryParam.setEndCallTime(endCalltime);
        }
        queryParam.setPageNo(startPage);
        queryParam.setPageSize(500);
    }



    public Workbook bigExcel(int pageNum, CapYesswCaseInfo caseQuery, Workbook workbook, ExportParams exportParams) {

        /*Page<CapYesswCaseInfo> pageQueryGroupBy = findPageQueryGroupBy(pageNum, 1000, caseQuery);
        List<CapYesswCaseInfo> list = pageQueryGroupBy.getContent();
        workbook = ExcelExportUtil.exportBigExcel(exportParams, CapYesswCaseInfo.class, list);
        //如果不是最后一页，递归查询
        if (pageQueryGroupBy.getTotalPages() > pageNum) {
            bigExcel(pageNum + 1, caseQuery, workbook, exportParams);
        }*/
        return workbook;
    }

    @Override
    public List<CapYesswCaseInfo> selectListByCondition(CapYesswCaseInfo capYesswCaseInfo) {
        return capYesswCaseInfoMapper.selectListByCondition(capYesswCaseInfo);
    }



    public void saveListToLocalFile(List<CapYesswCaseInfo> batchList) {
        ExportParams params = new ExportParams("案件列表", "案件列表", ExcelType.XSSF);
        try {
            Workbook workbook = ExcelExportUtil.exportBigExcel(params, CapYesswCaseAnalyze.class, batchList);
            //Workbook workbook = capYesswCaseInfoService.bigExcel(1, capYesswCaseInfo, null, params);
            ExcelExportUtil.closeExportBigExcel();
            //下载方法
            ExcelUtils.exportToLoaclFile(workbook, "F://test//", "案件列表"+DateUtils.SDF_DATETIME.format(new Date()));
            //ExcelUtils.export(response, workbook, "案件列表");
            //request.getSession().setAttribute("endflag", "1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    //@Scheduled(cron = "0 0/30 * * * ?")
    //@Scheduled(cron = "0 0/2 * * * ?  ")
    @Scheduled(cron = "0 0 12,18 * * ?")
    public void loadYesswDataTimer() {
        try {
            Date nowDate = new Date();
            CapYesswCaseInfo query = new CapYesswCaseInfo();
            query.setYesswStatusNotQuery(CaseConstant.YESSW_STATUS_FINISH);
            List<CapYesswCaseInfo> notFinishList = capYesswCaseInfoMapper.selectListByCondition(query);
            String beginStarttime = "";
            if (notFinishList != null && !notFinishList.isEmpty()) {
                beginStarttime = notFinishList.get(notFinishList.size()-1).getYesswSendTime();
                //要算一下这个时间和当前时间直接是否差了400天以上。如果400天以上则只取到400天之前
                //或者这样，创建时间限制默认的取的90天之前，这里也取90天
                Date notFinishDate = DateUtils.SDF_DATETIME.parse(beginStarttime);
                int days = DateUtils.differentDaysByMillisecond(notFinishDate, nowDate);
                if (days>30) {//暂时改成只找10天以内的。这样快一点同步数据
                    beginStarttime = DateUtils.SDF_DATE.format(DateUtils.getBeforeDate(nowDate, 30)) + " 00:00:00";
                }
            } else {
                beginStarttime = DateUtils.SDF_DATE.format(DateUtils.getBeforeDate(nowDate, 30)) + " 00:00:00";
            }
            String endStarttime = DateUtils.SDF_DATE.format(nowDate) + " 23:59:59";
            this.saveCapYesswCaseInfo(beginStarttime, endStarttime, "", CaseConstant.TOKEN_STR);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
