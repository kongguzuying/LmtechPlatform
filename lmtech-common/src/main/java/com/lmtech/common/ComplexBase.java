package com.lmtech.common;

import com.baomidou.mybatisplus.annotations.TableField;
import com.lmtech.model.DbEntityBase;
import com.lmtech.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 复合类型基类
 * @author huang.jb
 *
 * @param <T>
 */
public abstract class ComplexBase<T> extends DbEntityBase implements IComplex<T> {
	private static final long serialVersionUID = 1L;

	public static final String ROOT_KEY = "0";
	
	@TableField("NAME")
	private String name;
	@TableField("PARENT_ID")
	private String parentId;
	@TableField(exist = false)
	private List<T> childrens = new ArrayList<T>();
	
	/**
	 * 是否根元素
	 * @return
	 */
	public boolean isRoot() {
		if (!StringUtil.isNullOrEmpty(this.getParentId())) {
			return this.getParentId().equals(ROOT_KEY);
		} else {
			return false;
		}
	}
	
	//general getter and setter
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String getParentId() {
		return this.parentId;
	}
	@Override
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}


	@Override
	public void addChildren(T children) {
		if (!containsChildren(children)) {
			this.childrens.add(children);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteChildren(String childrenId) {
		for (T item : childrens) {
			IComplex<T> compItem = (IComplex<T>)item;
			if (compItem.getId().equals(childrenId)) {
				childrens.remove(item);
			}
		}
	}

	@Override
	public boolean hasChildren() {
		return this.childrens.size() > 0;
	}

	@Override
	public void setChildrens(List<T> childrens) {
		this.childrens = childrens;
	}

	@Override
	public List<T> getChildrens() {
		return this.childrens;
	}

	@SuppressWarnings("unchecked")
	private boolean containsChildren(T children) {
		if (childrens != null && childrens.size() > 0) {
			IComplex<T> childComplex = (IComplex<T>)children;
			for (T item : childrens) {
				if (((IComplex<T>)item).getId().equals(childComplex.getId())) {
					return true;
				}
			}
		}
		return false;
	}

}
