package com.lmtech.common;

import java.util.List;
 
/**
 * 复合类接口
 * @author huang.jb
 *
 */
public interface IComplex<T> {
	/**
	 * 获取id
	 * @return
	 */
	String getId();
	/**
	 * 设置id设
	 * @param id
	 */
	void setId(String id);
	/**
	 * 获取父id
	 * @return
	 */
	String getParentId();
	/**
	 * 置父id
	 * @param parentId
	 */
	void setParentId(String parentId);
	/**
	 * 获取子项
	 * @return
	 */
	List<T> getChildrens();
	/**
	 * 设置所有子项
	 * @param childrens
	 */
	void setChildrens(List<T> childrens);
	/**
	 *  添加子项
	 * @param children
	 */
	void addChildren(T children);
	/**
	 * 删除子项
	 * @param childrenId
	 */
	void deleteChildren(String childrenId);
	/**
	 * 是否拥有子项
	 * @return
	 */
	boolean hasChildren();
}
