package com.capinfo.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.capinfo.base.BaseController;
import com.capinfo.base.CurrentUser;
import com.capinfo.entity.*;
import com.capinfo.enumUtils.DictEntity;
import com.capinfo.mapper.SysUserMapper;
import com.capinfo.service.*;
import com.capinfo.util.BeanUtil;
import com.capinfo.util.ReType;
import com.capinfo.utils.DictUtils;
import com.capinfo.utils.ExcelUtils;
import com.capinfo.utils.ResultData;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Controller
@RequestMapping(value = "/caseAnalyze")
public class CapYesswCaseAnalyzeController extends BaseController {


    @Autowired
    private CapYesswCaseInfoService capYesswCaseInfoService;
    @Autowired
    private CapYesswCaseAnalyzeService capYesswCaseAnalyzeService;
    @Autowired
    private CapAcceptOfficeService capAcceptOfficeService;
    @Autowired
    private CapYesswCaseProcessService capYesswCaseProcessService;


    /**
     * 获取12345案件
     * @param model
     * @return
     */
    @GetMapping(value = "yesswList")
    @RequiresPermissions("case:show")
    public String tsAdvice(Model model, HttpServletRequest request) {
        List<CapAcceptOffice> officeList = capAcceptOfficeService.selectListByCondition(new CapAcceptOffice());
        model.addAttribute("officeList", officeList);
        return "/system/analyze/yesswCaseInfoList";
    }

    @ApiOperation(value = "/showYesswList", httpMethod = "GET", notes = "意见列表")
    @GetMapping(value = "showYesswList")
    @ResponseBody
    @RequiresPermissions("case:show")
    public ReType showAdviceList(CapYesswCaseAnalyze capYesswCaseAnalyze, Model model, String page, String limit) {
        capYesswCaseAnalyzeService.setCapYesswCaseAnalyzeRole(capYesswCaseAnalyze);
        capYesswCaseAnalyze.setQueryPageFlag("1");
        return capYesswCaseAnalyzeService.show(capYesswCaseAnalyze, Integer.valueOf(page), Integer.valueOf(limit));
    }

    @RequestMapping(value = "addCase")
    public String addCase(Model model) {
        List<CapAcceptOffice> officeList = capAcceptOfficeService.selectListByCondition(new CapAcceptOffice());
        model.addAttribute("officeList", officeList);
        List<DictEntity> sendCaseTypeDict = DictUtils.getDictList("sendCaseType");
        model.addAttribute("sendCaseTypeDict", sendCaseTypeDict);
        return "/system/analyze/addyessw";
    }


    /**
     * 添加保存之前验证工单号是否已经存在
     * @param yesswNumber
     * @param acceptOffice
     * @return
     */
    @RequestMapping(value = "checkYesswNumberExist")
    @ResponseBody
    public ResultData checkYesswNumberExist(String yesswNumber, String acceptOffice) {
        CapYesswCaseAnalyze query = new CapYesswCaseAnalyze();
        query.setYesswNumber(yesswNumber);
        List<CapYesswCaseAnalyze> list = capYesswCaseAnalyzeService.selectListByCondition(query);
        if (list!=null && !list.isEmpty()) {
            //已经存在了。返回false
            return ResultData.error("fail");
        } else {
            return ResultData.sucess("success");
        }
    }

