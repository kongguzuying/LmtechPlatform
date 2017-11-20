package com.lmtech.auth.service.impl;

import com.lmtech.auth.constants.TokenConstValue;
import com.lmtech.auth.constants.TokenValidateResultCode;
import com.lmtech.auth.model.Token;
import com.lmtech.auth.model.TokenLog;
import com.lmtech.auth.model.TokenValidateResult;
import com.lmtech.auth.service.TokenLogManager;
import com.lmtech.auth.service.TokenLogService;
import com.lmtech.auth.service.TokenService;
import com.lmtech.redis.service.RedisDataService;
import com.lmtech.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by huang.jb on 2017-1-6.
 */
@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private TokenLogManager tokenLogManager;
    @Autowired
    private TokenLogService tokenLogService;
    @Autowired
    private RedisDataService redisDataService;

    private long expireSeconds = TokenConstValue.EXPIRE_SECONDS;

    @Override
    public Token genToken(String... keys) {
        String id = IdWorkerUtil.generateStringId();
        List<String> keysList = (List<String>) CollectionUtil.convertArrayToList(keys);
        keysList.add(id);
        String[] newKeys = new String[keysList.size()];
        keysList.toArray(newKeys);
        String key = StringUtil.encryptStrings(newKeys);
        boolean result = redisDataService.setKey(TokenConstValue.REDIS_PREFIX + key, key, "NX", expireSeconds);
        if (result) {
            //添加Token日志
            try {
                TokenLog tokenLog = new TokenLog();
                tokenLog.setId(IdWorkerUtil.generateStringId());
                tokenLog.setAccount(keys[0]);
                tokenLog.setToken(key);
                tokenLog.setStatus(TokenLog.STATUS_ACTIVE);
                tokenLogManager.add(tokenLog);
            } catch (Exception e) {
                LoggerManager.error(e);
            }

            //构建生成的token
            Token token = new Token();
            token.setTokenCode(key);
            token.setExpireTime(DateUtil.addSecond(new Date(), (int) expireSeconds));
            return token;
        } else {
            LoggerManager.error("gen token error");
            return null;
        }
    }

    @Override
    public List<String> parseToken(String tokenCode) {
        List<String> keys = StringUtil.decryptStrings(tokenCode);
        keys.remove(keys.size() - 1);
        return keys;
    }

    @Override
    public void removeAccountToken(String account) {
        tokenLogService.disableAccountToken(account);
    }

    @Override
    public TokenValidateResult validateToken(Token token) {
        String value = redisDataService.getKey(TokenConstValue.REDIS_PREFIX + token.getTokenCode());
        TokenValidateResult result = new TokenValidateResult();
        if (StringUtil.isNullOrEmpty(value)) {
            result.setCode(TokenValidateResultCode.ERROR);
            result.setValidSuccess(false);
            result.setMessage("Token无效或者已经过期，请重新申请。");
        } else {
            //刷新过期时间
            redisDataService.setExpireTime(TokenConstValue.REDIS_PREFIX + token.getTokenCode(), TokenConstValue.EXPIRE_SECONDS);
            result.setCode(TokenValidateResultCode.SUCCESS);
            result.setValidSuccess(true);
            result.setMessage("Token验证成功");
        }
        return result;
    }

    public long getExpireSeconds() {
        return expireSeconds;
    }

    public void setExpireSeconds(long expireSeconds) {
        this.expireSeconds = expireSeconds;
    }
}
