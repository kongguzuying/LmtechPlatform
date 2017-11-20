package com.lmtech.infrastructure.service;

import java.util.List;

import com.lmtech.infrastructure.model.CodeItem;
import com.lmtech.infrastructure.model.CodeType;

/**
 * 代码服务
 * @author huang.jb
 *
 */
public interface CodeService {
	/**
	 * 获取代码表及代码项
	 * @return
     */
	List<CodeType> getCodes();

	/**
	 * 根据类型获取代码项
	 * @param typeCode
	 * @return
	 */
	List<CodeItem> getCodeItemOfType(String typeCode);

	/**
	 * 获取代码项文本
	 * @param typeCode
	 * @param codeItemValue
     * @return
     */
	String getCodeItemText(String typeCode, String codeItemValue);

	/**
	 * 获取代码项值
	 * @param typeCode
	 * @param codeItemName
     * @return
     */
	String getCodeItemValue(String typeCode, String codeItemName);
}
