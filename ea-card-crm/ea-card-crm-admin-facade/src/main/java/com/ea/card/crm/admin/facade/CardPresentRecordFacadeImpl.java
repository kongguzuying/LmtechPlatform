package com.ea.card.crm.admin.facade;

import com.ea.card.crm.admin.facade.request.CardPresentRecordPageRequest;
import com.ea.card.crm.admin.facade.request.CardPresentRecordRequest;
import com.ea.card.crm.admin.facade.response.CardPresentRecordPageResponse;
import com.ea.card.crm.admin.facade.response.CardPresentRecordResponse;
import com.ea.card.crm.admin.facade.stub.CardPresentRecordFacade;
import com.ea.card.crm.model.CardPresentRecord;
import com.ea.card.crm.service.CardPresentRecordService;
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

@Api(description = "卡片赠送记录接口")
@RestController
@RequestMapping(value = "/cardPresentRecord")
public class CardPresentRecordFacadeImpl implements CardPresentRecordFacade {

    @Autowired
    private CardPresentRecordService cardPresentRecordService;

    @Override
    @RequestMapping(value = "/getCardPresentRecordOfPage", method = RequestMethod.POST)
    @ApiOperation(value = "获取卡片赠送记录分页数据")
    public CardPresentRecordPageResponse getCardPresentRecordOfPage(@RequestBody CardPresentRecordPageRequest request) {
        PageData<CardPresentRecord> pageData = cardPresentRecordService.getPageData(request.getReqData(), request.getPageIndex(), request.getPageSize());

        CardPresentRecordPageResponse response = new CardPresentRecordPageResponse();
        response.setSuccess(true);
        response.setData(pageData);
        response.setMessage("获取卡片赠送记录分页数据成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/getCardPresentRecord", method = RequestMethod.POST)
    @ApiOperation(value = "获取卡片赠送记录数据")
    public CardPresentRecordResponse getCardPresentRecord(@RequestBody StringRequest request) {
        String id = request.getReqData();

        CardPresentRecord cardPresentRecord = cardPresentRecordService.get(id);

        CardPresentRecordResponse response = new CardPresentRecordResponse();
        response.setData(cardPresentRecord);
        response.setSuccess(true);
        response.setMessage("获取卡片赠送记录数据成功");
        return response;
    }


    @Override
    @RequestMapping(value = "/removeCardPresentRecord", method = RequestMethod.POST)
    @ApiOperation(value = "删除卡片赠送记录分类")
    public NormalResponse removeCardPresentRecord(@RequestBody StringRequest request) {
        String id = request.getReqData();
        cardPresentRecordService.remove(id);
        NormalResponse response = new NormalResponse();
        response.setSuccess(true);
        response.setMessage("删除卡片赠送记录分类成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/addCardPresentRecord", method = RequestMethod.POST)
    @ApiOperation(value = "添加卡片赠送记录分类")
    public StringResponse addCardPresentRecord(@RequestBody CardPresentRecordRequest request) {
        CardPresentRecord cardPresentRecord = request.getReqData();

        String id = cardPresentRecordService.add(cardPresentRecord);

        StringResponse response = new StringResponse(id);
        response.setSuccess(true);
        response.setMessage("添加卡片赠送记录分类成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/editCardPresentRecord", method = RequestMethod.POST)
    @ApiOperation(value = "编辑卡片赠送记录分类")
    public StringResponse editCardPresentRecord(@RequestBody CardPresentRecordRequest request) {
        CardPresentRecord cardPresentRecord = request.getReqData();
        Assert.notNull(cardPresentRecord.getId(), "传入卡片赠送记录id不允许为空");

        cardPresentRecordService.edit(cardPresentRecord);

        StringResponse response = new StringResponse();
        response.setSuccess(true);
        response.setMessage("编辑卡片赠送记录分类成功");
        return response;
    }
}
