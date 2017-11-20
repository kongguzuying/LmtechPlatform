package com.lmtech.admin.common.controller;

import com.lmtech.common.ComplexBase;
import com.lmtech.util.ComplexUtil;
import com.lmtech.util.StringUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 复合类控制器基类
 * @param <T>
 */
public abstract class ComplexControllerBase<T extends ComplexBase<T>> extends ManagerControllerBase<T> {

	@Override
	@RequestMapping(value = "/list.do")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) {
		List<T> entitys = getManager().getAll();
		
		ComplexUtil<T> util = new ComplexUtil<T>();
		List<T> ts = util.convertToComplex(entitys);
		for (T item : ts) {
			//将顶层菜单父id置0
			item.setParentId("0");
		}
		entitys = util.convertToList(ts);

		ModelAndView mav = new ModelAndView();
		mav.addObject("entitys", entitys);
		mav.setViewName(String.format("%1$s/list", this.getRequestMapping()));
		return mav;
	}
	
	@Override
	@RequestMapping(value = "/edit.do", method = RequestMethod.GET)
	public ModelAndView editIndex(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") String id) {
		ModelAndView mav = new ModelAndView();

		String parentId;
		String parentName;

		if (!StringUtil.isNullOrEmpty(id)) {
			//编辑
			T entity = this.getManager().get(id);
			parentId = entity.getParentId();
			mav.addObject("entity", entity);
		} else {
			//新增
			parentId = request.getParameter("parentId");
			mav.addObject("parentId", parentId);
		}

		T parent = getManager().get(parentId);
		if (parent != null) {
			parentName = parent.getName();
			mav.addObject("parentName", parentName);
		}
		mav.setViewName(String.format("%1$s/edit", this.getRequestMapping()));
		return mav;
	}

	@RequestMapping(value = "/selparent.do", method = RequestMethod.GET)
	public ModelAndView selParentIndex(@RequestParam("id") String id,@RequestParam("parentId") String parentId) {
		List<T> oldTs = getManager().getAll();
		ComplexUtil<T> util = new ComplexUtil<T>();
		List<T> ts = util.convertToComplex(oldTs);
		for (T item : ts) {
			//将顶层菜单父id置0
			item.setParentId("0");
		}
		
		List<String> exceptIds = new ArrayList<String>();
		if (!StringUtil.isNullOrEmpty(id)) {
			//去除自已，以免自己的父菜单是自己造成死循环
			exceptIds.add(id);
		}
		List<T> entitys = util.convertToList(ts, exceptIds);

		ModelAndView mav = new ModelAndView();
		mav.addObject("entitys", entitys);
		mav.addObject("parentId", parentId);
		mav.setViewName(String.format("%1$s/selparent", this.getRequestMapping()));
		return mav;
	}

}
