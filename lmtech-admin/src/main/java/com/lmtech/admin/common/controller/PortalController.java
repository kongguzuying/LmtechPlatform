package com.lmtech.admin.common.controller;

import com.lmtech.admin.common.adaptor.AccountAdaptor;
import com.lmtech.admin.common.adaptor.MenuAdaptor;
import com.lmtech.common.ContextManager;
import com.lmtech.infrastructure.model.Menu;
import com.lmtech.infrastructure.model.User;
import com.lmtech.util.LoggerManager;
import com.lmtech.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 入口控制器
 * Created by huang.jb on 2016-7-7.
 */
@Controller
@RequestMapping("/platform/portal")
public class PortalController extends ControllerBase {

    @Autowired
    private AccountAdaptor accountAdaptor;
    @Autowired
    private MenuAdaptor menuAdaptor;

    @RequestMapping(value = "/index.do", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView();

        //set menu
        String userId = ContextManager.getContext().getUserId();
        List<Menu> menus = menuAdaptor.getCurrentUserMenus();
        mav.addObject("menus", menus);
        mav.addObject("time", System.currentTimeMillis());
        mav.addObject("userId", userId);

        mav.setViewName("platform/portal/index");

        return mav;
    }

    @RequestMapping(value = "/login.do", method = RequestMethod.GET)
    public ModelAndView loginIndex() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("platform/portal/login");

        return mav;
    }

    @RequestMapping(value = "/login.do", method = RequestMethod.POST)
    public ModelAndView loginCommit(HttpServletRequest request, @RequestParam String loginName, @RequestParam String password) {
        ModelAndView mav = new ModelAndView();

        try {
            User user = accountAdaptor.authenticate(loginName, password);
            request.getSession().setAttribute("cur_user", user);
            mav.setViewName("redirect:/platform/portal/index.do");
        } catch (Exception e) {
            mav.setViewName("/platform/portal/login");
            mav.addObject("errMsg", e.getMessage());
        }
        return mav;
    }

    @RequestMapping(value = "/changepswd.do", method = RequestMethod.GET)
    public ModelAndView chagepswdIndex() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/platform/portal/changepswd");

        return mav;
    }

    @RequestMapping(value = "/changepswd.do", method = RequestMethod.POST)
    public ModelAndView chagepswdCommit(HttpServletResponse response, @RequestParam String oldPassword, @RequestParam String newPassword, @RequestParam String repeatPassword) {
        try {
            String accountId = ContextManager.getContext().getAccountId();
            String loginName = ContextManager.getContext().getLoginName();

            accountAdaptor.authenticate(loginName, oldPassword);

            if (!StringUtil.isNullOrEmpty(newPassword) && !StringUtil.isNullOrEmpty(repeatPassword)) {
                if (!newPassword.equals(repeatPassword)) {
                    return returnFailed(response, "两次输入的密码不一致，请重新输入！");
                }
            } else {
                return returnFailed(response, "密码不允许为空！");
            }

            accountAdaptor.changePswd(accountId, newPassword);

            return returnSuccess(response, "修改密码成功！");
        } catch (Exception e) {
        	LoggerManager.error("修改密码未知错误",e);
            return returnFailed(response, "修改密码出现错误，");
        }
    }

    @RequestMapping(value = "/logout.do")
    public ModelAndView logout(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("platform/portal/login");

        accountAdaptor.logout();

        return mav;
    }

    @RequestMapping(value = "/welcome.do")
    public ModelAndView welcome() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("platform/portal/welcome");
        return mav;
    }

    @Override
    public String getRequestMapping() {
        return "platform/portal";
    }
}
