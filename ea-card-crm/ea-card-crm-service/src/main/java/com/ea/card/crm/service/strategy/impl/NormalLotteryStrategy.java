package com.ea.card.crm.service.strategy.impl;

import com.ea.card.crm.dao.IntegralLotteryDao;
import com.ea.card.crm.model.LotteryProduct;
import com.ea.card.crm.service.exception.NoExistNoprizeProductException;
import com.ea.card.crm.service.exception.NoExistPrizeProductException;
import com.ea.card.crm.service.strategy.IntegralLotteryStrategy;
import com.lmtech.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 普通积分抽奖，接概率处理
 * @author
 */
@Service
@RefreshScope
public class NormalLotteryStrategy implements IntegralLotteryStrategy {

    @Value("${integral.lottery.coefficient}")
    private long coefficient;     //抽奖系数

    @Autowired
    private IntegralLotteryDao integralLotteryDao;

    @Override
    public LotteryProduct drawLottery(List<LotteryProduct> productList) {
        boolean prize = false;
        long count = integralLotteryDao.selectCount(null);
        if (count != 0 && count % coefficient == 0) {
            prize = true;
        }

        List<LotteryProduct> prizeProducts = new ArrayList<>();
        List<LotteryProduct> noPrizeProducts = new ArrayList<>();
        for (LotteryProduct product : productList) {
            if (product.isPrize()) {
                prizeProducts.add(product);
            } else {
                if (product.isJoin()) {
                    noPrizeProducts.add(product);
                }
            }
        }

        if (prize) {
            //中奖
            int allProductCount = prizeProducts.size();
            if (allProductCount > 1) {
                int prizeIndex = RandomUtil.genRandomNumber(allProductCount + 1);
                LotteryProduct prizeProduct = prizeProducts.get(prizeIndex - 1);
                return prizeProduct;
            } else if (allProductCount == 1) {
                return prizeProducts.get(0);
            } else {
                throw new NoExistPrizeProductException();
            }
        } else {
            //没中奖
            int allProductCount = noPrizeProducts.size();
            if (allProductCount > 1) {
                int noPrizeIndex = RandomUtil.genRandomNumber(allProductCount + 1);
                LotteryProduct noPrizeProduct = noPrizeProducts.get(noPrizeIndex - 1);
                return noPrizeProduct;
            } else if (allProductCount == 1) {
                return noPrizeProducts.get(0);
            } else {
                throw new NoExistNoprizeProductException();
            }
        }
    }
}
