package com.ea.card.crm.admin.facade.stub;

import com.ea.card.crm.admin.facade.request.RechargePayRecordPageRequest;
import com.ea.card.crm.admin.facade.request.RechargePayRecordRequest;
import com.ea.card.crm.admin.facade.response.RechargePayRecordPageResponse;
import com.ea.card.crm.admin.facade.response.RechargePayRecordResponse;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.response.StringResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "ea-card-crm-admin")
@RequestMapping(value = "/rechargePayRecord")
public interface RechargePayRecordFacade {

    @RequestMapping(value = "/getRechargePayRecordOfPage", method = RequestMethod.POST)
    RechargePayRecordPageResponse getRechargePayRecordOfPage(@RequestBody RechargePayRecordPageRequest request);

    @RequestMapping(value = "/getRechargePayRecord", method = RequestMethod.POST)
    RechargePayRecordResponse getRechargePayRecord(@RequestBody StringRequest request);

    @RequestMapping(value = "/removeRechargePayRecord", method = RequestMethod.POST)
    NormalResponse removeRechargePayRecord(@RequestBody StringRequest request);

    @RequestMapping(value = "/addRechargePayRecord", method = RequestMethod.POST)
    StringResponse addRechargePayRecord(@RequestBody RechargePayRecordRequest request);

    @RequestMapping(value = "/editRechargePayRecord", method = RequestMethod.POST)
    StringResponse editRechargePayRecord(@RequestBody RechargePayRecordRequest request);
}
