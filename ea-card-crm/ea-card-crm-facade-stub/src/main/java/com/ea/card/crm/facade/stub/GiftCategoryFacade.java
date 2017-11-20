package com.ea.card.crm.facade.stub;

import com.ea.card.crm.facade.request.CardPresentRequest;
import com.ea.card.crm.facade.response.GiftCategoryResult;
import com.lmtech.common.StateResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "ea-card-crm")
@RequestMapping(value = "/giftcategory")
public interface GiftCategoryFacade {
    /**
     * 获取礼品分类列表
     * @return
     */
    @RequestMapping(value = "getCardPresentPageData", method = RequestMethod.POST)
    StateResult getCardPresentPageData(@RequestBody CardPresentRequest request);

    @RequestMapping(value = "findGiftCategoryList",method = RequestMethod.POST)
    GiftCategoryResult findGiftCategoryList();
}
