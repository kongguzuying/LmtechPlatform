package com.ea.card.crm.admin.facade;

import com.ea.card.crm.admin.facade.stub.CardActiveRecordFacade;
import com.ea.card.crm.admin.facade.request.CardActiveRecordPageRequest;
import com.ea.card.crm.admin.facade.request.CardActiveRecordRequest;
import com.ea.card.crm.admin.facade.response.CardActiveRecordPageResponse;
import com.ea.card.crm.admin.facade.response.CardActiveRecordResponse;
import com.ea.card.crm.model.CardActiveRecord;
import com.ea.card.crm.service.CardActiveRecordService;
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

@Api(description = "卡激活记录接口")
@RestController
@RequestMapping(value = "/cardActiveRecord")
public class CardActiveRecordFacadeImpl implements CardActiveRecordFacade {

    @Autowired
    private CardActiveRecordService cardActiveRecordService;

    @Override
    @RequestMapping(value = "/getCardActiveRecordOfPage", method = RequestMethod.POST)
    @ApiOperation(value = "获取卡激活记录分页数据")
    public CardActiveRecordPageResponse getCardActiveRecordOfPage(@RequestBody CardActiveRecordPageRequest request) {
        PageData<CardActiveRecord> pageData = cardActiveRecordService.getPageData(request.getReqData(), request.getPageIndex(), request.getPageSize());

        CardActiveRecordPageResponse response = new CardActiveRecordPageResponse();
        response.setSuccess(true);
        response.setData(pageData);
        response.setMessage("获取卡激活记录分页数据成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/getCardActiveRecord", method = RequestMethod.POST)
    @ApiOperation(value = "获取卡激活记录数据")
    public CardActiveRecordResponse getCardActiveRecord(@RequestBody StringRequest request) {
        String id = request.getReqData();

        CardActiveRecord cardActiveRecord = cardActiveRecordService.get(id);

        CardActiveRecordResponse response = new CardActiveRecordResponse();
        response.setData(cardActiveRecord);
        response.setSuccess(true);
        response.setMessage("获取卡激活记录数据成功");
        return response;
    }


    @Override
    @RequestMapping(value = "/removeCardActiveRecord", method = RequestMethod.POST)
    @ApiOperation(value = "删除卡激活记录分类")
    public NormalResponse removeCardActiveRecord(@RequestBody StringRequest request) {
        String id = request.getReqData();
        cardActiveRecordService.remove(id);
        NormalResponse response = new NormalResponse();
        response.setSuccess(true);
        response.setMessage("删除卡激活记录分类成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/addCardActiveRecord", method = RequestMethod.POST)
    @ApiOperation(value = "添加卡激活记录分类")
    public StringResponse addCardActiveRecord(@RequestBody CardActiveRecordRequest request) {
        CardActiveRecord cardActiveRecord = request.getReqData();

        String id = cardActiveRecordService.add(cardActiveRecord);

        StringResponse response = new StringResponse(id);
        response.setSuccess(true);
        response.setMessage("添加卡激活记录分类成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/editCardActiveRecord", method = RequestMethod.POST)
    @ApiOperation(value = "编辑卡激活记录分类")
    public StringResponse editCardActiveRecord(@RequestBody CardActiveRecordRequest request) {
        CardActiveRecord cardActiveRecord = request.getReqData();
        Assert.notNull(cardActiveRecord.getId(), "传入卡激活记录id不允许为空");

        cardActiveRecordService.edit(cardActiveRecord);

        StringResponse response = new StringResponse();
        response.setSuccess(true);
        response.setMessage("编辑卡激活记录分类成功");
        return response;
    }
}
