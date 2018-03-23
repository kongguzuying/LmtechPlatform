package com.ea.card.crm.dao;

import com.ea.card.crm.model.WxActiveMessage;
import com.lmtech.dao.Dao;

public interface WxActiveMessageDao extends Dao<WxActiveMessage> {
    WxActiveMessage getByFromUserName(String fromUserName);
    
    WxActiveMessage selectByCode(String code);
}
