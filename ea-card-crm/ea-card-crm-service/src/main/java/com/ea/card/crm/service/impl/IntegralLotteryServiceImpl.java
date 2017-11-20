package com.ea.card.crm.service.impl;

import com.ea.card.crm.dao.IntegralLotteryDao;
import com.ea.card.crm.model.IntegralLottery;
import com.ea.card.crm.service.IntegralLotteryService;
import com.lmtech.common.PageData;
import com.lmtech.dao.Dao;
import com.lmtech.service.support.AbstractDbManagerBaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RefreshScope
public class IntegralLotteryServiceImpl extends AbstractDbManagerBaseImpl<IntegralLottery> implements IntegralLotteryService {

    /** 显示最近的中奖数 **/
    @Value("${integral.lottery.show_latest_prize_count}")
    private int showLatestPrizeCount;

    /** 积分抽奖支付 **/
    @Value("${integral.lottery.integral_pay}")
    private int integralPay;

    @Autowired
    private IntegralLotteryDao integralLotteryDao;

    @Override
    public Dao getDao() {
        return integralLotteryDao;
    }

    @Override
    public List<IntegralLottery> getLatestPrizeList() {
        PageData<IntegralLottery> pageData = integralLotteryDao.getLatestPrizeList(1, showLatestPrizeCount);
        if (pageData.getTotal() > 0) {
            return (List<IntegralLottery>) pageData.getItems();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public int getIntegralPay() {
        return integralPay;
    }

    @Override
    public long getCount() {
        return integralLotteryDao.selectCount(null);
    }
}
