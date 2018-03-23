package com.lmtech.admin.common.controller;

import com.lmtech.admin.common.adaptor.MenuAdaptor;
import com.lmtech.admin.common.adaptor.RoleAdaptor;
import com.lmtech.infrastructure.model.Menu;
import com.lmtech.util.ComplexUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/platform/rmenu")
public class RoleMenuController extends CheckControllerBase<Menu> {

	@Autowired
	private RoleAdaptor roleAdaptor;
	@Autowired
	private MenuAdaptor menuAdaptor;

	@Override
	@RequestMapping(value = "/checklist.do")
	public ModelAndView checkList(HttpServletRequest request, HttpServletResponse response, @RequestParam int type) {
		ModelAndView mav = super.checkList(request, response, type);

		//sort menu
		ComplexUtil<Menu> util = new ComplexUtil<Menu>();
		List<Menu> datas = (List<Menu>) mav.getModel().get("datas");
		List<Menu> compWebMenus = util.convertToComplex(datas);
		for (Menu item : compWebMenus) {
			//将顶层菜单父id置0
			item.setParentId("0");
		}
		datas = util.convertToList(compWebMenus);
		mav.getModel().put("datas", datas);

		mav.addObject("id", getId());
		mav.setViewName("platform/role/rmenu");
		return mav;
	}

	@Override
	public List<Menu> getAllData() {
		return menuAdaptor.getAll();
	}

	@Override
	public List<Menu> getAuthedData() {
		return roleAdaptor.getRoleMenus(getId());
	}

	@Override
	public List<Menu> getUnAuthedData() {
		return roleAdaptor.getRoleUnauthMenus(getId());
	}

	@Override
	public List<Menu> getAllAuthedData() {
		return roleAdaptor.getRoleMenus(getId());
	}

	@Override
	public void fullAuthData(List<String> ids) {
		roleAdaptor.setRoleMenus(getId(), ids);
	}

	@Override
	public void increaseAuthData(List<String> ids) {
		roleAdaptor.addRoleMenus(getId(), ids);
	}

	@Override
	public void increaseUnAuthData(List<String> ids) {
		roleAdaptor.removeRoleMenus(getId(), ids);
	}

	@Override
	public boolean isIncreaseAuth() {
		//NOTE 有条件查询时需为增量
		return false;
	}

	private String getId() {
		return getRequest().getParameter("id");
	}

	@Override
	public String getRequestMapping() {
		return "platform/rmenu";
	}
}
