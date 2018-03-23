package com.lmtech.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;

/**
 * config entity base
 * 
 * @author huang.jb
 * 
 */
public abstract class ConfigEntityBase extends EntityBase {
	private static final long serialVersionUID = 1L;
	@TableId
	@TableField("CODE")
	private String code;
	@TableField("NAME")
	private String name;
	
	// property
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
