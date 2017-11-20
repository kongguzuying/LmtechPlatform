package com.ea.card.crm.facade.stub;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 积分交易
 * @author huacheng.li
 *
 */

@FeignClient(name = "ea-card-crm")
@RequestMapping(value = "/integraltrading")
public interface IntegralTradingFacade {
	
	
}
