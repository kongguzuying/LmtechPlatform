package com.ea.card.crm.service;

import com.ea.card.crm.model.CardActiveRecord;
import com.lmtech.service.DbManagerBase;

/**
 * 会员卡激活记录服务
 * @author
 */
public interface CardDeleteService extends DbManagerBase<CardActiveRecord> {
    /**
     * 校验手机号和微信号
     * @return
     */
    boolean checkPhoneAndOpenid(String phone, String uniqueId);

    /**
     * 获取已申领卡记录，通过uniqueid
     * @param uniqueId
     * @return
     */
    CardActiveRecord getAppliedRecordByUniqueId(String uniqueId);

    /**
     * 获取已申领卡记录，通过公众号openid
     * @param openId
     * @return
     */
    CardActiveRecord getAppliedRecordByOpenId(String openId);
}
