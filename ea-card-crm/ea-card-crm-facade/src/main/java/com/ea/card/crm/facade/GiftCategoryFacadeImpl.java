package com.ea.card.crm.facade;

import com.ea.card.crm.constants.ErrorConstants;
import com.ea.card.crm.constants.IntegralConstants;
import com.ea.card.crm.facade.request.CardPresentRequest;
import com.ea.card.crm.facade.response.*;
import com.ea.card.crm.facade.stub.GiftCategoryFacade;
import com.ea.card.crm.model.CardCategory;
import com.ea.card.crm.model.GiftCategory;
import com.ea.card.crm.model.GiftMemberCard;
import com.ea.card.crm.model.MemberRegister;
import com.ea.card.crm.service.*;
import com.ea.card.crm.service.exception.NoneRegisterException;
import com.lmtech.common.ExeResult;
import com.lmtech.common.StateResult;
import com.lmtech.exceptions.ErrorCodeException;
import com.lmtech.util.IdWorkerUtil;
import com.lmtech.util.LoggerManager;
import com.lmtech.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(description = "礼品分类接口")
@RestController
@RequestMapping(value = "/giftcategory")
public class GiftCategoryFacadeImpl implements GiftCategoryFacade {

    @Autowired
    private GiftCategoryService giftCategoryService;
    @Autowired
    private CardCategoryService cardCategoryService;
    @Autowired
    private GiftMemberCardService giftMemberCardService;
    @Autowired
    private MemberRegisterService memberRegisterService;
    @Autowired
    private CodeAdaptorService codeAdaptorService;

    @ApiOperation("获取礼品卡片赠送页面数据")
    @Override
    public StateResult getCardPresentPageData(@Valid @RequestBody CardPresentRequest request) {
        ExeResult result = new ExeResult();
        if (StringUtil.isNullOrEmpty(request.getOpenId())) {
            throw new IllegalArgumentException("传入openid为允许为空");
        }

        MemberRegister register = memberRegisterService.getByOpenId(request.getOpenId());
        if (register != null) {
            Map<String, Object> data = new HashMap<>();
            List<GiftCategory> categories = giftCategoryService.getCategoryList();

            List<Map<String, Object>> levels = new ArrayList<>();
            Map<String, Object> level1 = new HashMap<>();
            level1.put("levelName", codeAdaptorService.getNameByCodeItemValue(String.valueOf(MemberRegister.MLEVEL_NORMAL)));
            level1.put("levelValue", MemberRegister.MLEVEL_NORMAL);
            Map<String, Object> level2 = new HashMap<>();
            level2.put("levelName", codeAdaptorService.getNameByCodeItemValue(String.valueOf(MemberRegister.MLEVEL_VPASS)));
            level2.put("levelValue", MemberRegister.MLEVEL_VPASS);
            levels.add(level1);
            levels.add(level2);

            List<CardCategory> childCategories = cardCategoryService.getChildCategoryList(request.getCardCategoryId());
            List<GiftMemberCard> noPresentCardOfFree = giftMemberCardService.getActivePresentCard(register.getUserId());

            data.put("giftCategories", categories);
            data.put("childCategories", childCategories);
            data.put("noPresentCards", noPresentCardOfFree);
            data.put("cardLevels", levels);
            data.put("logoImgUrl", "");
            result.setSuccess(true);
            result.setData(data);
        } else {
            throw new NoneRegisterException();
        }
        return result.getResult();
    }

    @RequestMapping(value = "/findGiftCategoryList", method = RequestMethod.POST)
    @Override
    @ApiOperation("获取礼品卡列表数据")
    public GiftCategoryResult findGiftCategoryList() {
        GiftCategoryResult result = new GiftCategoryResult();
        String t_id = IdWorkerUtil.generateStringId();
        try {
            List<GiftCategory> categories = giftCategoryService.getCategoryList();

            GiftCategoryData data = new GiftCategoryData();
            data.setLastPage(IntegralConstants.ZERO);
            data.setTotalPage(categories.size());
            data.setList(categories);

            result.setState(0);
            result.setData(data);
        } catch (Exception ex) {
            LoggerManager.error(ex.getMessage());
            result.setState(-1);
            result.setMsg(IntegralConstants.ERROR1);
        }
        result.settId(t_id);
        return result;
    }
}
