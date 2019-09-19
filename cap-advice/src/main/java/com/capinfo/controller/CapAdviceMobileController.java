package com.capinfo.controller;


import com.capinfo.base.BaseController;
import com.capinfo.entity.CapAdvice;
import com.capinfo.entity.CapAdviceDeal;
import com.capinfo.service.CapAdviceDealService;
import com.capinfo.service.CapAdviceService;
import com.capinfo.util.JsonUtil;
import com.capinfo.util.ReType;
import com.capinfo.util.UploadUtil;
import com.capinfo.utils.Base64;
import com.capinfo.utils.ResultData;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(value = "/adviceMobile")
public class CapAdviceMobileController extends BaseController {

    @Autowired
    private CapAdviceService capAdviceService;
    @Autowired
    private CapAdviceDealService capAdviceDealService;

    @Autowired
    UploadUtil uploadUtil;
    @Value("${capsp.imagePath}")
    private String imagePath;
    /*@Value("${capsp.prodAddress}")
    private String prodAddress;*/


    @GetMapping(value = "adviceIndex")
    public String adviceIndex(Model model) {
        return "/system/advice/mobile/advicebox-index";
    }


    /**
     * 选择举报问题的类型。选择是实名举报还是匿名举报
     * @param model
     * @return
     */
    @GetMapping(value = "chooseProblemType")
    public String chooseProblemType(Model model) {
        return "/system/advice/mobile/chooseReportType";
    }


    /**
     * 手机进入的添加页面    -举报问题
     * @param model
     * @return
     */
    @GetMapping(value = "addProblem")
    public String addProblem(HttpServletRequest request, Model model) {
        model.addAttribute("reportType", request.getParameter("reportType"));
        model.addAttribute("pageType", "addProblem");
        return "/system/advice/mobile/add-advice-mobile";
    }

    /**
     * 手机进入的添加页面    -意见建议
     * @param model
     * @return
     */
    @GetMapping(value = "addAdvice")
    public String addAdvice(Model model) {
        model.addAttribute("pageType", "addAdvice");
        return "/system/advice/mobile/add-advice-mobile";
    }


    /**
     * 案件查询
     * @param model
     * @return
     */
    @GetMapping(value = "adviceQuery")
    public String adviceQuery(HttpServletRequest request,Model model) {
        model.addAttribute("selectCode", request.getParameter("selectCode"));
        return "/system/advice/mobile/adviceQuery";
    }



    @ApiOperation(value = "/queryAdviceByCode", httpMethod = "POST", notes = "查询意见")
    @PostMapping(value = "queryAdviceByCode")
    @ResponseBody
    public ResultData queryAdviceByCode(String code) {
        if (StringUtils.isEmpty(code)) {
            return ResultData.error("查询码不能为空");
        }
        ResultData result = new ResultData();
        CapAdvice capAdvice = new CapAdvice();
        capAdvice.setAdviceCode(code);
        List<CapAdvice> list = capAdviceService.select(capAdvice);
        if (list.size()>1) {
            return ResultData.error("查询结果不唯一。请核对查询码");
        } else if (list.size()==0) {
            return ResultData.error("查询结果为空");
        } else {
            result.setFlag(true);
            result.setMsg("查询成功");
            result.setData(list.get(0));
        }
        return result;
    }





    /*@ApiOperation(value = "/addAdvice", httpMethod = "POST", notes = "添加意见")
    @PostMapping(value = "addAdvice")
    @ResponseBody
    public JsonUtil addAdvice(CapAdvice capAdvice) {
        if (StringUtils.isEmpty(capAdvice.getTitle())) {
            JsonUtil.error("角色名称不能为空");
        }
        JsonUtil j = new JsonUtil();
        try {
            capAdvice.setId(UUID.randomUUID().toString().replace("-", "").toLowerCase());
            capAdviceService.insertByMobile(capAdvice);
            j.setMsg(capAdvice.getId());
        } catch (MyException e) {
            j.setMsg("保存失败");
            j.setFlag(false);
            e.printStackTrace();
        }
        return j;
    }*/

    @PostMapping(value = "addAdvice")
    public String addAdvice(CapAdvice capAdvice, HttpServletRequest request) {
        String pageType = request.getParameter("pageType");
        String id = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        //传图片，改成传base64的方式
        String baseImg = request.getParameter("fileBase64");
        setImgBase64(capAdvice, baseImg, id);
        capAdvice.setId(id);
        capAdviceService.insertByMobile(capAdvice);
        return "redirect:addAdviceResult?id="+capAdvice.getId()+"&pageType="+pageType;
    }


