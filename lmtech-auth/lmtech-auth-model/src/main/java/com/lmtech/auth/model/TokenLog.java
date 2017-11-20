package com.lmtech.auth.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lmtech.model.DbEntityBase;

/**
 * Token日志记录
 * Created by huang.jb on 2017-1-12.
 */
@TableName("lm_token_log")
public class TokenLog extends DbEntityBase {
    /** 活动状态 */
    public static final String STATUS_ACTIVE = "A";
    /** 无效状态 */
    public static final String STATUS_INVALID = "I";

    @TableField("TOKEN")
    private String token;       //token值
    @TableField("ACCOUNT")
    private String account;     //申请的帐户
    @TableField("STATUS")
    private String status;      //状态

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
