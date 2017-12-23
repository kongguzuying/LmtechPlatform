package com.lmtech.infrastructure.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lmtech.model.DbEntityBase;

import java.util.Date;

/**
 * 系统用户信息
 * @author huang.jb
 *
 */
@TableName("lm_user")
public class User extends DbEntityBase {
	/** 活动状态 */
	public static final int STATUS_ACTIVE = 1;
	/** 禁用状态 */
	public static final int STATUS_DISABLE = 2;

	/** 姓别-男 */
	public static final int SEX_MAN = 1;
	/** 性别-女 */
	public static final int SEX_WOMEN = 2;
	/** 性别-保密 */
	public static final int SEX_OTHER = 3;
	
	@TableField("nick_name")
	private String nickName;	//昵称
	@TableField("real_name")
	private String realName;	//真实姓名
	@TableField("qq")
	private String qq;			//QQ
	@TableField("email")
	private String email;		//邮箱
	@TableField("birthday")
	private Date birthday;		//生日
	@TableField("sex")
	private int sex;			//性别
	@TableField("mobile")
	private String mobile;		//手机号
	@TableField("header_img")
	private String headerImg;	//头像
	@TableField("card_id")
	private String cardId;		//生份证ID
	@TableField("tenancy_id")
	private String tenancyId;	//租户ID
	@TableField("group_id")
	private String groupId;		//群组ID
	@TableField("status")
	private int status;			//状态 1:活动,2:禁用

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
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

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getHeaderImg() {
		return headerImg;
	}

	public void setHeaderImg(String headerImg) {
		this.headerImg = headerImg;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTenancyId() {
		return tenancyId;
	}

	public void setTenancyId(String tenancyId) {
		this.tenancyId = tenancyId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
}
