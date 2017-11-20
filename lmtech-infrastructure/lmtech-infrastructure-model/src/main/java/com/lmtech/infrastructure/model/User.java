package com.lmtech.infrastructure.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lmtech.model.DbEntityBase;

/**
 * 系统用户信息
 * @author huang.jb
 *
 */
@TableName("lm_user")
public class User extends DbEntityBase {
	private static final long serialVersionUID = 1L;
	
	@TableField("NAME")
	private String name;
	@TableField("QQ")
	private String qq;
	@TableField("EMAIL")
	private String email;
	@TableField("MOBILE")
	private String mobile;
	@TableField("ENABLE")
	private boolean enable;
	
	// property
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
}
