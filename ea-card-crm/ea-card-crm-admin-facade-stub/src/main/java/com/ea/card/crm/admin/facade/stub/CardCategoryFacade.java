package com.ea.card.crm.admin.facade.stub;

import com.ea.card.crm.admin.facade.request.CardPageRequest;
import com.ea.card.crm.admin.facade.request.CardRequest;
import com.ea.card.crm.admin.facade.response.CardListResponse;
import com.ea.card.crm.admin.facade.response.CardPageResponse;
import com.ea.card.crm.admin.facade.response.CardResponse;
import com.lmtech.facade.request.NormalRequest;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.BooleanResponse;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.response.StringResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 卡片分类
 * @author
 */
@FeignClient(name = "ea-card-crm-admin")
@RequestMapping(value = "/cardcategory")
public interface CardCategoryFacade {

    @RequestMapping(value = "/getCardOfPage", method = RequestMethod.POST)
    CardPageResponse getCardOfPage(@RequestBody CardPageRequest request);

    @RequestMapping(value = "/getCard", method = RequestMethod.POST)
    CardResponse getCard(@RequestBody StringRequest request);

    @RequestMapping(value = "/existTitleName", method = RequestMethod.POST)
    BooleanResponse existTitleName(@RequestBody StringRequest request);

    @RequestMapping(value = "/removeCard", method = RequestMethod.POST)
    NormalResponse removeCard(@RequestBody StringRequest request);

    @RequestMapping(value = "/addCard", method = RequestMethod.POST)
    StringResponse addCard(@RequestBody CardRequest request);

    @RequestMapping(value = "/editCard", method = RequestMethod.POST)
    StringResponse editCard(@RequestBody CardRequest request);

    @RequestMapping(value = "/getAllCard", method = RequestMethod.POST)
    CardListResponse getAllCard(NormalRequest request);
}
