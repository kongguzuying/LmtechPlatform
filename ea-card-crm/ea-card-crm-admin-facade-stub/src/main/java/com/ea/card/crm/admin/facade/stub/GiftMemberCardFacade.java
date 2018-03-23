package com.ea.card.crm.admin.facade.stub;

import com.ea.card.crm.admin.facade.request.GiftMemberCardPageRequest;
import com.ea.card.crm.admin.facade.request.GiftMemberCardRequest;
import com.ea.card.crm.admin.facade.response.GiftMemberCardPageResponse;
import com.ea.card.crm.admin.facade.response.GiftMemberCardResponse;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.response.StringResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "ea-card-crm-admin")
@RequestMapping(value = "/giftMemberCard")
public interface GiftMemberCardFacade {

    @RequestMapping(value = "/getGiftMemberCardOfPage", method = RequestMethod.POST)
    GiftMemberCardPageResponse getGiftMemberCardOfPage(@RequestBody GiftMemberCardPageRequest request);

    @RequestMapping(value = "/getGiftMemberCard", method = RequestMethod.POST)
    GiftMemberCardResponse getGiftMemberCard(@RequestBody StringRequest request);

    @RequestMapping(value = "/removeGiftMemberCard", method = RequestMethod.POST)
    NormalResponse removeGiftMemberCard(@RequestBody StringRequest request);

    @RequestMapping(value = "/addGiftMemberCard", method = RequestMethod.POST)
    StringResponse addGiftMemberCard(@RequestBody GiftMemberCardRequest request);

    @RequestMapping(value = "/editGiftMemberCard", method = RequestMethod.POST)
    StringResponse editGiftMemberCard(@RequestBody GiftMemberCardRequest request);
}
