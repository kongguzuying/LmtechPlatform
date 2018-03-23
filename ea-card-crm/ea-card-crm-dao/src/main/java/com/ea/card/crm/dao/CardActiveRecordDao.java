package com.ea.card.crm.dao;

import com.ea.card.crm.model.CardActiveRecord;
import com.lmtech.dao.Dao;

import java.util.List;

public interface CardActiveRecordDao extends Dao<CardActiveRecord> {
    /**
     * 是否存在手机号或微信号
     * @param phone
     * @param openId
     * @return
     */
    boolean existSuccessPhoneOrOpenId(String phone, String openId);

    /**
     * 通过uniqueid和status获取记录
     * @param uniqueId
     * @return
     */
    CardActiveRecord getByUniqueId(String uniqueId);

    /**
     * 通过uniqueid和status获取记录
     * @param uniqueId
     * @param status
     * @return
     */
    CardActiveRecord getByUniqueIdAndStatus(String uniqueId, int status);

    /**
     * 通过openid和status获取记录
     * @param openId
     * @param status
     * @return
     */
    CardActiveRecord getByOpenIdAndStatus(String openId, int status);

    /**
     * 通过openid和status获取记录
     * @param openIds
     * @param status
     * @return
     */
    CardActiveRecord getByOpenIdsAndStatus(List<String> openIds, int status);

    /**
     * 通过openid和status删除记录
     * @param openId
     * @param status
     * @return
     */
    void deleteByOpenIdAndStatus(String openId, int status);

    /**
     * 通过code和status删除记录
     * @param code
     * @param status
     * @return
     */
    void deleteByCodeAndStatus(String code, int status);

    /**
     * 通过uniqueId和status删除记录
     * @param uniqueId
     * @param status
     * @return
     */
    void deleteByUniqueIdAndStatus(String uniqueId, int status);

    /**
     * 更新状态
     * @param id
     * @param status
     */
    void updateStatus(String id, int status);
}
