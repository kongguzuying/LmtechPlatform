package com.lmtech.auth.service.impl;

import com.lmtech.auth.dao.AccountDao;
import com.lmtech.auth.model.Account;
import com.lmtech.auth.service.AccountManager;
import com.lmtech.dao.Dao;
import com.lmtech.service.support.AbstractDbManagerBaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huang.jb on 2017-1-11.
 */
@Service
public class AccountManagerImpl extends AbstractDbManagerBaseImpl<Account> implements AccountManager {

    @Autowired
    private AccountDao accountDao;

    @Override
    public Dao<Account> getDao() {
        return accountDao;
    }
}
