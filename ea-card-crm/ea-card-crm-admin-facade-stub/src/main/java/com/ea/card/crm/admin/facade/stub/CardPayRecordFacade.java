package com.ea.card.crm.admin.facade.stub;

import com.ea.card.crm.admin.facade.request.CardPayRecordPageRequest;
import com.ea.card.crm.admin.facade.request.CardPayRecordRequest;
import com.ea.card.crm.admin.facade.response.CardPayRecordPageResponse;
import com.ea.card.crm.admin.facade.response.CardPayRecordResponse;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.response.StringResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "ea-card-crm-admin")
@RequestMapping(value = "/cardPayRecord")
public interface CardPayRecordFacade {

    @RequestMapping(value = "/getCardPayRecordOfPage", method = RequestMethod.POST)
    CardPayRecordPageResponse getCardPayRecordOfPage(@RequestBody CardPayRecordPageRequest request);

    @RequestMapping(value = "/getCardPayRecord", method = RequestMethod.POST)
    CardPayRecordResponse getCardPayRecord(@RequestBody StringRequest request);

    @RequestMapping(value = "/removeCardPayRecord", method = RequestMethod.POST)
    NormalResponse removeCardPayRecord(@RequestBody StringRequest request);

    @RequestMapping(value = "/addCardPayRecord", method = RequestMethod.POST)
    StringResponse addCardPayRecord(@RequestBody CardPayRecordRequest request);

    @RequestMapping(value = "/editCardPayRecord", method = RequestMethod.POST)
    StringResponse editCardPayRecord(@RequestBody CardPayRecordRequest request);
}
