package com.ea.card.crm.admin.facade.stub;

import com.ea.card.crm.admin.facade.request.IntegralRulePageRequest;
import com.ea.card.crm.admin.facade.request.IntegralRuleRequest;
import com.ea.card.crm.admin.facade.response.IntegralRulePageResponse;
import com.ea.card.crm.admin.facade.response.IntegralRuleResponse;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.response.StringResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "ea-card-crm-admin")
@RequestMapping(value = "/integralRule")
public interface IntegralRuleFacade {

    @RequestMapping(value = "/getIntegralRuleOfPage", method = RequestMethod.POST)
    IntegralRulePageResponse getIntegralRuleOfPage(@RequestBody IntegralRulePageRequest request);

    @RequestMapping(value = "/getIntegralRule", method = RequestMethod.POST)
    IntegralRuleResponse getIntegralRule(@RequestBody StringRequest request);

    @RequestMapping(value = "/removeIntegralRule", method = RequestMethod.POST)
    NormalResponse removeIntegralRule(@RequestBody StringRequest request);

    @RequestMapping(value = "/addIntegralRule", method = RequestMethod.POST)
    StringResponse addIntegralRule(@RequestBody IntegralRuleRequest request);

    @RequestMapping(value = "/editIntegralRule", method = RequestMethod.POST)
    StringResponse editIntegralRule(@RequestBody IntegralRuleRequest request);
}
