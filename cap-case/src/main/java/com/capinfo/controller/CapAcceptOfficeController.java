package com.capinfo.controller;

import com.capinfo.base.BaseController;
import com.capinfo.entity.CapAcceptOffice;
import com.capinfo.entity.CapYesswCaseAnalyze;
import com.capinfo.service.CapAcceptOfficeService;
import com.capinfo.util.ReType;
import com.capinfo.utils.ResultData;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/acceptOffice")
public class CapAcceptOfficeController extends BaseController {

    @Autowired
    private CapAcceptOfficeService capAcceptOfficeService;

    /**
     * 承办单位list
     * @param model
     * @return
     */
    @GetMapping(value = "acceptOfficeList")
    @RequiresPermissions("case:show")
    public String tsAdvice(Model model) {
        return "/system/acceptOffice/acceptOfficeList";
    }

    @ApiOperation(value = "/showOfficeList", httpMethod = "GET", notes = "意见列表")
    @GetMapping(value = "showOfficeList")
    @ResponseBody
    @RequiresPermissions("case:show")
    public ReType showOfficeList(CapAcceptOffice capAcceptOffice, Model model, String page, String limit) {
        return capAcceptOfficeService.show(capAcceptOffice, Integer.valueOf(page), Integer.valueOf(limit));
    }


    @RequestMapping(value = "addOfficePage")
    public String addOffice(Model model) {
        model.addAttribute("capAcceptOffice", new CapAcceptOffice());
        return "/system/acceptOffice/addOffice";
    }


    @RequestMapping(value = "saveOffice")
    @ResponseBody
    public ResultData saveOffice(CapAcceptOffice capAcceptOffice, HttpServletRequest request) {
        if (StringUtils.isBlank(capAcceptOffice.getName())) {
            return ResultData.error("简称不能为空");
        }
        try {
            capAcceptOfficeService.save(capAcceptOffice);
            return ResultData.sucess("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.error("保存失败");
        }
    }

    @RequestMapping(value = "updateOffice")
    public String updateOffice(String id, Model model, boolean detail) {
        CapAcceptOffice capAcceptOffice = capAcceptOfficeService.selectByPrimaryKey(id);
        model.addAttribute("capAcceptOffice", capAcceptOffice);
        model.addAttribute("detail", detail);
        return "/system/acceptOffice/addOffice";
    }

    @RequestMapping(value = "delOffice")
    @ResponseBody
    public ResultData delOffice(String id) {
        try {
            CapAcceptOffice capAcceptOffice = capAcceptOfficeService.selectByPrimaryKey(id);
            capAcceptOfficeService.delOffice(capAcceptOffice);
            return ResultData.sucess("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.error("删除失败");
        }
    }


}
