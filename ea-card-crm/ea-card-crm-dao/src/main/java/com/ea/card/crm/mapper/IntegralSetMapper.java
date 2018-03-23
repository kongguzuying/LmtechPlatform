package com.ea.card.crm.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ea.card.crm.model.BonusType;
import com.ea.card.crm.model.IntegralSet;
import com.lmtech.dao.LmtechBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IntegralSetMapper extends LmtechBaseMapper<IntegralSet> {
    Integer getIntegralSet(@Param("dayNo") int dayNo,@Param("integralSource") int integralSource);
    List<IntegralSet> getIntegralSetAll(@Param("integralSource") int integralSource);
    List<BonusType> groupByType();
}
