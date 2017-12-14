package com.ea.card.crm.admin.facade;

import com.ea.card.crm.admin.facade.request.CardPageRequest;
import com.ea.card.crm.admin.facade.request.CardRequest;
import com.ea.card.crm.admin.facade.response.CardListResponse;
import com.ea.card.crm.admin.facade.response.CardPageResponse;
import com.ea.card.crm.admin.facade.response.CardResponse;
import com.ea.card.crm.admin.facade.stub.CardCategoryFacade;
import com.ea.card.crm.model.CardCategory;
import com.ea.card.crm.service.CardCategoryService;
import com.lmtech.common.PageData;
import com.lmtech.facade.request.NormalRequest;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.BooleanResponse;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.response.StringResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(description = "卡分类接口")
@RestController
@RequestMapping(value = "/cardcategory")
public class CardCategoryFacadeImpl implements CardCategoryFacade {

    @Autowired
    private CardCategoryService cardCategoryService;

    @Override
    @RequestMapping(value = "/getCardOfPage", method = RequestMethod.POST)
    @ApiOperation(value = "获取卡分页数据")
    public CardPageResponse getCardOfPage(@RequestBody CardPageRequest request) {
        PageData<CardCategory> pageData = cardCategoryService.getPageData(request.getReqData(), request.getPageIndex(), request.getPageSize());

        CardPageResponse response = new CardPageResponse();
        response.setSuccess(true);
        response.setData(pageData);
        response.setMessage("获取卡分页数据成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/getCard", method = RequestMethod.POST)
    @ApiOperation(value = "获取卡数据")
    public CardResponse getCard(@RequestBody StringRequest request) {
        String id = request.getReqData();

        CardCategory giftCategory = cardCategoryService.get(id);

        CardResponse response = new CardResponse();
        response.setData(giftCategory);
        response.setSuccess(true);
        response.setMessage("获取卡数据成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/existTitleName", method = RequestMethod.POST)
    @ApiOperation(value = "是否已存在卡分类标题")
    public BooleanResponse existTitleName(@RequestBody StringRequest request) {
        String title = request.getReqData();

        boolean exist = cardCategoryService.existTitleName(title);

        BooleanResponse response = new BooleanResponse(exist);
        response.setSuccess(true);
        response.setMessage("查询是否存在相同的卡分类标题成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/removeCard", method = RequestMethod.POST)
    @ApiOperation(value = "删除卡分类")
    public NormalResponse removeCard(@RequestBody StringRequest request) {
        String id = request.getReqData();

        cardCategoryService.remove(id);

        NormalResponse response = new NormalResponse();
        response.setSuccess(true);
        response.setMessage("删除卡分类成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/addCard", method = RequestMethod.POST)
    @ApiOperation(value = "添加卡分类")
    public StringResponse addCard(@RequestBody CardRequest request) {
        CardCategory giftCategory = request.getReqData();

        String id = cardCategoryService.add(giftCategory);

        StringResponse response = new StringResponse(id);
        response.setSuccess(true);
        response.setMessage("添加卡分类成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/editCard", method = RequestMethod.POST)
    @ApiOperation(value = "编辑卡分类")
    public StringResponse editCard(@RequestBody CardRequest request) {
        CardCategory giftCategory = request.getReqData();
        Assert.notNull(giftCategory.getId(), "传入卡分类id不允许为空");

        cardCategoryService.edit(giftCategory);

        StringResponse response = new StringResponse();
        response.setSuccess(true);
        response.setMessage("编辑卡分类成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/getAllCard", method = RequestMethod.POST)
    @ApiOperation(value = "获取所有卡分类数据")
    public CardListResponse getAllCard(@RequestBody NormalRequest request) {
        List<CardCategory> cards = cardCategoryService.getAll();
        CardListResponse response = new CardListResponse();
        response.setSuccess(true);
        response.setData(cards);
        response.setMessage("获取所有卡分类成功");
        return response;
    }
}
