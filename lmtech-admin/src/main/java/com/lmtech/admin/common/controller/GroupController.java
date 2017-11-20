package com.lmtech.admin.common.controller;

import com.lmtech.admin.common.adaptor.ControllerManager;
import com.lmtech.admin.common.adaptor.GroupAdaptor;
import com.lmtech.admin.common.adaptor.UserAdaptor;
import com.lmtech.infrastructure.model.Group;
import com.lmtech.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 群组管理控制器
 * Created by huang.jb on 2016-8-11.
 */
@Controller
@RequestMapping("/platform/group")
public class GroupController extends ManagerControllerBase<Group> {

    @Autowired
    private GroupAdaptor groupAdaptor;

    @Autowired
    private UserAdaptor userAdaptor;

    @Override
    public ControllerManager<Group> getManager() {
        return groupAdaptor;
    }

    @Override
    public void fillEntity(Group dbEntity, Group pageEntity) {
    }

    /**
     * 部门/用户选择列表(公共组件)
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/sellist.do", method = RequestMethod.GET)
    public ModelAndView selList(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName(this.getRequestMapping() + "/sellist");
        return mav;
    }

    /**
     * 部门选择列表(公共组件)
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/selGroupList.do", method = RequestMethod.GET)
    public ModelAndView selGroupList(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName(this.getRequestMapping() + "/selGroupList");
        return mav;
    }


    @RequestMapping(value = "/toEdit.do")
    public ModelAndView toEdit(HttpServletRequest request, HttpServletResponse response, @RequestParam String parentId, @RequestParam String id) {
        if (StringUtil.isNullOrEmpty(parentId)
                && StringUtil.isNullOrEmpty(id)) {
            return this.returnFailed(response, "参数错误", "参数错误");
        }
        ModelAndView mav = new ModelAndView();
        Group entity;
        if (!StringUtil.isNullOrEmpty(id)) {
            entity = groupAdaptor.get(id);
        } else {
            entity = new Group();
            entity.setParentId(parentId);
        }
        mav.addObject("entity", entity);
        mav.setViewName(getRequestMapping() + "/edit");
        return mav;
    }

    /**
     * 添加/修改部门
     *
     * @param request
     * @param response
     * @param entity
     * @return
     */
    @RequestMapping(value = "/doEdit.do")
    public ModelAndView doEdit(HttpServletRequest request, HttpServletResponse response, @ModelAttribute Group entity) {
        if ((StringUtil.isNullOrEmpty(entity.getParentId())
                && StringUtil.isNullOrEmpty(entity.getId()))
                || StringUtil.isNullOrEmpty(entity.getName())) {
            return this.returnFailed(response, "参数错误", "参数错误");
        }
        if (StringUtil.isNullOrEmpty(entity.getId())) {
            //添加
            groupAdaptor.add(entity);
        } else {
            //修改
            groupAdaptor.edit(entity);
        }
        return returnSuccess(response, "分组添加成功", entity);
    }

    @RequestMapping(value = "/removeById.do")
    public ModelAndView removeById(HttpServletRequest request, HttpServletResponse response, String id, String parentId) {
        groupAdaptor.remove(id);
        return returnSuccess(response, "部门删除成功", parentId);
    }

    /**
     * ajax请求子部门
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/ajaxChildren.do")
    public ModelAndView ajaxChildren(HttpServletRequest request, HttpServletResponse response, @RequestParam String parentId) {
        List<Group> ds;
        if (StringUtil.isNullOrEmpty(parentId)) {
            ds = groupAdaptor.getRootGroup();
        } else {
            ds = groupAdaptor.getChildrenGroup(parentId);
        }
        responseObjectJson(response, ds);
        return null;
    }

    @Override
    public String getRequestMapping() {
        return "platform/group";
    }

}
