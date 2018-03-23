package com.ea.card.crm.service.strategy;

import com.ea.card.crm.model.IntegralLottery;
import com.ea.card.crm.model.LotteryProduct;

import java.util.List;

/**
 * 积分抽奖策略
 * @author 
 */
public interface IntegralLotteryStrategy {
    /**
     * 抽奖
     * @return
     */
    LotteryProduct drawLottery(List<LotteryProduct> productList);
}
