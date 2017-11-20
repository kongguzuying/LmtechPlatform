package com.lmtech.admin.common.controller;

import com.lmtech.admin.common.adaptor.ControllerManager;
import com.lmtech.admin.common.adaptor.RoleAdaptor;
import com.lmtech.infrastructure.model.Role;
import com.lmtech.infrastructure.model.UserRole;
import com.lmtech.util.JsonUtil;
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
 * Created by huang.jb on 2016-7-12.
 */
@Controller
@RequestMapping("/platform/role")
public class RoleController extends ManagerControllerBase<Role> {

    @Autowired
    private RoleAdaptor roleAdaptor;

    @Override
    public ControllerManager<Role> getManager() {
        return roleAdaptor;
    }

    @Override
    public void fillEntity(Role dbEntity, Role pageEntity) {
        dbEntity.setName(pageEntity.getName());
        dbEntity.setRemark(pageEntity.getRemark());
        dbEntity.setEnable(pageEntity.isEnable());
        dbEntity.setLevel(pageEntity.getLevel());
    }

    @Override
    public String getRequestMapping() {
        return "platform/role";
    }

    /**
     * 角色选择列表
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/sellist.do", method = RequestMethod.GET)
    public ModelAndView selList(HttpServletRequest request, HttpServletResponse response, @RequestParam String did, @RequestParam String uid) {
        if (StringUtil.isNullOrEmpty(did) && StringUtil.isNullOrEmpty(uid)) {
            return this.returnFailed(response, "参数错误", "参数错误");
        }
        ModelAndView mav = new ModelAndView();

        List<Role> rs = roleAdaptor.getCurrentUserRoles();

        mav.addObject("rs", rs);
        String selJson = "";
        if (!StringUtil.isNullOrEmpty(uid)) {// 如果用户id存在,表示根据用户设置角色
            List<UserRole> selAll = roleAdaptor.getUserRoleByUserId(uid);
            selJson = JsonUtil.toJson(selAll);
            mav.addObject("uid", uid);
        }
        mav.addObject("selJson", selJson);
        mav.setViewName(this.getRequestMapping() + "/sellist");
        return mav;
    }

}
