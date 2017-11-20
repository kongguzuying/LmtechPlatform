package com.ea.card.crm.service.msghandler;

import com.ea.card.crm.service.vo.WxMessageBase;

public interface MessageHandler {
    /**
     * 处理消息
     * @param message
     */
    void handle(WxMessageBase message);
}
