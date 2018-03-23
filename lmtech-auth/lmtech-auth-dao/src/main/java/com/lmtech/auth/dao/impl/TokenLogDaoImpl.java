package com.lmtech.auth.dao.impl;

import com.lmtech.auth.dao.TokenLogDao;
import com.lmtech.auth.mapper.TokenLogMapper;
import com.lmtech.auth.model.TokenLog;
import com.lmtech.dao.MyBatisDaoBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huang.jb on 2017-2-28.
 */
@Service
public class TokenLogDaoImpl extends MyBatisDaoBase<TokenLogMapper, TokenLog> implements TokenLogDao {

    @Autowired
    private TokenLogMapper tokenLogMapper;

    @Override
    public List<String> selectAccountActiveTokens(String account) {
        return tokenLogMapper.selectAccountActiveTokens(account);
    }

    @Override
    public List<TokenLog> selectActiveTokens(TokenLog param) {
        return tokenLogMapper.selectActiveTokens(param);
    }

    @Override
    public void batchUpdateStatus(List<String> tokens, String status) {
        tokenLogMapper.batchUpdateStatus(tokens, status);
    }
}
