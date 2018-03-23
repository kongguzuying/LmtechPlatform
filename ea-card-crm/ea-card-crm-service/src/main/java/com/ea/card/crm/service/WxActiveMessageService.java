package com.ea.card.crm.service;

import com.ea.card.crm.model.WxActiveMessage;

public interface WxActiveMessageService {
	
	public void saveWxActiveMessage(String code,String openId);
	
	WxActiveMessage getByCode(String code);
	
}
