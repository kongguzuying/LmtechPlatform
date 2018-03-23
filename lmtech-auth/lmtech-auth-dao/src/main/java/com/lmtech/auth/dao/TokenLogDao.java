package com.lmtech.auth.dao;

import com.lmtech.auth.model.TokenLog;
import com.lmtech.dao.Dao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Token日志Dao
 * Created by huang.jb on 2017-2-28.
 */
public interface TokenLogDao extends Dao<TokenLog> {
    /**
     * 获取帐户活动状态的Token
     * @param account
     * @return
     */
    List<String> selectAccountActiveTokens(String account);

    /**
     * 获取活动状态的Token
     * @return
     */
    List<TokenLog> selectActiveTokens(TokenLog param);

    /**
     * 批量更新token状态
     * @param tokens
     * @param status
     */
    void batchUpdateStatus(List<String> tokens, String status);
}
