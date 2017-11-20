package com.ea.card.crm.dao;

import java.util.List;

import com.ea.card.crm.model.IntegralTradingRecord;
import com.lmtech.dao.Dao;

/**
 * 积分兑换交易DAO
 * @author huacheng.li
 *
 */
public interface IntegralTradingRecordDao extends Dao<IntegralTradingRecord> {
	
	/**
	 * 根据订单号查询积分兑换记录
	 * @param userId
	 * @param orderNoList
	 * @return
	 */
	List<IntegralTradingRecord> selectListByOrderNo(String userId, List<String> orderNoList);
}
