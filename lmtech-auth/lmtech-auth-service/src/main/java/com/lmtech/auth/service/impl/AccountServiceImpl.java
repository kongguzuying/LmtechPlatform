package com.lmtech.auth.service.impl;

import com.lmtech.auth.dao.AccountDao;
import com.lmtech.auth.exceptions.AuthenticateException;
import com.lmtech.auth.model.Account;
import com.lmtech.auth.model.AccountInfo;
import com.lmtech.auth.service.AccountService;
import com.lmtech.dao.Dao;
import com.lmtech.service.EncryptionAdapter;
import com.lmtech.service.support.AbstractDbServiceBaseImpl;
import com.lmtech.util.LoggerManager;
import com.lmtech.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by huang.jb on 2017-1-11.
 */
@Service
public class AccountServiceImpl extends AbstractDbServiceBaseImpl<Account> implements AccountService {

    private final String DEFAULT_PASSWORD = "123456";
    private final int MAX_PSWD_ERROR_COUNT = 10;

    private String defaultPassword = DEFAULT_PASSWORD;
    private int maxPswdErrorCount = MAX_PSWD_ERROR_COUNT;

    @Autowired
    private EncryptionAdapter encryptionAdapter;
    @Autowired
    private AccountDao accountDao;

    @Override
    public Dao<Account> getDao() {
        return accountDao;
    }

    @Override
    public void resetPswd(String accountId) {
        String encryptPassword = encryptionAdapter.getMd5Encryption().encryption(defaultPassword);
        accountDao.updatePassword(accountId, encryptPassword);
    }

    @Override
    public void changePswd(String accountId, String newPassword) {
        if (StringUtil.isNullOrEmpty(newPassword)) {
            throw new RuntimeException("重置密码不允许为空。");
        }
        accountDao.updatePassword(accountId, newPassword);
    }

    @Override
    public void authenticate(String loginName, String password) throws AuthenticateException {
        if (StringUtil.isNullOrEmpty(loginName)) throw new AuthenticateException("登录名不允许为空。");
        if (StringUtil.isNullOrEmpty(password)) throw new AuthenticateException("密码不允许为空。");

        Account account = this.getByLoginName(loginName);
        if (account != null) {
            if (account.isLock()) throw new AuthenticateException("用户已锁定。");

            String encryptPassword = encryptionAdapter.getMd5Encryption().encryption(password);
            if (!encryptPassword.equals(account.getPassword())) {
                //添加密码失败错误次数
                int newCount = account.getPswdErrorCount() + 1;
                if (newCount >= maxPswdErrorCount) {
                    //密码错误次数超过系统配置最大值，锁定帐户
                    accountDao.updateLock(account.getId(), true);
                } else {
                    accountDao.updatePswdErrorCount(account.getId(), newCount);
                }
                throw new AuthenticateException("用户名或密码不匹配。");
            } else {
                if (account.getPswdErrorCount() > 0) {
                    //重置密码错误次数
                    accountDao.updatePswdErrorCount(account.getId(), 0);
                }
            }
        } else {
            throw new AuthenticateException("用户不存在。");
        }
    }

    @Override
    public void lockAccount(String accountId) {
        Account account = accountDao.selectById(accountId);
        if (account != null) {
            if (!account.isLock()) {
                accountDao.updateLock(accountId, true);
            } else {
                LoggerManager.warn(String.format("帐户：%1$s已被锁定，不允许重复锁定。", account.getLoginName()));
            }
        }
    }

    @Override
    public void releaseLockAccount(String accountId) {
        Account account = accountDao.selectById(accountId);
        if (account != null) {
            if (account.isLock()) {
                accountDao.updateReleaseLock(accountId);
            } else {
                LoggerManager.warn(String.format("用户：%1$s已被解锁，不允许重复解锁。", account.getLoginName()));
            }
        }
    }

    @Override
    public void refreshLoginTime(String accountId) {
        Account account = accountDao.selectById(accountId);
        if (account != null) {
            accountDao.updateLastLoginTime(accountId, new Date());
        }
    }

    @Override
    public Account getByLoginName(String loginName) {
        return accountDao.selectByLoginName(loginName);
    }

    @Override
    public List<AccountInfo> getAccountInfoList(Account param) {
        return accountDao.selectAccountInfoList(param);
    }

}
