package com.lmtech.auth.service.impl;

import com.lmtech.auth.dao.TokenLogDao;
import com.lmtech.auth.model.TokenLog;
import com.lmtech.auth.service.TokenLogManager;
import com.lmtech.dao.Dao;
import com.lmtech.service.support.AbstractDbManagerBaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huang.jb on 2017-1-12.
 */
@Service
public class TokenLogManagerImpl extends AbstractDbManagerBaseImpl<TokenLog> implements TokenLogManager {

    @Autowired
    private TokenLogDao tokenLogDao;

    @Override
    public Dao<TokenLog> getDao() {
        return tokenLogDao;
    }
}
