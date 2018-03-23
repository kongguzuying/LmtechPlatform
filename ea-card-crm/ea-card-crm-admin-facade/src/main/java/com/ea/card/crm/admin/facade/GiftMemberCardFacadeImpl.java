package com.ea.card.crm.admin.facade;

import com.ea.card.crm.admin.facade.request.GiftMemberCardPageRequest;
import com.ea.card.crm.admin.facade.request.GiftMemberCardRequest;
import com.ea.card.crm.admin.facade.response.GiftMemberCardPageResponse;
import com.ea.card.crm.admin.facade.response.GiftMemberCardResponse;
import com.ea.card.crm.admin.facade.stub.GiftMemberCardFacade;
import com.ea.card.crm.admin.facade.stub.GiftMemberCardFacade;
import com.ea.card.crm.model.GiftMemberCard;
import com.ea.card.crm.service.GiftMemberCardService;
import com.lmtech.common.PageData;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.response.StringResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Api(description = "礼品会员卡接口")
@RestController
@RequestMapping(value = "/giftMemberCard")
public class GiftMemberCardFacadeImpl implements GiftMemberCardFacade {

    @Autowired
    private GiftMemberCardService giftMemberCardService;

    @Override
    @RequestMapping(value = "/getGiftMemberCardOfPage", method = RequestMethod.POST)
    @ApiOperation(value = "获取礼品会员卡分页数据")
    public GiftMemberCardPageResponse getGiftMemberCardOfPage(@RequestBody GiftMemberCardPageRequest request) {
        PageData<GiftMemberCard> pageData = giftMemberCardService.getPageData(request.getReqData(), request.getPageIndex(), request.getPageSize());

        GiftMemberCardPageResponse response = new GiftMemberCardPageResponse();
        response.setSuccess(true);
        response.setData(pageData);
        response.setMessage("获取礼品会员卡分页数据成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/getGiftMemberCard", method = RequestMethod.POST)
    @ApiOperation(value = "获取礼品会员卡数据")
    public GiftMemberCardResponse getGiftMemberCard(@RequestBody StringRequest request) {
        String id = request.getReqData();

        GiftMemberCard giftCategory = giftMemberCardService.get(id);

        GiftMemberCardResponse response = new GiftMemberCardResponse();
        response.setData(giftCategory);
        response.setSuccess(true);
        response.setMessage("获取礼品会员卡数据成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/removeGiftMemberCard", method = RequestMethod.POST)
    @ApiOperation(value = "删除礼品会员卡")
    public NormalResponse removeGiftMemberCard(@RequestBody StringRequest request) {
        String id = request.getReqData();
        giftMemberCardService.remove(id);
        NormalResponse response = new NormalResponse();
        response.setSuccess(true);
        response.setMessage("删除礼品会员卡成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/addGiftMemberCard", method = RequestMethod.POST)
    @ApiOperation(value = "添加礼品会员卡")
    public StringResponse addGiftMemberCard(@RequestBody GiftMemberCardRequest request) {
        GiftMemberCard giftCategory = request.getReqData();

        String id = giftMemberCardService.add(giftCategory);

        StringResponse response = new StringResponse(id);
        response.setSuccess(true);
        response.setMessage("添加礼品会员卡成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/editGiftMemberCard", method = RequestMethod.POST)
    @ApiOperation(value = "编辑礼品会员卡")
    public StringResponse editGiftMemberCard(@RequestBody GiftMemberCardRequest request) {
        GiftMemberCard giftCategory = request.getReqData();
        Assert.notNull(giftCategory.getId(), "传入礼品会员卡id不允许为空");

        giftMemberCardService.edit(giftCategory);

        StringResponse response = new StringResponse();
        response.setSuccess(true);
        response.setMessage("编辑礼品会员卡成功");
        return response;
    }
}
