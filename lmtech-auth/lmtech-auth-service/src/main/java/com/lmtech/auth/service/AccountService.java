package com.lmtech.auth.service;

import com.lmtech.auth.exceptions.AuthenticateException;
import com.lmtech.auth.model.Account;
import com.lmtech.auth.model.AccountInfo;
import com.lmtech.auth.model.TokenLog;

import java.util.List;

/**
 * 帐户服务
 * Created by huang.jb on 2017-1-9.
 */
public interface AccountService {
    /**
     * 重置用户密码
     * @param accountId
     */
    void resetPswd(String accountId);
    /**
     * 设置用户密码
     * @param accountId
     * @param newPassword
     */
    void changePswd(String accountId, String newPassword);
    /**
     * 用户验证
     * @param loginName
     * @param password
     */
    void authenticate(String loginName, String password) throws AuthenticateException;
    /**
     * 锁定用户
     * @param accountId
     */
    void lockAccount(String accountId);
    /**
     * 解锁用户
     * @param accountId
     */
    void releaseLockAccount(String accountId);
    /**
     * 刷新用户登录时间
     * @param accountId
     */
    void refreshLoginTime(String accountId);
    /**
     * 通过登录名获取用户
     * @param loginName
     * @return
     */
    Account getByLoginName(String loginName);
    /**
     * 获取帐户信息列表
     * @param param
     * @return
     */
    List<AccountInfo> getAccountInfoList(Account param);
}
