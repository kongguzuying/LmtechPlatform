package com.lmtech.infrastructure.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lmtech.model.ConfigEntityBase;

/**
 * 系统参数
 * @author huang.jb
 *
 */
@TableName("lm_system_config")
public class SystemConfig extends ConfigEntityBase {
	private static final long serialVersionUID = 1L;
	@TableField("VALUE")
	private String value;
	@TableField("CATEGORY")
	private String category;
	@TableField("REMARK")
	private String remark;
	
	public SystemConfig() {}
	
	public SystemConfig(String name, String value, String category, String remark) {
		super.setName(name);
		this.value = value;
		this.category = category;
		this.remark = remark;
	}
	
	//general getter and setter
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
