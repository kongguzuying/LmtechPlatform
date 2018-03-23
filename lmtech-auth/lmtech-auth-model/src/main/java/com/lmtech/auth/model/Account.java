package com.lmtech.auth.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lmtech.model.DbEntityBase;

import java.util.Date;

/**
 * 帐户信息
 * Created by huang.jb on 2017-1-5.
 */
@TableName("lm_account")
public class Account extends DbEntityBase {
    @TableField("NAME")
    private String name;            //帐户昵称
    @TableField("LOGIN_NAME")
    private String loginName;       //登录名
    @TableField("PASSWORD")
    private String password;        //密码
    @TableField("USER_ID")
    private String userId;          //用户编号
    @TableField("IS_LOCK")
    private boolean isLock;         //是否锁定
    @TableField("LAST_LOGIN_DATE")
    private Date lastLoginDate;     //最后一次登录时间
    @TableField("PSWD_ERROR_COUNT")
    private int pswdErrorCount;     //密码错误次数
    @TableField("MOBILE")
    private String mobile;          //手机
    @TableField("EMAIL")
    private String email;           //邮箱
    @TableField("ENABLE")
    private boolean enable;         //是否启用

    /**
     * 构建帐户信息
     * @return
     */
    public AccountInfo buildAccountInfo() {
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountId(getId());
        accountInfo.setAccountName(getName());
        accountInfo.setLoginName(getLoginName());
        accountInfo.setUserId(getUserId());

        return accountInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isLock() {
        return isLock;
    }

    public void setLock(boolean lock) {
        isLock = lock;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public int getPswdErrorCount() {
        return pswdErrorCount;
    }

    public void setPswdErrorCount(int pswdErrorCount) {
        this.pswdErrorCount = pswdErrorCount;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
