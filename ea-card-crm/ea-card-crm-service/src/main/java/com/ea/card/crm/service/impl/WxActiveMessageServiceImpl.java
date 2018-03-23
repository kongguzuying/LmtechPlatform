package com.ea.card.crm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.card.crm.dao.WxActiveMessageDao;
import com.ea.card.crm.model.IntegralSignLog;
import com.ea.card.crm.model.WxActiveMessage;
import com.ea.card.crm.service.WxActiveMessageService;
import com.lmtech.dao.Dao;
import com.lmtech.service.support.AbstractDbManagerBaseImpl;

@Service
public class WxActiveMessageServiceImpl extends AbstractDbManagerBaseImpl<WxActiveMessage> implements WxActiveMessageService{

	@Autowired
	private WxActiveMessageDao wxActiveMessageDao;
	@Override
	public void saveWxActiveMessage(String code,String openId) {
		WxActiveMessage wxActiveMessage = new WxActiveMessage();
    	wxActiveMessage.setCode(code);
    	wxActiveMessage.setFromUserName(openId);
    	addObjectEntity(wxActiveMessage);
	}

	@Override
	public WxActiveMessage getByCode(String code) {
		return wxActiveMessageDao.selectByCode(code);
	}
	
	@Override
	public Dao getDao() {
		return wxActiveMessageDao;
	}

}
