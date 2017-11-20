package com.ea.card.crm.service;

import com.ea.card.crm.model.CardActiveRecord;
import com.lmtech.service.DbManagerBase;

import java.util.List;

/**
 * 会员卡激活记录服务
 * @author
 */
public interface CardActiveRecordService extends DbManagerBase<CardActiveRecord> {
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
     * 获取正在申领卡记录，通过uniqueid
     * @param uniqueId
     * @return
     */
    CardActiveRecord getApplyingRecordByUniqueId(String uniqueId);

    /**
     * 获取已申领卡记录，通过公众号openid
     * @param openId
     * @return
     */
    CardActiveRecord getAppliedRecordByOpenId(String openId);

    /**
     * 获取已申领卡记录，通过公众号openid
     * @param openIds
     * @return
     */
    CardActiveRecord getAppliedRecordByOpenIds(List<String> openIds);

    /**
     * 删除已激活卡记录
     * @param openId
     * @return
     */
    void deleteActiveRecordByOpenId(String openId);

    /**
     * 删除已激活卡记录
     * @param uniqueId
     * @return
     */
    void deleteActiveRecordByUniqueId(String uniqueId);

    /**
     * 更新状态
     * @param id
     * @param status
     */
    void updateStatus(String id, int status);
}
