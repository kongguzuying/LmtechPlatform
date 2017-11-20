package com.lmtech.infrastructure.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lmtech.model.DbEntityBase;

import java.util.Date;

/**
 * 反馈信息表
 * Created by huang.jb on 2016-8-17.
 */
@TableName("LM_APP_FEEDBACK")
public class AppFeedback extends DbEntityBase {
	
	private static final long serialVersionUID = 1L;

	@TableField("USER_ID")
	private String userId;  //用户ID
    @TableField("CONTENT")
	private String content; //反馈内容
    @TableField("DATE")
	private Date date; //反馈时间
    @TableField("APP_TYPE")
	private String appType; //Android,IOS
    @TableField(exist = false)
    private String userName;
	@TableField(exist = false)
    private String departmentName;
    
    
    
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

}

