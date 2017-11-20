package com.ea.card.crm.admin.facade.stub;

import com.ea.card.crm.admin.facade.request.CardPresentRecordPageRequest;
import com.ea.card.crm.admin.facade.request.CardPresentRecordRequest;
import com.ea.card.crm.admin.facade.response.CardPresentRecordPageResponse;
import com.ea.card.crm.admin.facade.response.CardPresentRecordResponse;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.response.StringResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "ea-card-crm-admin")
@RequestMapping(value = "/cardPresentRecord")
public interface CardPresentRecordFacade {

    @RequestMapping(value = "/getCardPresentRecordOfPage", method = RequestMethod.POST)
    CardPresentRecordPageResponse getCardPresentRecordOfPage(@RequestBody CardPresentRecordPageRequest request);

    @RequestMapping(value = "/getCardPresentRecord", method = RequestMethod.POST)
    CardPresentRecordResponse getCardPresentRecord(@RequestBody StringRequest request);

    @RequestMapping(value = "/removeCardPresentRecord", method = RequestMethod.POST)
    NormalResponse removeCardPresentRecord(@RequestBody StringRequest request);

    @RequestMapping(value = "/addCardPresentRecord", method = RequestMethod.POST)
    StringResponse addCardPresentRecord(@RequestBody CardPresentRecordRequest request);

    @RequestMapping(value = "/editCardPresentRecord", method = RequestMethod.POST)
    StringResponse editCardPresentRecord(@RequestBody CardPresentRecordRequest request);
}
