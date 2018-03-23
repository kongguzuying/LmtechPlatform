package com.lmtech.auth.mapper;

import com.lmtech.auth.model.Account;
import com.lmtech.auth.model.AccountInfo;
import com.lmtech.dao.LmtechBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 帐户Mapper
 * Created by huang.jb on 2017-1-10.
 */
public interface AccountMapper extends LmtechBaseMapper<Account> {
    /**
     * 更新最后一次登录时间
     * @param accountId
     * @param loginDate
     */
    void updateLastLoginTime(@Param("accountId") String accountId, @Param("loginDate") Date loginDate);

    /**
     * 更新密码错误次数
     * @param accountId
     * @param pswdErrorCount
     */
    void updatePswdErrorCount(@Param("accountId") String accountId, @Param("pswdErrorCount") int pswdErrorCount);

    /**
     * 更新密码
     * @param accountId
     * @param password
     */
    void updatePassword(@Param("accountId") String accountId, @Param("password") String password);

    /**
     * 更新密码锁定状态
     * @param accountId
     * @param isLock
     */
    void updateLock(@Param("accountId") String accountId, @Param("isLock") boolean isLock);

    /**
     * 更新帐户解锁状态
     * @param accountId
     */
    void updateReleaseLock(@Param("accountId") String accountId);

    /**
     * 通过登录名获取帐户
     * @param loginName
     * @return
     */
    Account selectByLoginName(@Param("loginName") String loginName);

    /**
     * 获取帐户信息列表
     * @param param
     * @return
     */
    List<AccountInfo> selectAccountInfoList(Account param);
}