    @ApiOperation(value = "/addAdviceApi", httpMethod = "POST", notes = "添加意见")
    @RequestMapping(value = "addAdviceApi")
    @ResponseBody
    public ResultData addAdviceApi(CapAdvice capAdvice, HttpServletRequest request) {
        //String pageType = request.getParameter("pageType");
        try {
            String id = UUID.randomUUID().toString().replace("-", "").toLowerCase();
            //传图片，改成传base64的方式
            String baseImg = request.getParameter("fileBase64");
            setImgBase64(capAdvice, baseImg, id);
            capAdvice.setId(id);
            capAdviceService.insertByMobile(capAdvice);
            ResultData result = ResultData.sucess("success");
            result.setData(capAdvice);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.error("fail");
        }
    }

    @ApiOperation(value = "/queryByIdApi", httpMethod = "GET", notes = "根据id查询")
    @RequestMapping(value = "queryByIdApi")
    @ResponseBody
    public ResultData queryByIdApi(String id, HttpServletRequest request) {
        try {
            CapAdvice capAdvice = capAdviceService.selectByPrimaryKey(id);
            ResultData result = ResultData.sucess("查询成功");
            result.setData(capAdvice);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.error("查询失败");
        }
    }



    /**
     * 提交之后查看查询码
     * @param id
     * @param model
     * @return
     */
    @GetMapping(value = "addAdviceResult")
    public String addAdviceResult(String id, HttpServletRequest request, Model model) {
        CapAdvice capAdvice = capAdviceService.selectByPrimaryKey(id);
        model.addAttribute("capAdvice", capAdvice);
        model.addAttribute("pageType", request.getParameter("pageType"));
        //model.addAttribute("prodAddress", prodAddress);
        return "/system/advice/mobile/addadvice-result";
    }



    /**
     * 意见处理情况list的接口
     * @param capAdvice
     * @param request
     * @param model
     * @param page
     * @param limit
     * @return
     */
    @ApiOperation(value = "/showAdviceDealList", httpMethod = "GET", notes = "意见处理列表")
    @GetMapping(value = "showAdviceDealList")
    @ResponseBody
    public ReType showAdviceDealList(CapAdvice capAdvice, HttpServletRequest request, Model model, String page, String limit) {
        String adviceId = request.getParameter("adviceId");
        CapAdviceDeal deal = new CapAdviceDeal();
        deal.setCapAdviceId(adviceId);
        deal.setStatus("2");
        return capAdviceDealService.show(deal, Integer.valueOf(page), Integer.valueOf(limit));
    }

    @ApiOperation(value = "/queryAdviceDealList", httpMethod = "GET", notes = "意见处理列表")
    @GetMapping(value = "queryAdviceDealList")
    @ResponseBody
    public ResultData queryAdviceDealList(String adviceId, HttpServletRequest request) {
        try {
            CapAdviceDeal deal = new CapAdviceDeal();
            deal.setCapAdviceId(adviceId);
            deal.setStatus("2");
            List<CapAdviceDeal> dealList = capAdviceDealService.selectListByPage(deal);
            ResultData result = ResultData.sucess("success");
            result.setData(dealList);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.error("fail");
        }
    }





    @PostMapping(value = "upload")
    @ResponseBody
    public JsonUtil imgUpload(HttpServletRequest req, @RequestParam("file") MultipartFile file,
                              ModelMap model) {
        String name = file.getOriginalFilename();
        String fileName=uploadUtil.upload(file);
        JsonUtil j = new JsonUtil();
        j.setMsg(fileName+","+name);
        return j;
    }


    private void setImgBase64(CapAdvice capAdvice, String baseImg, String id) {
        if (StringUtils.isNotBlank(baseImg)) {
            String str = baseImg.substring(baseImg.indexOf("/")+1, baseImg.indexOf(";"));
            String base64 = baseImg.substring(baseImg.indexOf(",") + 1);
            String imgName = id + "." +str;
            String imgPath = imagePath + File.separator + imgName;
            Base64.Base64ToImage(base64, imgPath, imgName);
            capAdvice.setFile(imgName);
        }
    }



}
