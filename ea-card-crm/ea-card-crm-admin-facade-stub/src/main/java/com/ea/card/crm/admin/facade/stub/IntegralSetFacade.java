package com.ea.card.crm.admin.facade.stub;

import com.ea.card.crm.admin.facade.request.IntegralSetPageRequest;
import com.ea.card.crm.admin.facade.request.IntegralSetRequest;
import com.ea.card.crm.admin.facade.response.IntegralSetPageResponse;
import com.ea.card.crm.admin.facade.response.IntegralSetResponse;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.response.StringResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "ea-card-crm-admin")
@RequestMapping(value = "/integralSet")
public interface IntegralSetFacade {

    @RequestMapping(value = "/getIntegralSetOfPage", method = RequestMethod.POST)
    IntegralSetPageResponse getIntegralSetOfPage(@RequestBody IntegralSetPageRequest request);

    @RequestMapping(value = "/getIntegralSet", method = RequestMethod.POST)
    IntegralSetResponse getIntegralSet(@RequestBody StringRequest request);

    @RequestMapping(value = "/removeIntegralSet", method = RequestMethod.POST)
    NormalResponse removeIntegralSet(@RequestBody StringRequest request);

    @RequestMapping(value = "/addIntegralSet", method = RequestMethod.POST)
    StringResponse addIntegralSet(@RequestBody IntegralSetRequest request);

    @RequestMapping(value = "/editIntegralSet", method = RequestMethod.POST)
    StringResponse editIntegralSet(@RequestBody IntegralSetRequest request);
}
