package com.ea.card.crm.service;

import com.ea.card.crm.model.IntegralLottery;
import com.lmtech.service.DbManagerBase;

import java.util.List;

public interface IntegralLotteryService extends DbManagerBase<IntegralLottery> {
    /**
     * 获取最新中奖列表
     * @return
     */
    List<IntegralLottery> getLatestPrizeList();

    /**
     * 获取积分抽奖所需支付的积分
     * @return
     */
    int getIntegralPay();

    /**
     * 获取总数
     * @return
     */
    long getCount();
}
