package com.ea.card.crm.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ea.card.crm.model.CardActiveRecord;
import com.lmtech.dao.LmtechBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CardActiveRecordMapper extends LmtechBaseMapper<CardActiveRecord> {
    /**
     * 查找已激活成功的手机号或微信号个数
     * @param phone
     * @param uniqueId
     * @return
     */
    long countSuccessPhoneOrOpenId(@Param("phone") String phone, @Param("uniqueId") String uniqueId);

    CardActiveRecord selectOneByUniqueId(@Param("uniqueId") String uniqueId);

    CardActiveRecord selectOneByUniqueIdAndStatus(@Param("uniqueId") String uniqueId, @Param("status") int status);

    CardActiveRecord selectOneByOpenIdAndStatus(@Param("openId") String openId, @Param("status") int status);

    CardActiveRecord selectOneByOpenIdsAndStatus(@Param("openIds") List<String> openId, @Param("status") int status);

    void deleteByOpenIdAndStatus(@Param("openId") String openId, @Param("status") int status);

    void deleteByCodeAndStatus(@Param("code") String code, @Param("status") int status);

    void deleteByUniqueIdAndStatus(@Param("uniqueId") String uniqueId, @Param("status") int status);

    void updateStatus(@Param("id") String id, @Param("status") int status);
}
