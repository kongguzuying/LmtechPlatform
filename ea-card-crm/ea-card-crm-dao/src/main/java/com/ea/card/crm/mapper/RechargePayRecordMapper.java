package com.ea.card.crm.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ea.card.crm.model.RechargePayRecord;
import com.lmtech.dao.LmtechBaseMapper;
import org.apache.ibatis.annotations.Param;

public interface RechargePayRecordMapper extends LmtechBaseMapper<RechargePayRecord> {
    RechargePayRecord selectByOrderNo(@Param("orderNo") String orderNo);

    void updateStatusByOrderNo(@Param("orderNo") String orderNo, @Param("status") int status);
}
