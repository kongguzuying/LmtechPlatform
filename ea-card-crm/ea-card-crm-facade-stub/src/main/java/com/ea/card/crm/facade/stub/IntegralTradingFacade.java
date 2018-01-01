package com.ea.card.crm.facade.stub;

import com.ea.card.crm.facade.request.IntegralProductListRequest;
import com.ea.card.crm.facade.request.IntegralTradingRequest;
import com.lmtech.common.StateResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

/**
 * 积分交易
 * @author huacheng.li
 *
 */

@FeignClient(name = "ea-card-crm")
@RequestMapping(value = "/integraltrading")
public interface IntegralTradingFacade {
    /**
     * 积分兑换商品
     * @param request
     * @return
     */
    @RequestMapping(value = "/exchangeProduct", method = RequestMethod.POST)
    StateResult exchangeProduct(@RequestBody IntegralTradingRequest request);

    /**
     * 积分兑换商品列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/tradingProductList", method = RequestMethod.POST)
    StateResult exchangeProductList(@RequestBody IntegralProductListRequest request);

    /**
     * 兑换积分
     * @param unionId
     * @param orderSn
     * @param productIds
     * @param integralNum
     * @param type
     * @param shopName
     * @param payChannel
     * @param paytime
     * @param sourceType
     * @return
     */
    @RequestMapping(value = "/exchangeIntegral", method = RequestMethod.POST)
    StateResult exchangeIntegral(@RequestParam String unionId, @RequestParam String orderSn, @RequestParam String productIds,
                                        @RequestParam long integralNum, @RequestParam int type, @RequestParam String shopName,
                                        @RequestParam String payChannel, @RequestParam String paytime, @RequestParam String sourceType);
}