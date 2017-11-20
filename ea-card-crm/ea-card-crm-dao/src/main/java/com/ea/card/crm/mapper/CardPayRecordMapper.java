package com.ea.card.crm.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ea.card.crm.model.CardPayRecord;
import com.lmtech.dao.LmtechBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CardPayRecordMapper extends LmtechBaseMapper<CardPayRecord> {
    CardPayRecord selectByOrderNo(@Param("orderNo") String orderNo);

    void updateStatusByOrderNo(CardPayRecord param);
}
