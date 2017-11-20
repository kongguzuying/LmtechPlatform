package com.lmtech.admin.common.controller;

import com.lmtech.admin.common.adaptor.RoleAdaptor;
import com.lmtech.admin.common.adaptor.UserAdaptor;
import com.lmtech.admin.common.config.ConfigInfo;
import com.lmtech.common.PageData;
import com.lmtech.infrastructure.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/platform/urole")
public class UserRoleController extends CheckPageControllerBase<Role> {

	@Autowired
	private UserAdaptor userAdaptor;
	@Autowired
	private RoleAdaptor roleAdaptor;

	@Override
	@RequestMapping(value = "/checklist.do")
	public ModelAndView checkList(HttpServletRequest request, HttpServletResponse response, @RequestParam int type) {
		ModelAndView mav = super.checkList(request, response, type);
		mav.addObject("id", getId());
		mav.setViewName("platform/user/urole");

		return mav;
	}

	@Override
	public PageData getAllPageData() {
		return roleAdaptor.getPageData(null, super.getPageIndex(), ConfigInfo.PAGE_SIZE);
	}

	@Override
	public PageData getAuthedPageData() {
		return userAdaptor.getUserRoleOfPage(getId(), null, super.getPageIndex(), ConfigInfo.PAGE_SIZE);
	}

	@Override
	public PageData getUnAuthedPageData() {
		return userAdaptor.getUserUnauthRoleOfPage(getId(), null, super.getPageIndex(), ConfigInfo.PAGE_SIZE);
	}

	@Override
	public List<Role> getAllAuthedData() {
		return userAdaptor.getUserRoles(getId());
	}

	@Override
	public void fullAuthData(List<String> ids) {
		userAdaptor.setUserRoles(getId(), ids);
	}

	@Override
	public void increaseAuthData(List<String> ids) {
		userAdaptor.addUserRoles(getId(), ids);
	}

	@Override
	public void increaseUnAuthData(List<String> ids) {
		userAdaptor.removeUserRoles(getId(), ids);
	}

	private String getId() {
		return getRequest().getParameter("id");
	}

	@Override
	public String getRequestMapping() {
		return "platform/urole";
	}
}