    /**
     * String yesswNumber, String acceptOffice, String sendCaseType, Date finishTime, Date createCaseTime
     * @return
     */
    @RequestMapping(value = "saveCase")
    @ResponseBody
    public ResultData saveCase(CapYesswCaseAnalyze capYesswCaseAnalyze) {
        //案件号，存到这张主表里。承办单位，存到这条数据对应的流转过程表里
        System.out.println("还得存流转过程表");
        try {
            CapYesswCaseAnalyze query = new CapYesswCaseAnalyze();
            query.setYesswNumber(capYesswCaseAnalyze.getYesswNumber());
            List<CapYesswCaseAnalyze> list = capYesswCaseAnalyzeService.selectListByCondition(query);
            if (list!=null && !list.isEmpty()) {
                //在这里直接录入一条子表的数据。

                //判断一下是否填写了回复市中心时间。如果填写了，则表示对应案件号的已经办结了。再来添加这个单号的话则新起一条。
                // ===》单号重复的问题需要再想一想
                /*if (list.get(0).getReplyCityTime() == null) {
                    CapYesswCaseProcess capYesswCaseProcess = new CapYesswCaseProcess();
                    capYesswCaseProcess.setYesswNumber(yesswNumber);
                    capYesswCaseProcess.setYesswCaseId(list.get(0).getId());
                    capYesswCaseProcess.setAcceptOffice(acceptOffice);
                    capYesswCaseProcessService.saveProcess(capYesswCaseProcess);
                } else {
                    capYesswCaseAnalyzeService.saveYesswNumberAndAcceptOffice(yesswNumber, acceptOffice, finishTime, sendCaseType);
                }*/

                CapYesswCaseProcess capYesswCaseProcess = new CapYesswCaseProcess();
                capYesswCaseProcess.setYesswNumber(capYesswCaseAnalyze.getYesswNumber());
                capYesswCaseProcess.setYesswCaseId(list.get(0).getId());
                capYesswCaseProcess.setAcceptOffice(capYesswCaseAnalyze.getAcceptOffice());
                capYesswCaseProcessService.saveProcess(capYesswCaseProcess);

                //sendCaseType  在这这个值可能也需要更新一下

            } else {
                capYesswCaseAnalyzeService.saveYesswNumberAndAcceptOffice(capYesswCaseAnalyze);
            }
            return ResultData.sucess("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.error("保存失败");
        }
    }


    /**
     * 编辑、查看的页面
     * @param id
     * @param model
     * @param detail
     * @return
     */
    @RequestMapping(value = "editCase")
    public String editCase(String id, Model model, boolean detail, HttpServletRequest request) {
        CapYesswCaseAnalyze capYesswCaseAnalyze = capYesswCaseAnalyzeService.selectByPrimaryKey(id);
        model.addAttribute("capYesswCaseAnalyze", capYesswCaseAnalyze);
        String edit = detail?"checkInfo":"edit";
        model.addAttribute("edit", edit);
        List<DictEntity> sendCaseTypeDict = DictUtils.getDictList("sendCaseType");
        model.addAttribute("sendCaseTypeDict", sendCaseTypeDict);
        return "/system/analyze/edit";
    }


    @ApiOperation(value = "/showProcessList", httpMethod = "GET", notes = "意见处理列表")
    @GetMapping(value = "showProcessList")
    @ResponseBody
    public ReType showAdviceDealList(CapYesswCaseAnalyze capYesswCaseAnalyze, HttpServletRequest request, Model model, String page, String limit) {
        String yesswCaseAnalyzeId = request.getParameter("yesswCaseAnalyzeId");
        CapYesswCaseProcess process = new CapYesswCaseProcess();
        process.setYesswCaseId(yesswCaseAnalyzeId);
        return capYesswCaseProcessService.show(process, Integer.valueOf(page), Integer.valueOf(limit));
    }

    @RequestMapping(value = "addProcessPage")
    public String addProcessPage(HttpServletRequest request, Model model) {
        String yesswCaseAnalyzeId = request.getParameter("yesswCaseAnalyzeId");
        CapYesswCaseAnalyze capYesswCaseAnalyze = capYesswCaseAnalyzeService.selectByPrimaryKey(yesswCaseAnalyzeId);
        CapYesswCaseProcess process = new CapYesswCaseProcess();
        process.setYesswCaseId(yesswCaseAnalyzeId);
        List<CapAcceptOffice> officeList = capAcceptOfficeService.selectListByCondition(new CapAcceptOffice());
        model.addAttribute("officeList", officeList);
        model.addAttribute("capYesswCaseAnalyze", capYesswCaseAnalyze);
        model.addAttribute("process", process);
        return "/system/analyze/addProcessPage";
    }

    @RequestMapping(value = "editProcessPage")
    public String editProcessPage(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        CapYesswCaseProcess process = capYesswCaseProcessService.selectByPrimaryKey(id);
        CapYesswCaseAnalyze capYesswCaseAnalyze = capYesswCaseAnalyzeService.selectByPrimaryKey(process.getYesswCaseId());
        List<CapAcceptOffice> officeList = capAcceptOfficeService.selectListByCondition(new CapAcceptOffice());
        model.addAttribute("officeList", officeList);
        model.addAttribute("capYesswCaseAnalyze", capYesswCaseAnalyze);
        model.addAttribute("process", process);
        return "/system/analyze/addProcessPage";
    }



    @RequestMapping(value = "saveProcess")
    @ResponseBody
    public ResultData saveProcess(CapYesswCaseProcess capYesswCaseProcess) {
        try {
            capYesswCaseProcessService.saveProcess(capYesswCaseProcess);
            return ResultData.sucess("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.error("保存失败");
        }
    }

    @RequestMapping(value = "delYesswCaseAnalyze")
    @ResponseBody
    public ResultData delYesswCaseAnalyze(String id) {
        try {
            capYesswCaseAnalyzeService.delAnalyze(id);
            return ResultData.sucess("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.error("删除失败");
        }
    }


    @RequestMapping(value = "saveAnalyze")
    @ResponseBody
    public ResultData saveAnalyze(CapYesswCaseAnalyze capYesswCaseAnalyze) {
        try {
            CurrentUser currentUser = (CurrentUser) SecurityUtils.getSubject().getSession().getAttribute("curentUser");
            CapYesswCaseAnalyze oldAnalyze = capYesswCaseAnalyzeService.selectByPrimaryKey(capYesswCaseAnalyze.getId());
            BeanUtil.copyNotNullBean(capYesswCaseAnalyze, oldAnalyze);
            oldAnalyze.setUpdateDate(new Date());
            oldAnalyze.setUpdateBy(currentUser.getId());
            capYesswCaseAnalyzeService.updateByPrimaryKeySelective(oldAnalyze);
            //也要看一下case_info里是否有对应的其他数据项。如果有也带过来
            CapYesswCaseInfo caseInfoQuery = new CapYesswCaseInfo();
            caseInfoQuery.setYesswNumber(oldAnalyze.getYesswNumber());
            List<CapYesswCaseInfo> caseInfoList = capYesswCaseInfoService.selectListByCondition(caseInfoQuery);
            if (caseInfoList!=null && !caseInfoList.isEmpty()) {
                CapYesswCaseInfo info = caseInfoList.get(0);
                capYesswCaseAnalyzeService.saveInfoByYesswCaseInfo(info);
            }
            return ResultData.sucess("success");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.error("fail");
        }
    }




    @RequestMapping(value = "delProcess")
    @ResponseBody
    public ResultData delProcess(String id) {
        try {
            capYesswCaseProcessService.delProcess(id);
            return ResultData.sucess("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.error("删除失败");
        }
    }



    @RequestMapping(value = "expExcel")
    public void expExcel(CapYesswCaseAnalyze capYesswCaseAnalyze, HttpServletRequest request, HttpServletResponse response) {
        //List<CapYesswCaseInfo> list = capYesswCaseInfoService.selectListByCondition(capYesswCaseInfo);
        request.getSession().removeAttribute("endflag");
        request.getSession().setAttribute("endflag", "0");
        capYesswCaseAnalyzeService.setCapYesswCaseAnalyzeRole(capYesswCaseAnalyze);
        List<CapYesswCaseAnalyze> list = capYesswCaseAnalyzeService.selectListByCondition(capYesswCaseAnalyze);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setIndexStr((i+1)+"");
        }
        //List<CapYesswCaseInfo> list = capYesswCaseInfoService.findListQueryGroupBy(capYesswCaseInfo);
        ExportParams params = new ExportParams("案件列表", "案件列表", ExcelType.XSSF);
        try {
            Workbook workbook = ExcelExportUtil.exportBigExcel(params, CapYesswCaseAnalyze.class, list);
            //Workbook workbook = capYesswCaseInfoService.bigExcel(1, capYesswCaseInfo, null, params);
            ExcelExportUtil.closeExportBigExcel();
            //下载方法
            ExcelUtils.export(response, workbook, "案件列表");
            request.getSession().setAttribute("endflag", "1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequestMapping(value = "getEndFlag")
    @ResponseBody
    public ResultData getendflag(HttpServletRequest request) {
        Object endflag = request.getSession().getAttribute("endflag"); //获取结束标记*/
        /*JSONObject obj = new JSONObject();
        obj.put("flag", flag);//返回状态值*/
        ResultData result = ResultData.sucess("success");
        result.setData(endflag);
        return result;
    }

    /**
     * 导出每天的各个单位的随机抽样excel？
     */
    /*@RequestMapping(value = "expEachDayRandomDataByOffice")
    public void expEachDayRandomDataByOffice(CapYesswCaseAnalyze capYesswCaseAnalyze, HttpServletRequest request, HttpServletResponse response) {

        capYesswCaseAnalyzeService.setCapYesswCaseAnalyzeRole(capYesswCaseAnalyze);
        List<CapYesswCaseAnalyze> list = capYesswCaseAnalyzeService.selectListByCondition(capYesswCaseAnalyze);

        List<List<CapYesswCaseAnalyze>> officeDataList = new ArrayList<>();
        List<String> acceptOfficeList = capYesswCaseAnalyzeService.selectAcceptOfficeByConditionGroupBy(capYesswCaseAnalyze);
        acceptOfficeList.forEach((office) -> {
            Stream<CapYesswCaseAnalyze> capYesswCaseAnalyzeStream = list.stream().filter((analyze) -> analyze.getAcceptOffice().equals(office));
        });
    }*/


    @RequestMapping(value = "impExcelPage")
    public String impExcelPage(HttpServletRequest request) {
        return "/system/analyze/impExcelPage";
    }

    @RequestMapping(value = "downloadTemp")
    public void downloadTemp(HttpServletRequest request, HttpServletResponse response) {
        ExcelUtils.dowmloadTemplate(request, response, "文件模板", ExcelUtils.EXCEPT_TYPE_XLS);
    }

    @RequestMapping(value = "uploadExcel")
    @ResponseBody
    public ResultData uploadExcel(@RequestParam MultipartFile excelFile, HttpServletRequest request, HttpServletResponse response) {
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        params.setNeedVerfiy(true);
        List<CaseImportEntity> list;
        try {
            list = ExcelImportUtil.importExcel(excelFile.getInputStream(), CaseImportEntity.class, params);
            System.out.println("=====================");
            list.forEach((importEntity) -> {
                String yesswNumber = importEntity.getYesswNumber();
                String acceptOffice = importEntity.getAcceptOffice();
                //查一下工单号是不是存在
                CapYesswCaseAnalyze query = new CapYesswCaseAnalyze();
                query.setYesswNumber(yesswNumber);
                List<CapYesswCaseAnalyze> listByNumber = capYesswCaseAnalyzeService.selectListByCondition(query);
                if (listByNumber!=null && !listByNumber.isEmpty()) {
                    //在这里直接录入一条子表的数据。
                    CapYesswCaseProcess capYesswCaseProcess = new CapYesswCaseProcess();
                    capYesswCaseProcess.setYesswNumber(yesswNumber);
                    capYesswCaseProcess.setYesswCaseId(listByNumber.get(0).getId());
                    capYesswCaseProcess.setAcceptOffice(acceptOffice);
                    capYesswCaseProcessService.saveProcess(capYesswCaseProcess);
                    capYesswCaseAnalyzeService.saveWorkerNum(importEntity, listByNumber.get(0).getId());
                } else {
                    String id = capYesswCaseAnalyzeService.saveYesswNumberAndAcceptOfficeBackId(yesswNumber, acceptOffice);
                    capYesswCaseAnalyzeService.saveWorkerNum(importEntity, id);
                }
            });

            /*list.forEach((importEntity) -> {
                CapYesswCaseInfo query = new CapYesswCaseInfo();
                query.setYesswNumber(importEntity.getYesswNumber());
                //List<CapYesswCaseInfo> caseInfoList = capYesswCaseInfoService.findListQuery(query);
                List<CapYesswCaseInfo> caseInfoList = capYesswCaseInfoService.selectListByCondition(query);
                if (caseInfoList != null && !caseInfoList.isEmpty()) {
                    CapYesswCaseInfo info = caseInfoList.get(0);
                    info.setConnectFlag(importEntity.getConnectFlag());
                    info.setSolveFlag(importEntity.getSolveFlag());
                    info.setPleasedFlag(importEntity.getPleasedFlag());
                    if (StringUtils.isNotBlank(importEntity.getAcceptOffice())) {
                        info.setAcceptOffice(importEntity.getAcceptOffice());
                    }
                    //capYesswCaseInfoService.save(info);
                }
            });*/
            return ResultData.sucess("导入成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.error("导入失败");
        }
    }


}
