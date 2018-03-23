package com.ea.card.crm.admin.facade.stub;

import com.ea.card.crm.admin.facade.request.CardActiveRecordPageRequest;
import com.ea.card.crm.admin.facade.request.CardActiveRecordRequest;
import com.ea.card.crm.admin.facade.response.CardActiveRecordPageResponse;
import com.ea.card.crm.admin.facade.response.CardActiveRecordResponse;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.response.StringResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "ea-card-crm-admin")
@RequestMapping(value = "/cardActiveRecord")
public interface CardActiveRecordFacade {

    @RequestMapping(value = "/getCardActiveRecordOfPage", method = RequestMethod.POST)
    CardActiveRecordPageResponse getCardActiveRecordOfPage(@RequestBody CardActiveRecordPageRequest request);

    @RequestMapping(value = "/getCardActiveRecord", method = RequestMethod.POST)
    CardActiveRecordResponse getCardActiveRecord(@RequestBody StringRequest request);

    @RequestMapping(value = "/removeCardActiveRecord", method = RequestMethod.POST)
    NormalResponse removeCardActiveRecord(@RequestBody StringRequest request);

    @RequestMapping(value = "/addCardActiveRecord", method = RequestMethod.POST)
    StringResponse addCardActiveRecord(@RequestBody CardActiveRecordRequest request);

    @RequestMapping(value = "/editCardActiveRecord", method = RequestMethod.POST)
    StringResponse editCardActiveRecord(@RequestBody CardActiveRecordRequest request);
}
