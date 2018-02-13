package com.ea.card.crm.facade.stub;

import com.lmtech.common.StateResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 卡片分类
 * @author
 */
@FeignClient(name = "ea-card-crm-ywop")
@RequestMapping(value = "/cardcategory")
public interface CardCategoryFacade {
    @RequestMapping(value = "getCategoryList", method = RequestMethod.GET)
    StateResult getCategoryList(@RequestParam(value = "openId",required = false) String openId);
}
