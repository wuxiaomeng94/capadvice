package com.capinfo.service;

import com.capinfo.base.BaseMapper;
import com.capinfo.base.CurrentUser;
import com.capinfo.base.impl.BaseServiceImpl;
import com.capinfo.entity.*;
import com.capinfo.exception.MyException;
import com.capinfo.mapper.CapVehicleInfoMapper;
import com.capinfo.mapper.CapWxAccountFansMapper;
import com.capinfo.util.ReType;
import com.capinfo.utils.TimeUtil;
import com.capinfo.vehicle.utilEntity.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CapVehicleInfoService extends BaseServiceImpl<CapVehicleInfo, String> {

    @Autowired
    private CapVehicleInfoMapper capVehicleInfoMapper;
    @Autowired
    private CapWorkOrderRecordService capWorkOrderRecordService;
    @Autowired
    private CapVehicleSpendtimeService capVehicleSpendtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private FlowMessagePushService flowMessagePushService;
    @Autowired
    private SysUserService sysUserService;


    @Override
    public BaseMapper<CapVehicleInfo, String> getMappser() {
        return capVehicleInfoMapper;
    }


    public ReType showJoinOrderRecord(CapVehicleInfo capVehicleInfo, int page, int limit) {
        List<CapVehicleInfo> tList = null;
        Page<CapVehicleInfo> tPage = PageHelper.startPage(page, limit);
        try {
            tList = capVehicleInfoMapper.selectListByCondition(capVehicleInfo);
        } catch (MyException e) {
            //log.error("class:BaseServiceImpl ->method:show->message:" + e.getMessage());
            e.printStackTrace();
        }
        return new ReType(tPage.getTotal(), tList);
    }


    /**
     * 根据条件查询
     * @param capVehicleInfo
     * @return
     */
    public List<CapVehicleInfo> selectListByCondition(CapVehicleInfo capVehicleInfo) {
        return capVehicleInfoMapper.selectListByCondition(capVehicleInfo);
    }





    public CapVehicleInfo save(CapVehicleInfo capVehicleInfo) {
        CurrentUser currentUser = (CurrentUser) SecurityUtils.getSubject().getSession().getAttribute("curentUser");
        String id = capVehicleInfo.getId();
        if (StringUtils.isBlank(id)) {
            capVehicleInfo.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            //因为是模拟摄像头拍下车牌后记录数据，createby写一个默认的用户
            capVehicleInfo.setCreateBy(VehicleConstant.USER_WORKER_ID);
            capVehicleInfo.setCreateDate(new Date());
            capVehicleInfo.setUpdateBy(VehicleConstant.USER_WORKER_ID);
            capVehicleInfo.setUpdateDate(new Date());
            capVehicleInfo.setDelFlag("0");
            this.getMappser().insert(capVehicleInfo);
        } else {
            capVehicleInfo.setUpdateBy(currentUser.getId());
            capVehicleInfo.setUpdateDate(new Date());
            this.updateByPrimaryKey(capVehicleInfo);
        }
        return capVehicleInfo;
    }


    public List<CapVehicleInfo> selectByPlateNo(String plateNo) {
        CapVehicleInfo vehicleInfo = new CapVehicleInfo();
        vehicleInfo.setPlateNo(plateNo);
        return capVehicleInfoMapper.selectListByCondition(vehicleInfo);
    }






    /**
     * 需要这么一个东西，判断接下来走哪一步
     * @param status
     * @param capWorkOrderRecord
     * @return
     */
    public VehicleFlowEntity getMatchMap(String status, CapWorkOrderRecord capWorkOrderRecord) {
        VehicleFlowEntity flow = new VehicleFlowEntity();
        Map<String, Object> map = new HashMap<String, Object>();
        //数据进来的时候是这个节点状态。出去需要改变成下一个节点对应的
        String nowLink = capWorkOrderRecord.getNowLink();
        switch (nowLink) {
            case VehicleConstant.PROCESS_APPEAR:
                //外观检测
                if ("pass".equals(status)) {
                    //下一步到尾气检测 ，可能需要判断是否是新能源汽车
                    map.put("pass", "1");
                    flow.setNowLink(VehicleConstant.PROCESS_GAS);
                } else if ("nopass".equals(status)) {
                    map.put("pass", "2");
                    flow.setNowLink(VehicleConstant.PROCESS_APPEAR);
                    flow.setNowStatus(VehicleConstant.PROCESS_NOWSTATUS_NO);
                }
                flow.setStepMoney(10);
                break;
            case VehicleConstant.PROCESS_GAS:
                //尾气检测
                if ("pass".equals(status)) {
                    map.put("pass", "1");
                    flow.setNowLink(VehicleConstant.PROCESS_ONLINE);
                } else if ("nopass".equals(status)) {
                    map.put("pass", "2");
                    flow.setNowLink(VehicleConstant.PROCESS_GAS);
                    flow.setNowStatus(VehicleConstant.PROCESS_NOWSTATUS_NO);
                }
                //暂时用新加入的变量判断是新能源车辆还是普通车辆
                if (VehicleConstant.IS_POWERFREE_YES.equals(capWorkOrderRecord.getIsPowerfree())) {
                    flow.setStepMoney(0);
                } else {
                    flow.setStepMoney(20);
                }
                break;
            case VehicleConstant.PROCESS_ONLINE:
                //上线检测
                if ("pass".equals(status)) {
                    map.put("pass", "1");
                    flow.setNowLink(VehicleConstant.PROCESS_PAY);
                } else if ("nopass".equals(status)) {
                    map.put("pass", "2");
                    flow.setNowLink(VehicleConstant.PROCESS_ONLINE);
                    flow.setNowStatus(VehicleConstant.PROCESS_NOWSTATUS_NO);
                } else if ("nopasslight".equals(status)) {
                    map.put("pass", "3");
                    flow.setNowLink(VehicleConstant.PORCESS_LIGHT);
                    //忘记不通过的时候加上这个检测不通过的属性了。加上再测一下一下一下- 2019-08-08
                    flow.setNowStatus(VehicleConstant.PROCESS_NOWSTATUS_NO);
                }
                flow.setStepMoney(20);
                break;
            case VehicleConstant.PORCESS_LIGHT:
                //车灯复检
                if ("pass".equals(status)) {
                    map.put("pass", "1");
                    flow.setNowLink(VehicleConstant.PROCESS_PAY);
                } else if ("nopass".equals(status)) {
                    map.put("pass", "2");
                    flow.setNowLink(VehicleConstant.PORCESS_LIGHT);
                    //忘记不通过的时候加上这个检测不通过的属性了。加上再测一下一下一下- 2019-08-08
                    flow.setNowStatus(VehicleConstant.PROCESS_NOWSTATUS_NO);
                }
                flow.setStepMoney(20);
                break;
        }
        flow.setMap(map);
        return flow;
    }


    /**
     * 签收。拿着主表的id找一下record那张表，找到之后用里边的流程实例id找到task签收
     * 再insert一条用时这张表的数据
     * @param id
     */
    public void claim(String id) {
        CurrentUser currentUser = (CurrentUser) SecurityUtils.getSubject().getSession().getAttribute("curentUser");
        String userId = "";
        if (currentUser == null) {
            userId = VehicleConstant.USER_WORKER_ID;
        } else {
            userId = currentUser.getId();
        }
        CapWorkOrderRecord capWorkOrderRecord = capWorkOrderRecordService.selectByPrimaryKey(id);
        //为空或者为2不通过的时候，证明没签收过。已经是1的时候说明点进来了关掉又点进来了
        capWorkOrderRecord.setNowStatus(VehicleConstant.PROCESS_NOWSTATUS_CHECKING);
        capWorkOrderRecord.setUpdateDate(new Date());
        capWorkOrderRecordService.updateByPrimaryKey(capWorkOrderRecord);
        String processId = capWorkOrderRecord.getProcInstId();
        Task task = taskService.createTaskQuery().processInstanceId(processId).singleResult();
        taskService.claim(task.getId(), userId);

        String nowLink = capWorkOrderRecord.getNowLink();

        if (VehicleConstant.PROCESS_GAS.equals(nowLink)) {
            String free = capWorkOrderRecord.getIsPowerfree();
            if (VehicleConstant.IS_POWERFREE_NO.equals(free)) {
                capVehicleSpendtimeService.insertSpendtime(capWorkOrderRecord.getId(), task.getName(), "");
            }
        } else {
            capVehicleSpendtimeService.insertSpendtime(capWorkOrderRecord.getId(), task.getName(), "");
        }
    }



    public void createVehicleInfo(String license, String direction) {
        //记下来这么一个车的对象，在最后发一个消息给微信的队列里
        CapVehicleInfo vehicleMsg = new CapVehicleInfo();
        CapVehicleInfo vehicleInfo = new CapVehicleInfo();
        vehicleInfo.setPlateNo(license);
        List<CapVehicleInfo> list = this.selectListByCondition(vehicleInfo);
        if (list!=null&&!list.isEmpty()) {
            CapVehicleInfo vehicle = list.get(0);
            vehicleMsg = vehicle;

            CapWorkOrderRecord record = new CapWorkOrderRecord();
            //record.setRecordId(vehicle.getId());
            record.setVehicleId(vehicle.getId());
            List<CapWorkOrderRecord> recordList = capWorkOrderRecordService.selectListByCondition(record);
            if (recordList.size() == 0) {
                CapWorkOrderRecord capWorkOrderRecord = capWorkOrderRecordService.saveRecordByVehicleInfo(vehicle);
                //加一条spendtime这张表的数据
                CapVehicleSpendtime spendtime = new CapVehicleSpendtime();
                spendtime.setNowStatus(VehicleConstant.PROCESS_SPENDTIME_CHECKING);
                spendtime.setCapWorkRecordId(capWorkOrderRecord.getId());
                spendtime.setStartTime(new Date());
                spendtime.setTaskName(VehicleProcessEnum.PROCESS_ENTER.getTypeName());
                capVehicleSpendtimeService.save(spendtime);
                record = capWorkOrderRecord;
                vehicleMsg.setCapWorkOrderRecord(record);
                //发送消息队列
                sendMsg(vehicleMsg);
            } else {
                record = recordList.get(0);
                vehicleMsg.setCapWorkOrderRecord(record);
                //这个时候不在发消息到消息队列了
            }


            if (VehicleConstant.PROCESS_END.equals(record.getNowLink())) {
                CapWorkOrderRecord capWorkOrderRecord = capWorkOrderRecordService.saveRecordByVehicleInfo(vehicle);
                //加一条spendtime这张表的数据
                CapVehicleSpendtime spendtime = new CapVehicleSpendtime();
                spendtime.setNowStatus(VehicleConstant.PROCESS_SPENDTIME_CHECKING);
                spendtime.setCapWorkRecordId(capWorkOrderRecord.getId());
                spendtime.setStartTime(new Date());
                spendtime.setTaskName(VehicleProcessEnum.PROCESS_ENTER.getTypeName());
                capVehicleSpendtimeService.save(spendtime);
                vehicleMsg.setCapWorkOrderRecord(capWorkOrderRecord);
                sendMsg(vehicleMsg);
            } else {
                //这个时候不需要在添加数据。这个时候说明还有没结束流程的年检记录
                //在这看是在哪一步就给哪一步的用户发消息
                String procInstId = record.getProcInstId();
                if (StringUtils.isNotBlank(procInstId)) {
                    Task task = taskService.createTaskQuery().processInstanceId(procInstId).singleResult();
                    if (task != null) {
                        String nextRoleId = NowLinkUtils.getRoleIdByNowLink(record.getNowLink());
                        List<SysUser> nextUserList = sysUserService.getUserListByRoleId(nextRoleId);
                        flowMessagePushService.addflowByRecord(nextUserList, record, "add");
                    }
                }
            }
        } else {
            CapVehicleInfo capVehicleInfo = new CapVehicleInfo();
            capVehicleInfo.setPlateNo(license);
            capVehicleInfo.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            //因为是模拟摄像头拍下车牌后记录数据，createby写一个默认的用户
            capVehicleInfo.setCreateBy(VehicleConstant.USER_WORKER_ID);
            capVehicleInfo.setCreateDate(new Date());
            capVehicleInfo.setUpdateBy(VehicleConstant.USER_WORKER_ID);
            capVehicleInfo.setUpdateDate(new Date());
            capVehicleInfo.setDelFlag("0");
            this.insertSelective(capVehicleInfo);

            vehicleMsg = capVehicleInfo;
            //capVehicleInfo = this.save(capVehicleInfo);
            //在这要再加一条record表的数据
            CapWorkOrderRecord capWorkOrderRecord = capWorkOrderRecordService.saveRecordByVehicleInfo(capVehicleInfo);
            vehicleMsg.setCapWorkOrderRecord(capWorkOrderRecord);

            //加一条spendtime这张表的数据
            CapVehicleSpendtime spendtime = new CapVehicleSpendtime();
            spendtime.setStatus(VehicleConstant.PROCESS_SPENDTIME_END);
            spendtime.setNowStatus(VehicleConstant.PROCESS_SPENDTIME_CHECKING);
            spendtime.setCapWorkRecordId(capWorkOrderRecord.getId());
            spendtime.setStartTime(new Date());
            spendtime.setTaskName(VehicleProcessEnum.PROCESS_ENTER.getTypeName());
            capVehicleSpendtimeService.save(spendtime);
            //发送消息队列
            sendMsg(vehicleMsg);
        }




    }


    public void startFlowByCamera(String license) {
        CapVehicleInfo vehicleInfo = new CapVehicleInfo();
        vehicleInfo.setPlateNo(license);
        List<CapVehicleInfo> list = this.selectListByCondition(vehicleInfo);
        if (list.size()>0) {
            CapVehicleInfo info = list.get(0);
            CapWorkOrderRecord record = new CapWorkOrderRecord();
            //record.setRecordId(info.getId());
            record.setVehicleId(info.getId());
            CapWorkOrderRecord capWorkOrderRecord = capWorkOrderRecordService.selectListByCondition(record).get(0);
            //这里还要加工作流的东西。判断之前开始工作流，判断之后走下一步或者这一步不通过去灯光复检那一步
            //在这里先写开始工作流的
            capWorkOrderRecordService.startFlow(capWorkOrderRecord);
            VehicleFlowEntity flow = new VehicleFlowEntity();
            Map<String, Object> map = new HashMap<String, Object>();
            //暂时不好判断电动车还是普通车，让所有的都过到尾气检测那一步，在那一步里加一个“免检”按钮，走免检的不算尾气检测
            flow.setNowLink(VehicleConstant.PROCESS_GAS);
            map.put("pass", "1");
            flow.setMap(map);
            flow.setStepMoney(0);
            capWorkOrderRecordService.completeFlow(capWorkOrderRecord, flow);
            //插入队列里     先找到对应的尾气检测角色的用户
            List<SysUser> userList = sysUserService.getUserListByRoleId(VehicleConstant.ROLEID_GAS);
            flowMessagePushService.addflowByRecord(userList, capWorkOrderRecord, "add");

        }

    }


    /**
     * 根据车牌号，查一下对应的Vehicle表车的信息里    车辆类型    有没有值。如果没有值根据当前用户赋一下值
     * @param plateNo
     */
    public void saveVehiclePropValue(String plateNo) {
        CurrentUser currentUser = (CurrentUser) SecurityUtils.getSubject().getSession().getAttribute("curentUser");
        CapVehicleInfo vehicleInfo = new CapVehicleInfo();
        vehicleInfo.setPlateNo(plateNo);
        List<CapVehicleInfo> list = capVehicleInfoMapper.selectListByCondition(vehicleInfo);
        if (list!=null && !list.isEmpty()) {
            CapVehicleInfo capVehicleInfo = capVehicleInfoMapper.selectByPrimaryKey(list.get(0).getId());
            String vehicleProp = capVehicleInfo.getVehicleProp();
            if (StringUtils.isBlank(vehicleProp)) {
                //在这根据用户判断一下附上值
                if (VehicleConstant.MID_USERID.equals(currentUser.getId())) {
                    capVehicleInfo.setVehicleProp(VehicleConstant.VEHICLE_PROP_MIDDLE);
                } else {
                    capVehicleInfo.setVehicleProp(VehicleConstant.VEHICLE_PROP_SMALL);
                }
                this.updateByPrimaryKey(capVehicleInfo);
            }

        }
    }



    public void sendMsg(CapVehicleInfo vehicleMsg) {
        //进入车检厂，判断一下vehicleMsg里有没有微信的openId,如果有给微信公众号发一条消息
        String openId = vehicleMsg.getOpenid();
        if (StringUtils.isNotBlank(openId)) {
            /*if ("3".equals(direction)) {
                //取到的数据暂时设定方向3为进入车检厂时的方向。进入车检厂时发送微信公众号一条消息。需要再测试一下
            }*/
            flowMessagePushService.sendRecordToWx(vehicleMsg);
        }
        //往大屏上也发送
        flowMessagePushService.sendRecordToLargeScreen(vehicleMsg);

    }




    public ExcelEntity loadExcelEntity(CapVehicleInfo vehicleInfo) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ExcelEntity excelEntity = new ExcelEntity();
        excelEntity.setPlateNo(vehicleInfo.getPlateNo());
        String workOrderRecordId = vehicleInfo.getCapWorkOrderRecord().getId();

        CapVehicleSpendtime spendtime = new CapVehicleSpendtime();
        spendtime.setCapWorkRecordId(workOrderRecordId);
        List<CapVehicleSpendtime> capVehicleSpendtimeList = capVehicleSpendtimeService.selectListByCondition(spendtime);
        List<CapVehicleSpendtime> enterList = capVehicleSpendtimeList.stream().filter((spend) -> VehicleProcessEnum.PROCESS_ENTER.getTypeName().equals(spend.getTaskName())).collect(Collectors.toList());
        if (enterList!=null && !enterList.isEmpty()) {
            excelEntity.setEnterTime(sdf.format(enterList.get(0).getStartTime()));
        }
        List<CapVehicleSpendtime> gasList = capVehicleSpendtimeList.stream().filter((spend) -> VehicleProcessEnum.PROCESS_GAS.getTypeName().equals(spend.getTaskName())).collect(Collectors.toList());
        if (gasList!=null && !gasList.isEmpty()) {
            gasList.sort(Comparator.comparing((gas) -> gas.getCreateDate()));
            excelEntity.setGasTimes(gasList.size()+"");
            int second = 0;
            for (CapVehicleSpendtime capVehicleSpendtime : gasList) {
                second = second + capVehicleSpendtime.getDuration();
            }
            String timeStr = TimeUtil.secondToTime(second);
            excelEntity.setGasAllSpend(timeStr);
            //尾气检测入场时间
            List<CapVehicleSpendtime> appearList = capVehicleSpendtimeList.stream().filter((spend) -> VehicleProcessEnum.PROCESS_APPEAR.getTypeName().equals(spend.getTaskName())).collect(Collectors.toList());
            if (appearList!=null && !appearList.isEmpty()) {
                excelEntity.setGasTime(sdf.format(appearList.get(0).getStartTime()));
            }
            //尾气检测结果更新时间
            excelEntity.setGasResultUpdateDate(sdf.format(gasList.get(0).getStartTime()));
            //尾气检测用时
            excelEntity.setGasSpend(TimeUtil.secondToTime(gasList.get(0).getDuration()));
            if (gasList.size()>1) {
                //尾气一次复检入场时间
                excelEntity.setGasOneTime(sdf.format(gasList.get(0).getEndTime()));
                //尾气一次复检结果更新时间
                excelEntity.setGasOneResultUpdateDate(sdf.format(gasList.get(1).getStartTime()));
                //尾气一次复检用时
                excelEntity.setGasOneSpend(TimeUtil.secondToTime(gasList.get(1).getDuration()));
            }
            if (gasList.size()>2) {
                //尾气二次复检入场时间
                excelEntity.setGasTwoTime(sdf.format(gasList.get(1).getEndTime()));
                //尾气二次复检结果更新时间
                excelEntity.setGasTwoResultUpdateDate(sdf.format(gasList.get(2).getStartTime()));
                //尾气二次复检用时
                excelEntity.setGasTwoSpend(TimeUtil.secondToTime(gasList.get(2).getDuration()));
            }
        }
        List<CapVehicleSpendtime> onlineList = capVehicleSpendtimeList.stream().filter((spend) -> VehicleProcessEnum.PROCESS_ONLINE.getTypeName().equals(spend.getTaskName())).collect(Collectors.toList());
        if (onlineList!=null && !onlineList.isEmpty()) {
            onlineList.sort(Comparator.comparing((online) -> online.getCreateDate()));
            excelEntity.setOnlineTimes(onlineList.size()+"");
            int second = 0;
            for (CapVehicleSpendtime capVehicleSpendtime : onlineList) {
                second = second + capVehicleSpendtime.getDuration();
            }
            String timeStr = TimeUtil.secondToTime(second);
            excelEntity.setOnlineAllSpend(timeStr);
            //上线检测入场时间
            excelEntity.setOnlineTime(sdf.format(gasList.get(gasList.size()-1).getEndTime()));
            excelEntity.setOnlineResultUpdateDate(sdf.format(onlineList.get(0).getStartTime()));
            excelEntity.setOnlineSpend(TimeUtil.secondToTime(onlineList.get(0).getDuration()));
            if (onlineList.size()>1) {
                //上线一次复检入场时间
                excelEntity.setOnlineOneTime(sdf.format(onlineList.get(0).getEndTime()));
                //上线一次复检结果更新时间
                excelEntity.setOnlineOneResultUpdateDate(sdf.format(onlineList.get(1).getStartTime()));
                //上线一次复检用时
                excelEntity.setOnlineOneSpend(TimeUtil.secondToTime(onlineList.get(1).getDuration()));
            }
            if (onlineList.size()>2) {
                //上线二次复检入场时间
                excelEntity.setOnlineTwoTime(sdf.format(onlineList.get(1).getEndTime()));
                //上线二次复检结果更新时间
                excelEntity.setOnlineTwoResultUpdateDate(sdf.format(onlineList.get(2).getStartTime()));
                //上线二次复检用时
                excelEntity.setOnlineTwoSpend(TimeUtil.secondToTime(onlineList.get(2).getDuration()));
            }
        }
        List<CapVehicleSpendtime> lightList = capVehicleSpendtimeList.stream().filter((spend) -> VehicleProcessEnum.PROCESS_LIGHT.getTypeName().equals(spend.getTaskName())).collect(Collectors.toList());
        excelEntity.setLightTimes(lightList.size()+"");
        if (lightList.size()>0) {
            //灯光一次复检入场时间
            excelEntity.setLightOneTime(sdf.format(gasList.get(onlineList.size()-1).getEndTime()));
            excelEntity.setLightOneResultUpdateDate(sdf.format(lightList.get(0).getStartTime()));
            excelEntity.setLightOneSpend(TimeUtil.secondToTime(lightList.get(0).getDuration()));
        }
        if (lightList.size()>1) {
            excelEntity.setLightTwoTime(sdf.format(lightList.get(0).getEndTime()));
            excelEntity.setLightTwoResultUpdateDate(sdf.format(lightList.get(1).getStartTime()));
            excelEntity.setLightTwoSpend(TimeUtil.secondToTime(lightList.get(1).getDuration()));
        }

        int allTime = 0;
        for (CapVehicleSpendtime capVehicleSpendtime : capVehicleSpendtimeList) {
            Integer duration = capVehicleSpendtime.getDuration();
            if (duration != null) {
                allTime = allTime + duration;
            }
        }
        excelEntity.setCheckSpend(TimeUtil.secondToTime(allTime));
        return excelEntity;
    }


}
