package com.ea.card.crm.dao.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.card.crm.dao.IntegralTradingRecordDao;
import com.ea.card.crm.mapper.IntegralTradingRecordMapper;
import com.ea.card.crm.model.IntegralTradingRecord;
import com.lmtech.dao.MyBatisDaoBase;

/**
 * 积分兑奖记录DAO实现类
 * @author huacheng.li
 *
 */
@Service
public class IntegralTradingRecordDaoImpl extends MyBatisDaoBase<IntegralTradingRecordMapper, IntegralTradingRecord> implements IntegralTradingRecordDao {

	@Autowired
	private IntegralTradingRecordMapper integralTradingRecordMapper;
	
	@Override
	public List<IntegralTradingRecord> selectListByOrderNo(String userId, List<String> orderNoList) {
		return integralTradingRecordMapper.selectListByOrderNo(userId, orderNoList);
	}

	

}
