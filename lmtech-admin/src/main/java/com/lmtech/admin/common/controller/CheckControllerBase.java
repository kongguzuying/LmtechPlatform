package com.lmtech.admin.common.controller;

import com.lmtech.model.DbEntityBase;
import com.lmtech.model.EntityBase;
import com.lmtech.util.LoggerManager;
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
 * Check基础Action
 * @author huang.jb
 *
 */
public abstract class CheckControllerBase<T extends EntityBase> extends ControllerBase {

	protected static int TYPE_ALL = 0;
	protected static int TYPE_AUTH = 1;
	protected static int TYPE_UNAUTH = 2;

	/**
	 * 获取所有数据
	 * @return
	 */
	public abstract List<T> getAllData();
	/**
	 * 获取已授权数据
	 * @return
	 */
	public abstract List<T> getAuthedData();
	/**
	 * 获取未授权数据
	 * @return
	 */
	public abstract List<T> getUnAuthedData();
	/**
	 * 获取所有已授权数据
	 * @return
	 */
	public abstract List<T> getAllAuthedData();
	/**
	 * 全量授权数据
	 * @param ids
	 */
	public abstract void fullAuthData(List<String> ids);
	/**
	 * 增量授权数据
	 * @param ids
	 */
	public abstract void increaseAuthData(List<String> ids);
	/**
	 * 增量取消授权数据
	 * @param ids
	 */
	public abstract void increaseUnAuthData(List<String> ids);
	/**
	 * 是否增量授权
	 * @return
	 */
	public abstract boolean isIncreaseAuth();

	@RequestMapping(value = "/checklist.do")
	public ModelAndView checkList(HttpServletRequest request, HttpServletResponse response, @RequestParam int type) {
		List<T> datas;
		if (type == TYPE_ALL) {
			datas = this.getAllData();
		} else if (type == TYPE_AUTH) {
			datas = this.getAuthedData();
		} else if (type == TYPE_UNAUTH) {
			datas = this.getUnAuthedData();
		} else {
			//默认取所有项
			datas = this.getAllData();
		}
		
		//set selected ids
		List<T> authedDatas = this.getAllAuthedData();
		String viewSelIds = this.getSelIds(authedDatas);

		ModelAndView mav = new ModelAndView();
		mav.setViewName(String.format("%1$s/checklist", this.getRequestMapping()));
		mav.addObject("datas", datas);
		mav.addObject("selIds", authedDatas);
		mav.addObject("viewSelIds", viewSelIds);
		mav.addObject("type", type);

		return mav;
	}

	@RequestMapping(value = "/check.do", method = RequestMethod.POST)
	public ModelAndView checkCommit(HttpServletRequest request, HttpServletResponse response, String selectedIds, @RequestParam int type, @RequestParam String unSelectedIds) {
		try {
			boolean isIncrease = this.isIncreaseAuth();
			List<String> authedIds = this.getItemIds(selectedIds);
			List<String> unAuthedIds = this.getItemIds(unSelectedIds);
			
			if (isIncrease) {
				if(authedIds != null && authedIds.size() > 0){
					//增量授权
					increaseAuthData(authedIds);
				}
				
				if (type != TYPE_UNAUTH && null != unAuthedIds 
						&& unAuthedIds.size() > 0) {
					//在未授权列表中不需要执行增量未授权
					increaseUnAuthData(unAuthedIds);
				}
			} else {
				//全量授权
				if (type == TYPE_UNAUTH) {
					//需预先将已授权的项提取，否则将造成已授权的项丢失
					List<T> authedData = getAllAuthedData();
					
					if (authedData != null && authedData.size() > 0) {
						for (T t : authedData) {
							String id = this.getEntityId(t);
							if (!StringUtil.isNullOrEmpty(id)) {
								authedIds.add(id);
							}
						}
					}
				}
				
				fullAuthData(authedIds);
			}
			
			returnSuccess(response);
		} catch (Exception e) {
			LoggerManager.error(e);
			returnFailed(response);
		}
		return null;
	}

	/**
	 * 是否启用分页
	 * @return
     */
	public boolean enablePager() {
		return false;
	}
	
	private List<String> getItemIds(String idsString) {
		List<String> result = new ArrayList<String>();
		if (!StringUtil.isNullOrEmpty(idsString)) {
			String[] ids = idsString.split(",");
			for (int i = 0; i < ids.length; i++) {
				if (!StringUtil.isNullOrEmpty(ids[i]) && !ids[i].trim().equals("null")) {
					result.add(ids[i].trim());
				}
			}
		}
		return result;
	}
	
	private String getSelIds(List<T> list) {
		String viewSelIds = "";
		if (list != null) {
			int i = 0;
			for (Object l : list) {
				String id = this.getEntityId(l);
				
				if (i > 0) {
					viewSelIds += ",";
				}
				viewSelIds += id;
				i++;
			}
		}
		return viewSelIds;
	}
	
	protected String getEntityId(Object t) {
		String id = null;
		if (t instanceof DbEntityBase) {
			DbEntityBase item = (DbEntityBase) t;
			id = item.getId();
		} else {
			LoggerManager.warn(String.format("不支持的实体类型%1$s", t.getClass().getName()));
		}
		return id;
	}
}
