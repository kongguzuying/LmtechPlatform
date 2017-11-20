package com.lmtech.infrastructure.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lmtech.model.DbEntityBase;

/**
 * 角色
 * @author huang.jb
 *
 */
@TableName("lm_role")
public class Role extends DbEntityBase {
	private static final long serialVersionUID = 1L;
	@TableField("NAME")
	private String name;
	@TableField("REMARK")
	private String remark;
	@TableField("LEVEL_")
	private int level;
	@TableField("ENABLE")
	private boolean enable;
	
	// property
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
}
