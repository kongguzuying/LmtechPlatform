package com.ea.card.crm.service.strategy.impl;

import com.ea.card.crm.constants.IntegralConstants;
import com.ea.card.crm.model.LotteryProduct;
import com.ea.card.crm.service.IntegralService;
import com.ea.card.crm.service.strategy.LotterySuccessStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 中奖积分策略
 * @author
 */
@Service
public class LotterySuccessIntegralStrategy implements LotterySuccessStrategy {

    @Autowired
    private IntegralService integralService;

    @Override
    public void handle(String userId, LotteryProduct lotteryProduct) {
        int integral = (int) lotteryProduct.getNumber();
        integralService.increaseIntegral(userId, integral, IntegralConstants.SOURCE_SEVEN);
    }
}
