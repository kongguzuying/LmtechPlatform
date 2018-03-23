package com.ea.card.crm.dao.impl;

import com.ea.card.crm.dao.WxActiveMessageDao;
import com.ea.card.crm.mapper.WxActiveMessageMapper;
import com.ea.card.crm.model.WxActiveMessage;
import com.lmtech.dao.MyBatisDaoBase;
import org.springframework.stereotype.Service;

@Service
public class WxActiveMessageDaoImpl extends MyBatisDaoBase<WxActiveMessageMapper, WxActiveMessage> implements WxActiveMessageDao {
    public WxActiveMessage getByFromUserName(String fromUserName) {
        return baseMapper.selectOutstrOfUser(fromUserName);
    }

	@Override
	public WxActiveMessage selectByCode(String code) {
		return baseMapper.selectByCode(code);
	}
}
