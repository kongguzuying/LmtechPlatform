package com.ea.card.crm.admin.facade.stub;

import com.ea.card.crm.admin.facade.request.GiftPageRequest;
import com.ea.card.crm.admin.facade.request.GiftRequest;
import com.ea.card.crm.admin.facade.response.GiftPageResponse;
import com.ea.card.crm.admin.facade.response.GiftResponse;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.response.StringResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "ea-card-crm-admin")
@RequestMapping(value = "/giftcategory")
public interface GiftCategoryFacade {

    @RequestMapping(value = "/getGiftOfPage", method = RequestMethod.POST)
    GiftPageResponse getGiftOfPage(@RequestBody GiftPageRequest request);

    @RequestMapping(value = "/getGift", method = RequestMethod.POST)
    GiftResponse getGift(@RequestBody StringRequest request);

    @RequestMapping(value = "/removeGift", method = RequestMethod.POST)
    NormalResponse removeGift(@RequestBody StringRequest request);

    @RequestMapping(value = "/addGift", method = RequestMethod.POST)
    StringResponse addGift(@RequestBody GiftRequest request);

    @RequestMapping(value = "/editGift", method = RequestMethod.POST)
    StringResponse editGift(@RequestBody GiftRequest request);
}
