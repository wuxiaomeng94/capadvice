package com.capinfo.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.capinfo.base.BaseController;
import com.capinfo.entity.CapYesswCaseAnalyze;
import com.capinfo.entity.CapYesswCaseInfo;
import com.capinfo.entity.CapYesswCaseProcess;
import com.capinfo.service.CapYesswCaseInfoService;
import com.capinfo.service.CapYesswCaseProcessService;
import com.capinfo.util.ReType;
import com.capinfo.utils.DateUtils;
import com.capinfo.utils.ExcelUtils;
import com.capinfo.utils.ExportByFreeMarkerUtils;
import com.capinfo.utils.ResultData;
import freemarker.template.Template;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/case")
public class CapYesswCaseInfoController extends BaseController {

    @Autowired
    private CapYesswCaseInfoService capYesswCaseInfoService;
    @Autowired
    private CapYesswCaseProcessService capYesswCaseProcessService;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;


    /**
     * 获取12345案件
     * @param model
     * @return
     */
    @GetMapping(value = "yessw")
    @RequiresPermissions("case:show")
    public String tsAdvice(Model model) {
        return "/system/yessw/yesswCaseInfoList";
    }


    @ApiOperation(value = "/showYesswList", httpMethod = "GET", notes = "意见列表")
    @GetMapping(value = "showYesswList")
    @ResponseBody
    @RequiresPermissions("case:show")
    public ReType showAdviceList(CapYesswCaseInfo capYesswCaseInfo, Model model, String page, String limit) {
        capYesswCaseInfo.setGroupByFlag("true");//表示需要group by查询
        return capYesswCaseInfoService.show(capYesswCaseInfo, Integer.valueOf(page), Integer.valueOf(limit));
    }

    /**
     * 获取12345案件的页面
     * @param model
     * @return
     */
    @GetMapping("loadYesswDataPage")
    public String loadYesswDataPage(Model model) {
        return "/system/yessw/loadYesswData";
    }


    @RequestMapping(value = "loadData")
    @ResponseBody
    public ResultData loadData(HttpServletRequest request) {
        String filingBeginTime = request.getParameter("filingBeginTime");
        String filingOverTime = request.getParameter("filingOverTime");
        String tokenStr = request.getParameter("tokenStr");
        ResultData result = new ResultData();
        try {
            ResultData resultData = capYesswCaseInfoService.saveCapYesswCaseInfo(filingBeginTime, filingOverTime, "", tokenStr);
            return resultData;
        } catch (Exception e) {
            e.printStackTrace();
            result.setFlag(false);
            result.setMsg("fail");
            return result;
        }
    }


    @RequestMapping(value = "editPage")
    public String editPage(String id, Model model, boolean detail) {
        CapYesswCaseInfo caseInfo = capYesswCaseInfoService.selectByPrimaryKey(id);
        CapYesswCaseInfo query = new CapYesswCaseInfo();
        query.setYesswNumber(caseInfo.getYesswNumber());
        List<CapYesswCaseInfo> caseInfoList = capYesswCaseInfoService.selectListByCondition(query);
        CapYesswCaseInfo capYesswCaseInfo = caseInfoList.get(0);
        model.addAttribute("capYesswCaseInfo", capYesswCaseInfo);
        model.addAttribute("detail", detail);
        return "/system/yessw/edit";
    }


    @ApiOperation(value = "/showProcessList", httpMethod = "GET", notes = "意见处理列表")
    @GetMapping(value = "showProcessList")
    @ResponseBody
    public ReType showAdviceDealList(CapYesswCaseAnalyze capYesswCaseAnalyze, HttpServletRequest request, Model model, String page, String limit) {
        String caseInfoId = request.getParameter("caseInfoId");
        CapYesswCaseInfo caseInfo = capYesswCaseInfoService.selectByPrimaryKey(caseInfoId);
        CapYesswCaseProcess process = new CapYesswCaseProcess();
        //process.setYesswCaseId(yesswCaseAnalyzeId);
        process.setYesswNumber(caseInfo.getYesswNumber());
        return capYesswCaseProcessService.show(process, Integer.valueOf(page), Integer.valueOf(limit));
    }



    @RequestMapping(value = "expExcel")
    public void expExcel(CapYesswCaseInfo capYesswCaseInfo, HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute("endflag");
        capYesswCaseInfo.setGroupByFlag("true");//表示需要group by查询
        List<CapYesswCaseInfo> list = capYesswCaseInfoService.selectListByCondition(capYesswCaseInfo);
        //List<CapYesswCaseInfo> list = capYesswCaseInfoService.findListQueryGroupBy(capYesswCaseInfo);
        ExportParams params = new ExportParams("案件列表", "案件列表", ExcelType.XSSF);
        try {
            Workbook workbook = ExcelExportUtil.exportBigExcel(params, CapYesswCaseInfo.class, list);
            //Workbook workbook = capYesswCaseInfoService.bigExcel(1, capYesswCaseInfo, null, params);
            ExcelExportUtil.closeExportBigExcel();
            //下载方法
            ExcelUtils.export(response, workbook, "案件列表");

            request.getSession().setAttribute("endflag", "1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequestMapping(value = "expWord")
    public void expWord(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        Map<String,Object> root = new HashMap<String,Object>();
        CapYesswCaseInfo caseInfo = capYesswCaseInfoService.selectByPrimaryKey(id);
        root.put("caseInfo", caseInfo);
        root.put("result", StringUtils.isBlank(caseInfo.getYesswResult())?"无":caseInfo.getYesswResult());
        root.put("yesswEndTime", StringUtils.isBlank(caseInfo.getYesswEndTime())?"-":caseInfo.getYesswEndTime());
        String basePath = request.getSession().getServletContext().getRealPath(ExportByFreeMarkerUtils.XLS_MODEL_PATH);
        String destFilePath = basePath + File.separatorChar + ExportByFreeMarkerUtils.XLS_MODEL_TEMP_PATH ;
        ExportByFreeMarkerUtils.exportWordByEasyPoi("xlsmodel/caseModel.docx", destFilePath,"案件信息.docx", root, request, response);
        //ExportByFreeMarkerUtils.exportWord(request, response, root, "caseModel.ftl", "案件信息");
    }



}
