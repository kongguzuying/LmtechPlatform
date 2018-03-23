package com.lmtech.infrastructure.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lmtech.common.ComplexBase;

/**
 * 菜单
 * @author huang.jb
 *
 */
@TableName("lm_menu")
public class Menu extends ComplexBase<Menu> {
	private static final long serialVersionUID = 1L;
	
	public static final String TARGET_FRAME = "F";
	public static final String TARGET_BLANK = "B";
	public static final String TARGET_SELF = "S";
	public static final String TARGET_EDITDIALOG = "ED";
	public static final String TARGET_DIALOG = "D";
	
	@TableField("HREF")
	private String href;
	@TableField("TARGET")
	private String target = TARGET_FRAME;
	@TableField("VISIBLE")
	private Boolean visible;
	@TableField("MENU_TYPE")
	private String menuType;
	@TableField("ICON")
	private String icon;
	@TableField("SORT_NO")
	private int sortNo;
	@TableField("APP_CODE")
	private String appCode;
	@TableField(exist = false)
	private String navigator;
	
	public Menu() {}
	
	public Menu(String id, String name,String href, String target, Boolean visible, String menuType, String parentId, int sortNo, String appCode) {
		super.setId(id);
		super.setParentId(parentId);
		super.setName(name);
		this.href = href;
		this.target = target;
		this.visible = visible;
		this.menuType = menuType;
		this.appCode = appCode;
	}

	//general getter and setter
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public Boolean getVisible() {
		return visible;
	}
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	public String getMenuType() {
		return menuType;
	}
	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}
	public int getSortNo() {
		return sortNo;
	}
	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	public String getNavigator() {
		return navigator;
	}
	public void setNavigator(String navigator) {
		this.navigator = navigator;
	}
}
