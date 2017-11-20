package com.lmtech.infrastructure.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lmtech.common.ComplexBase;

/**
 * 群组
 * @author huang.jb
 *
 */
@TableName("lm_group")
public class Group extends ComplexBase<Group> {

	private static final long serialVersionUID = 1L;
	
	@TableField("ALIAS")
	private String alias;
	@TableField("REMARK")
	private String remark;
	@TableField("ENABLE")
	private Boolean enable;

	@TableField(exist = false)
	private Boolean hasChildren = true;
	
	// property
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Boolean getEnable() {
		return enable;
	}
	public void setEnable(Boolean enable) {
		this.enable = enable;
	}
	public Boolean getHasChildren() {
		return hasChildren;
	}
	public void setHasChildren(Boolean hasChildren) {
		this.hasChildren = hasChildren;
	}
}
