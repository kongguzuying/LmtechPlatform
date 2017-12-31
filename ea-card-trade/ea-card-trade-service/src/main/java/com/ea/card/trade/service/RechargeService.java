package com.ea.card.trade.service;

import com.ea.card.trade.vo.UnifiedOrderParam;

import java.util.Map;

/**
 * 充值服务
 * @author huang.jb
 */
public interface RechargeService {
    /**
     * 充值下单
     * @param param
     * @return
     */
    String recharge(UnifiedOrderParam param);
}
