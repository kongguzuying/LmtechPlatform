package com.lmtech.auth.service;

import com.lmtech.auth.model.TokenLog;
import com.lmtech.common.PageData;
import com.lmtech.service.DbServiceBase;

/**
 * Token日志服务接口
 * Created by huang.jb on 2017-1-12.
 */
public interface TokenLogService extends DbServiceBase<TokenLog> {
    /**
     * 禁用帐户token
     * @param account
     */
    void disableAccountToken(String account);

    /**
     * 刷新在线Token
     */
    void refreshActiveToken();

    /**
     * 获取在线Token
     * @param param
     * @param pageIndex
     * @param pageSize
     * @return
     */
    PageData getActiveTokens(TokenLog param, int pageIndex, int pageSize);
}
