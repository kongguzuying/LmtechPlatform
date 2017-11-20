package com.lmtech.auth.service.impl;

import com.lmtech.auth.constants.TokenConstValue;
import com.lmtech.auth.dao.TokenLogDao;
import com.lmtech.auth.model.TokenLog;
import com.lmtech.auth.service.TokenLogService;
import com.lmtech.common.ExeResult;
import com.lmtech.common.PageData;
import com.lmtech.dao.Dao;
import com.lmtech.redis.service.RedisDataService;
import com.lmtech.service.support.AbstractDbServiceBaseImpl;
import com.lmtech.util.CollectionBatchHandler;
import com.lmtech.util.CollectionUtil;
import com.lmtech.util.LoggerManager;
import com.lmtech.util.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huang.jb on 2017-1-12.
 */
@Service
public class TokenLogServiceImpl extends AbstractDbServiceBaseImpl<TokenLog> implements TokenLogService {

    @Autowired
    private TokenLogDao tokenLogDao;
    @Autowired
    private RedisDataService redisDataService;

    @Override
    public Dao getDao() {
        return tokenLogDao;
    }

    @Override
    public void disableAccountToken(String account) {
        List<String> activeTokens = tokenLogDao.selectAccountActiveTokens(account);
        if (!CollectionUtil.isNullOrEmpty(activeTokens)) {
            List<String> keys = new ArrayList<String>();
            for (String activeToken : activeTokens) {
                keys.add(TokenConstValue.REDIS_PREFIX + activeToken);
            }
            //清除redis缓存token
            try {
                redisDataService.removeKeys(keys);
            } catch (Exception e) {
                LoggerManager.error(e);
            }
            //设置数据库token状态为无效
            tokenLogDao.batchUpdateStatus(activeTokens, TokenLog.STATUS_INVALID);
        }
    }

    @Override
    public void refreshActiveToken() {
        List<TokenLog> tokenLogs = tokenLogDao.selectActiveTokens(null);
        if (!CollectionUtil.isNullOrEmpty(tokenLogs)) {
            CollectionBatchHandler<TokenLog> handler = new CollectionBatchHandler<TokenLog>() {
                @Override
                public ExeResult handle(List<TokenLog> list) {
                    //获取Redis中活动状态的token
                    List<String> keys = new ArrayList<String>();
                    List<String> allTokens = new ArrayList<String>();
                    for (TokenLog item : list) {
                        keys.add(TokenConstValue.REDIS_PREFIX + item.getToken());
                    }
                    List<String> activeTokens = redisDataService.getKeys(keys);

                    //刷新本地库中token状态
                    List<String> invalidTokens = (List<String>) CollectionUtil.getDiffOfList(allTokens, activeTokens);
                    //更新为非活动状态
                    tokenLogDao.batchUpdateStatus(invalidTokens, TokenLog.STATUS_INVALID);
                    return new ExeResult(true);
                }
            };
            CollectionUtil.batchHandle(tokenLogs, handler, 50);
        }
    }

    @Override
    public PageData getActiveTokens(TokenLog param, int pageIndex, int pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        tokenLogDao.selectActiveTokens(param);
        return PageHelper.endPage();
    }
}
