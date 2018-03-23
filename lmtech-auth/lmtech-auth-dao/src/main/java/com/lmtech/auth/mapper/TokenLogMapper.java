package com.lmtech.auth.mapper;

import com.lmtech.auth.model.TokenLog;
import com.lmtech.dao.LmtechBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Token日志记录Mapper
 * Created by huang.jb on 2017-1-12.
 */
public interface TokenLogMapper extends LmtechBaseMapper<TokenLog> {
    /**
     * 获取帐户活动状态的Token
     * @param account
     * @return
     */
    List<String> selectAccountActiveTokens(@Param("account") String account);

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
    void batchUpdateStatus(@Param("tokens") List<String> tokens, @Param("status") String status);
}
