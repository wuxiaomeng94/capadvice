package com.capinfo.controller;

import com.alibaba.fastjson.JSONArray;
import com.capinfo.base.CurrentUser;
import com.capinfo.core.annotation.Log;
import com.capinfo.core.shiro.ShiroUtil;
import com.capinfo.entity.SysMenu;
import com.capinfo.entity.SysRole;
import com.capinfo.entity.SysUser;
import com.capinfo.mapper.SysMenuMapper;
import com.capinfo.service.MenuService;
import com.capinfo.service.RoleService;
import com.capinfo.service.SysUserService;
import com.capinfo.util.VerifyCodeUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author zhuxiaomeng
 * @date 2017/12/4.
 * @email 154040976@qq.com
 * 登录、退出页面
 */
@Controller
@Slf4j
public class LoginController {

    @Autowired
    private SysUserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;

    @GetMapping(value = "")
    public String loginInit(Model model) {
        return loginCheck(model);
    }

    @GetMapping(value = "goLogin")
    public String goLogin(Model model, ServletRequest request) {
        Subject sub = SecurityUtils.getSubject();
        if (sub.isAuthenticated()) {
            return "/main/main";
        } else {
            model.addAttribute("message", "请重新登录");
            return "/login";
            //return "/login-advice";
        }
    }

    @GetMapping(value = "/login")
    public String loginCheck(Model model) {
        Subject sub = SecurityUtils.getSubject();
        Boolean flag2 = sub.isRemembered();
        boolean flag = sub.isAuthenticated() || flag2;
        Session session = sub.getSession();
        if (flag) {
            return "/main/main";
        }
        Integer loginNum = (Integer)session.getAttribute("loginNum");
        if (loginNum == null) {
            loginNum = 0;
            session.setAttribute("loginNum", 0);
        }
        model.addAttribute("logNum", loginNum);
        //return "/login";
        return "/login-advice";
    }

    /**
     * 登录动作
     *
     * @param user
     * @param model
     * @param rememberMe
     * @return
     */
    @ApiOperation(value = "/login", httpMethod = "POST", notes = "登录method")
    @PostMapping(value = "/login")
    public String login(SysUser user, Model model, String rememberMe, HttpServletRequest request) {
        Integer loginNum = (Integer)request.getSession().getAttribute("loginNum");
        if (loginNum == null) {
            loginNum = 0;
            request.getSession().setAttribute("loginNum", 0);
        } else {
            loginNum = loginNum+1;
            request.getSession().setAttribute("loginNum", loginNum);
        }
        model.addAttribute("logNum", loginNum);
        String codeMsg = (String) request.getAttribute("shiroLoginFailure");
        /*if (loginNum>=3) {
            if ("code.error".equals(codeMsg)) {
                model.addAttribute("message", "验证码错误");
                return "/login";

            }
        }*/

        //有的浏览器出不来这个验证码图片。暂时去掉吧
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername().trim(),
                user.getPassword());
        Subject subject = ShiroUtil.getSubject();
        String msg = null;
        try {
            subject.login(token);
            //subject.hasRole("admin");
            if (subject.isAuthenticated()) {
                return "redirect:/main";
            }
        } catch (UnknownAccountException e) {
            msg = "用户名/密码错误";
        } catch (IncorrectCredentialsException e) {
            msg = "用户名/密码错误";
        } catch (ExcessiveAttemptsException e) {
            msg = "登录失败多次，账户锁定10分钟";
        }
        if (msg != null) {
            model.addAttribute("message", msg);
        }

        //return "/login";
        return "/login-advice";
    }

    @GetMapping("/main")
    public String main(Model model){
        CurrentUser currentUser = (CurrentUser) SecurityUtils.getSubject().getSession().getAttribute("curentUser");
        List<SysRole> roleList = roleService.getRoleListByUser(currentUser.getId());
        //意见箱的。登录之后的页面
        roleList.forEach((role) -> {
            if ("5d8fab9208ff405689de173c2b25622e".equals(role.getId())) {
                model.addAttribute("mainUrl", "/advice/dealProblem");
                model.addAttribute("mainTitle", "举报问题");//举报问题这个菜单的菜单id。如果需要和其他菜单一样关闭切换的话需要这个
            }
        });
        //12345案件情况的。登录之后跳转的页面
        /*roleList.forEach((role) -> {
            if ("eb431e606e4b45079297752481bd2f21".equals(role.getId())) {
                //12345案件录入人员
                model.addAttribute("mainUrl", "/caseAnalyze/yesswList");
                model.addAttribute("mainTitle", "录入已派单案件");
            } else if ("cf2c4ecc5fd24191a8aa1cfd14a12f13".equals(role.getId())) {
                //12345案件管理人员
                model.addAttribute("mainUrl", "/caseAnalyze/yesswList");
                model.addAttribute("mainTitle", "录入已派单案件");
            }
        });*/
        return "/main/main";
    }

    @Log(desc = "用户退出平台")
    @GetMapping(value = "/logout")
    public String logout() {
        Subject sub = SecurityUtils.getSubject();
        sub.logout();
        return "/login";
        //return "/login-advice";
    }

    /**
     * 组装菜单json格式
     * update by 17/12/13
     *
     * @return
     */
    public JSONArray getMenuJson() {
        List<SysMenu> mList = menuService.getMenuNotSuper();
        JSONArray jsonArr = new JSONArray();
        for (SysMenu sysMenu : mList) {
            SysMenu menu = getChild(sysMenu.getId());
            jsonArr.add(menu);
        }
        return jsonArr;
    }

    public SysMenu getChild(String id) {
        SysMenu sysMenu = menuService.selectByPrimaryKey(id);
        List<SysMenu> mList = menuService.getMenuChildren(id);
        for (SysMenu menu : mList) {
            SysMenu m = getChild(menu.getId());
            sysMenu.addChild(m);
        }
        return sysMenu;
    }


    @GetMapping(value = "/getCode")
    public void getYzm(HttpServletResponse response, HttpServletRequest request) {
        try {
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/jpg");

            //生成随机字串
            String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
            log.info("verifyCode:{}",verifyCode);
            //存入会话session
            HttpSession session = request.getSession(true);
            session.setAttribute("_code", verifyCode.toLowerCase());
            //生成图片
            int w = 146, h = 33;
            VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
