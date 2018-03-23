package com.lmtech.admin.common.controller;

import com.lmtech.common.PageData;
import com.lmtech.model.EntityBase;
import com.lmtech.util.StringUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Check分页基础Action
 * @author huang.jb
 *
 */
public abstract class CheckPageControllerBase<T extends EntityBase> extends CheckControllerBase<T> {

	public abstract PageData getAllPageData();
	public abstract PageData getAuthedPageData();
	public abstract PageData getUnAuthedPageData();

	private PageData pd;

	@Override
	@RequestMapping(value = "/checklist.do")
	public ModelAndView checkList(HttpServletRequest request, HttpServletResponse response, @RequestParam int type) {
		ModelAndView mav = super.checkList(request, response, type);
		mav.addObject("pageData", pd);
		return mav;
	}

	@Override
	public List<T> getAllData() {
		pd = this.getAllPageData();
		return this.convertPageDataToList(pd);
	}

	@Override
	public List<T> getAuthedData() {
		pd = this.getAuthedPageData();
		return this.convertPageDataToList(pd);
	}

	@Override
	public List<T> getUnAuthedData() {
		pd = this.getUnAuthedPageData();
		return this.convertPageDataToList(pd);
	}

	@Override
	public boolean isIncreaseAuth() {
		return true;
	}

	private List<T> convertPageDataToList(PageData pd) {
		if (pd != null) {
			@SuppressWarnings("unchecked")
			List<T> result = (List<T>) pd.getItems();
			return result;
		} else {
			return new ArrayList<T>();
		}
	}

	protected int getPageIndex() {
		String strPageIndex = getRequest().getParameter("pageIndex");
		if (!StringUtil.isNullOrEmpty(strPageIndex)) {
			return Integer.parseInt(strPageIndex);
		} else {
			return 1;
		}
	}
}
