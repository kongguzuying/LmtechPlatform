package com.lmtech.auth.service.impl;

import com.lmtech.auth.constants.AuthErrorConstants;
import com.lmtech.auth.constants.AuthConstants;
import com.lmtech.auth.model.Token;
import com.lmtech.auth.model.TokenLog;
import com.lmtech.auth.model.TokenValidateResult;
import com.lmtech.auth.service.TokenLogManager;
import com.lmtech.auth.service.TokenLogService;
import com.lmtech.auth.service.TokenService;
import com.lmtech.constants.LmPltConstants;
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

    private long expireSeconds = AuthConstants.EXPIRE_SECONDS;

    @Override
    public Token genToken(String... keys) {
        String id = IdWorkerUtil.generateStringId();
        List<String> keysList = (List<String>) CollectionUtil.convertArrayToList(keys);
        keysList.add(id);
        String[] newKeys = new String[keysList.size()];
        keysList.toArray(newKeys);
        String key = StringUtil.encryptStrings(newKeys);
        boolean result = redisDataService.setKey(AuthConstants.REDIS_PREFIX + key, key, "NX", expireSeconds);
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
        return keys;
    }

    @Override
    public void removeAccountToken(String account) {
        tokenLogService.disableAccountToken(account);
    }

    @Override
    public TokenValidateResult validateToken(Token token) {
        String value = redisDataService.getKey(AuthConstants.REDIS_PREFIX + token.getTokenCode());
        TokenValidateResult result = new TokenValidateResult();
        if (StringUtil.isNullOrEmpty(value)) {
            result.setCode(AuthErrorConstants.ERR_TOKEN_VALIDATE);
            result.setValidSuccess(false);
            result.setMessage(AuthErrorConstants.ERR_TOKEN_VALIDATE_MSG);
        } else {
            //刷新过期时间
            redisDataService.setExpireTime(AuthConstants.REDIS_PREFIX + token.getTokenCode(), AuthConstants.EXPIRE_SECONDS);
            result.setCode(LmPltConstants.SUCCESS);
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
