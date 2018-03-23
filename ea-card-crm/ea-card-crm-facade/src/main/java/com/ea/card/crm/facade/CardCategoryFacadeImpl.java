package com.ea.card.crm.facade;

import com.ea.card.crm.facade.stub.CardCategoryFacade;
import com.ea.card.crm.model.CardCategory;
import com.ea.card.crm.model.MemberRegister;
import com.ea.card.crm.service.CardCategoryService;
import com.ea.card.crm.service.GiftMemberCardService;
import com.ea.card.crm.service.MemberRegisterService;
import com.ea.card.crm.service.exception.NoneRegisterException;
import com.lmtech.common.ExeResult;
import com.lmtech.common.StateResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(description = "卡分类接口")
@RefreshScope
@RestController
@RequestMapping(value = "/cardcategory")
public class CardCategoryFacadeImpl implements CardCategoryFacade {

    @Value("${ui.cardcategory.img_banner_url}")
    private String imgBannerUrl;

    @Autowired
    private CardCategoryService cardCategoryService;
    @Autowired
    private GiftMemberCardService giftMemberCardService;
    @Autowired
    private MemberRegisterService memberRegisterService;

    @Override
    @RequestMapping(value = "getCategoryList", method = RequestMethod.GET)
    @ApiOperation("获取卡分类列表")
    public StateResult getCategoryList(@RequestParam(value = "openId", required = true) String openId) {

        ExeResult result = new ExeResult();
        MemberRegister register = memberRegisterService.getByOpenId(openId);
        if (register != null) {
            Map<String, Object> data = new HashMap<>();
            List<CardCategory> categories = cardCategoryService.getCategoryAndChildrensList();

            data.put("categories", categories);
            data.put("imgBannerUrl", imgBannerUrl);
            boolean hasNotPresentCards = giftMemberCardService.existNotPresentCards(register.getUserId());
            data.put("hasNotPresentCards", hasNotPresentCards);

            result.setSuccess(true);
            result.setData(data);
            return result.getResult();
        } else {
            throw new NoneRegisterException();
        }
    }
}
