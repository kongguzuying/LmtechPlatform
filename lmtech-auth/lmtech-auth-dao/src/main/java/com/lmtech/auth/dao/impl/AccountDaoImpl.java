package com.lmtech.auth.dao.impl;

import com.lmtech.auth.dao.AccountDao;
import com.lmtech.auth.mapper.AccountMapper;
import com.lmtech.auth.model.Account;
import com.lmtech.auth.model.AccountInfo;
import com.lmtech.dao.MyBatisDaoBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by huang.jb on 2017-2-28.
 */
@Service
public class AccountDaoImpl extends MyBatisDaoBase<AccountMapper, Account> implements AccountDao {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public void updateLastLoginTime(String accountId, Date loginDate) {
        accountMapper.updateLastLoginTime(accountId, loginDate);
    }

    @Override
    public void updatePswdErrorCount(String accountId, int pswdErrorCount) {
        accountMapper.updatePswdErrorCount(accountId, pswdErrorCount);
    }

    @Override
    public void updatePassword(String accountId, String password) {
        accountMapper.updatePassword(accountId, password);
    }

    @Override
    public void updateLock(String accountId, boolean isLock) {
        accountMapper.updateLock(accountId, isLock);
    }

    @Override
    public void updateReleaseLock(String accountId) {
        accountMapper.updateReleaseLock(accountId);
    }

    @Override
    public Account selectByLoginName(String loginName) {
        return accountMapper.selectByLoginName(loginName);
    }

    @Override
    public List<AccountInfo> selectAccountInfoList(Account param) {
        return accountMapper.selectAccountInfoList(param);
    }
}
