package com.ea.card.crm.service.strategy;

import java.util.List;

/**
 * 购买商品兑换积分记录 Strategy
 * @author huacheng.li
 *
 */
public interface ExchangeIntegralStrategy {

	void exchangeIntegral(String userId, String tId,
			String orderSn, String productIds, long integralNum,
			int type, String shopName, String payChannel,
			String paytime, String sourceType);
}
