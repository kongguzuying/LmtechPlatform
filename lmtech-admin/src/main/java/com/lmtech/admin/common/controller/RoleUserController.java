package com.lmtech.admin.common.controller;

import com.lmtech.admin.common.adaptor.RoleAdaptor;
import com.lmtech.admin.common.adaptor.UserAdaptor;
import com.lmtech.admin.common.config.ConfigInfo;
import com.lmtech.common.PageData;
import com.lmtech.infrastructure.model.User;
import com.lmtech.util.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/platform/ruser")
public class RoleUserController extends CheckPageControllerBase<User> {

	@Autowired
	private UserAdaptor userAdaptor;
	@Autowired
	private RoleAdaptor roleAdaptor;

	@Override
	@RequestMapping(value = "/checklist.do")
	public ModelAndView checkList(HttpServletRequest request, HttpServletResponse response, @RequestParam int type) {
		ModelAndView mav = super.checkList(request, response, type);
		mav.addObject("id", getId());
		Map<String, Object> filterParams = ServletUtil.getRequestMap(getRequest(), "userName,loginName,mobile",true);
        for(String key:filterParams.keySet()){
        	mav.addObject(key, filterParams.get(key));
        }
        mav.setViewName("platform/role/ruser");
		return mav;
	}

	@Override
	public PageData getAllPageData() {
		Map<String, Object> filterParams = ServletUtil.getRequestMap(getRequest(), "userName,loginName,mobile",false);
		return userAdaptor.getPageData(null, getPageIndex(), ConfigInfo.PAGE_SIZE);
	}
	
	@Override
	public PageData getAuthedPageData() {
		Map<String, Object> filterParams = ServletUtil.getRequestMap(getRequest(), "userName,loginName,mobile",false);
		return roleAdaptor.getRoleUserOfPage(getId(), null, super.getPageIndex(), ConfigInfo.PAGE_SIZE);
	}

	@Override
	public PageData getUnAuthedPageData() {
		Map<String, Object> filterParams = ServletUtil.getRequestMap(getRequest(), "userName,loginName,mobile",false);
		return roleAdaptor.getRoleUnauthUserOfPage(getId(), null, super.getPageIndex(), ConfigInfo.PAGE_SIZE);
	}

	@Override
	public List<User> getAllAuthedData() {
		return roleAdaptor.getRoleUsers(getId());
	}

	@Override
	public void fullAuthData(List<String> ids) {
		roleAdaptor.setRoleUsers(getId(), ids);
	}

	@Override
	public void increaseAuthData(List<String> ids) {
		roleAdaptor.addRoleUsers(getId(), ids);
	}

	@Override
	public void increaseUnAuthData(List<String> ids) {
		roleAdaptor.removeRoleUsers(getId(), ids);
	}

	private String getId() {
		return getRequest().getParameter("id");
	}

	@Override
	public String getRequestMapping() {
		return "platform/ruser";
	}
}
