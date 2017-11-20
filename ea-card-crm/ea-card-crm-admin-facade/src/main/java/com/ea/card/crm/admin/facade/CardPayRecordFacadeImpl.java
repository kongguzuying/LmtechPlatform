package com.ea.card.crm.admin.facade;

import com.ea.card.crm.admin.facade.request.CardPayRecordPageRequest;
import com.ea.card.crm.admin.facade.request.CardPayRecordRequest;
import com.ea.card.crm.admin.facade.response.CardPayRecordPageResponse;
import com.ea.card.crm.admin.facade.response.CardPayRecordResponse;
import com.ea.card.crm.admin.facade.stub.CardPayRecordFacade;
import com.ea.card.crm.model.CardPayRecord;
import com.ea.card.crm.service.CardPayRecordService;
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

@Api(description = "卡片购买记录接口")
@RestController
@RequestMapping(value = "/cardPayRecord")
public class CardPayRecordFacadeImpl implements CardPayRecordFacade {

    @Autowired
    private CardPayRecordService cardPayRecordService;

    @Override
    @RequestMapping(value = "/getCardPayRecordOfPage", method = RequestMethod.POST)
    @ApiOperation(value = "获取卡片购买记录分页数据")
    public CardPayRecordPageResponse getCardPayRecordOfPage(@RequestBody CardPayRecordPageRequest request) {
        PageData<CardPayRecord> pageData = cardPayRecordService.getPageData(request.getPageParam(), request.getPageIndex(), request.getPageSize());

        CardPayRecordPageResponse response = new CardPayRecordPageResponse();
        response.setSuccess(true);
        response.setData(pageData);
        response.setMessage("获取卡片购买记录分页数据成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/getCardPayRecord", method = RequestMethod.POST)
    @ApiOperation(value = "获取卡片购买记录数据")
    public CardPayRecordResponse getCardPayRecord(@RequestBody StringRequest request) {
        String id = request.getReqData();

        CardPayRecord cardPayRecord = cardPayRecordService.get(id);

        CardPayRecordResponse response = new CardPayRecordResponse();
        response.setData(cardPayRecord);
        response.setSuccess(true);
        response.setMessage("获取卡片购买记录数据成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/removeCardPayRecord", method = RequestMethod.POST)
    @ApiOperation(value = "删除卡片购买记录分类")
    public NormalResponse removeCardPayRecord(@RequestBody StringRequest request) {
        String id = request.getReqData();
        cardPayRecordService.remove(id);
        NormalResponse response = new NormalResponse();
        response.setSuccess(true);
        response.setMessage("删除卡片购买记录分类成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/addCardPayRecord", method = RequestMethod.POST)
    @ApiOperation(value = "添加卡片购买记录分类")
    public StringResponse addCardPayRecord(@RequestBody CardPayRecordRequest request) {
        CardPayRecord cardPayRecord = request.getReqData();

        String id = cardPayRecordService.add(cardPayRecord);

        StringResponse response = new StringResponse(id);
        response.setSuccess(true);
        response.setMessage("添加卡片购买记录分类成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/editCardPayRecord", method = RequestMethod.POST)
    @ApiOperation(value = "编辑卡片购买记录分类")
    public StringResponse editCardPayRecord(@RequestBody CardPayRecordRequest request) {
        CardPayRecord cardPayRecord = request.getReqData();
        Assert.notNull(cardPayRecord.getId(), "传入卡片购买记录id不允许为空");

        cardPayRecordService.edit(cardPayRecord);

        StringResponse response = new StringResponse();
        response.setSuccess(true);
        response.setMessage("编辑卡片购买记录分类成功");
        return response;
    }
}
