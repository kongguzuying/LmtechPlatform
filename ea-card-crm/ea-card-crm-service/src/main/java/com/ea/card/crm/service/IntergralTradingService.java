package com.ea.card.crm.service;

import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;

import com.ea.card.crm.facade.request.IntegralTradingRequest;
import com.ea.card.crm.model.IntegralTradingRecord;
import com.ea.card.crm.service.vo.RefreshSettleData;
import com.ea.card.crm.service.vo.ZeroMenoyPayData;

/**
 * 积分交易 service
 * @author huacheng.li
 *
 */
public interface IntergralTradingService {
	
	
	/**
	 * * 兑换积分商品
	 * @param userId  用户ID
	 * @param t_id	流水号
	 * @param consumptionIntegral 消费积分
	 * @param openId 微信openId
	 * @param token 商城登录token
	 * @param goodslist 商品集合 [{"goodsid":"17323","specid":"1057"}] 
	 * @param amount 商品数据
	 */
	IntegralTradingRecord consumerProduct(IntegralTradingRequest request);
	
	/**
	 * 提交订单
	 * @param submitOrderData
	 */
	String submitOrder(RefreshSettleData refreshSettleData, String openId, long consumptionIntegral);
	
	/**
	 * 0元支付
	 * @param zeroMenoyPayData
	 */
	void zeroMenoyPay(ZeroMenoyPayData zeroMenoyPayData);
	
	/**
	 * 兑换商品记录
	 * @param record
	 * @return id
	 */
	String exchangeProductRecord(IntegralTradingRecord record);
	
	/**
	 * 查询兑换商品列表
	 * @param userId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	LinkedHashMap<String, Object> exchangeProductList(String token, String pageNum, String pageSize,String userId) throws ParseException;
}
