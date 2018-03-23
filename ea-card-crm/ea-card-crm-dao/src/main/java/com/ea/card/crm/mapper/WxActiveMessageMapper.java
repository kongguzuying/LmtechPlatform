package com.ea.card.crm.mapper;

import com.ea.card.crm.model.WxActiveMessage;
import com.lmtech.dao.LmtechBaseMapper;
import org.apache.ibatis.annotations.Param;

public interface WxActiveMessageMapper extends LmtechBaseMapper<WxActiveMessage> {
    WxActiveMessage selectOutstrOfUser(@Param("fromUserName") String fromUserName);

    String selectOpenIdByFromUserName(@Param("fromUserName") String fromUserName);
    
    WxActiveMessage selectByCode(@Param("code") String fromUserName);
}
