package com.ea.card.crm.service.strategy;

import com.ea.card.crm.model.LotteryProduct;

/**
 * 中奖处理策略
 */
public interface LotterySuccessStrategy {
    /**
     * 处理中奖商品
     * @param userId
     * @param lotteryProduct
     */
    void handle(String userId, LotteryProduct lotteryProduct);
}
