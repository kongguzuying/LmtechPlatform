package com.lmtech.auth.dao;

import com.lmtech.auth.model.Account;
import com.lmtech.auth.model.AccountInfo;
import com.lmtech.dao.Dao;

import java.util.Date;
import java.util.List;

/**
 * 帐户Dao
 * Created by huang.jb on 2017-2-28.
 */
public interface AccountDao extends Dao<Account> {
    /**
     * 更新最后一次登录时间
     * @param accountId
     * @param loginDate
     */
    void updateLastLoginTime(String accountId, Date loginDate);

    /**
     * 更新密码错误次数
     * @param accountId
     * @param pswdErrorCount
     */
    void updatePswdErrorCount(String accountId, int pswdErrorCount);

    /**
     * 更新密码
     * @param accountId
     * @param password
     */
    void updatePassword(String accountId, String password);

    /**
     * 更新帐户锁定状态
     * @param accountId
     * @param isLock
     */
    void updateLock(String accountId, boolean isLock);

    /**
     * 更新帐户解锁状态
     * @param accountId
     */
    void updateReleaseLock(String accountId);

    /**
     * 通过登录名获取帐户
     * @param loginName
     * @return
     */
    Account selectByLoginName(String loginName);

    /**
     * 获取帐户信息列表
     * @param param
     * @return
     */
    List<AccountInfo> selectAccountInfoList(Account param);
}
