package com.ea.card.crm.mapper;

import java.util.List;

import com.lmtech.dao.LmtechBaseMapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ea.card.crm.model.IntegralTradingRecord;

/**
 * 积分兑换商品交易记录 Mapper
 * @author huacheng.li
 *
 */
public interface IntegralTradingRecordMapper extends LmtechBaseMapper<IntegralTradingRecord> {
	
	List<IntegralTradingRecord> selectListByOrderNo(@Param("userId") String userId, @Param("list") List<String> list);
}
