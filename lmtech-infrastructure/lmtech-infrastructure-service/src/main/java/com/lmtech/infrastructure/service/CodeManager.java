package com.lmtech.infrastructure.service;

import java.io.Serializable;
import java.util.List;

import com.lmtech.infrastructure.model.CodeItem;
import com.lmtech.infrastructure.model.CodeType;
import com.lmtech.service.DbManagerBase;
import com.lmtech.service.NotAdminDbManager;

/**
 * 代码表管理接口
 * @author huang.jb
 *
 */
public interface CodeManager extends DbManagerBase<CodeType>, NotAdminDbManager {
	/**
	 * 添加代码项
	 * @param t
	 * @return
	 */
	String addCodeItem(CodeItem t);
	/**
	 * 更新代码项
	 * @param t
	 */
	void editCodeItem(CodeItem t);
	/**
	 * 删除代码项
	 * @param id
	 */
	void removeCodeItem(Serializable id);
	/**
	 * 获取代码项
	 * @param id
	 * @return
	 */
	CodeItem getCodeItem(Serializable id);
	/**
	 * 获取所有的代码项
	 * @return
	 */
	List<CodeItem> getAllCodeItem();
	/**
	 * 获取某一代码项
	 * @param typeCode
	 * @return
	 */
	List<CodeItem> getCodeItemOfType(String typeCode);
}
