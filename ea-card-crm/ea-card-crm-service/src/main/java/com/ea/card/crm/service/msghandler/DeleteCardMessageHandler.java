package com.ea.card.crm.service.msghandler;

import com.ea.card.crm.model.MemberRegister;
import com.ea.card.crm.service.CardActiveRecordService;
import com.ea.card.crm.service.MemberRegisterService;
import com.ea.card.crm.service.vo.WxDeleteCardMessage;
import com.ea.card.crm.service.vo.WxMessageBase;
import com.lmtech.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户销卡消息处理
 */
@Service
public class DeleteCardMessageHandler implements MessageHandler{

    @Autowired
    private CardActiveRecordService cardActiveRecordService;

    @Autowired
    private MemberRegisterService memberRegisterService;

    @Override
    public void handle(WxMessageBase message) {
        WxDeleteCardMessage deleteCardMessage = (WxDeleteCardMessage) message;

        //查询用户注册表
        MemberRegister memberRegister = memberRegisterService.getByCodeAndIsDelete(deleteCardMessage.getUserCardCode(), MemberRegister.DELETE_NO);

        if (memberRegister != null) {
            LoggerManager.info("1.删除卡激活记录 => 开始");
            cardActiveRecordService.deleteActiveRecordByUniqueId(memberRegister.getUniqueId());
            LoggerManager.info("1.删除卡激活记录 => 结束");

            //逻辑删除用户注册记录
            LoggerManager.info("1.删除用户注册记录 => 开始");
            memberRegisterService.updateIsDelete(memberRegister.getId(), MemberRegister.DELETE_YES);
            LoggerManager.info("1.删除用户注册记录 => 结束");
        } else {
            LoggerManager.error("通过code删除激活记录失败，找不到code为" + deleteCardMessage.getUserCardCode() + "的有历史注册记录");
        }
    }
}
