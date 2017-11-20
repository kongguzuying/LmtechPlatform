package com.ea.card.crm.admin.facade;

import com.ea.card.crm.admin.facade.request.RechargePayRecordPageRequest;
import com.ea.card.crm.admin.facade.request.RechargePayRecordRequest;
import com.ea.card.crm.admin.facade.response.RechargePayRecordPageResponse;
import com.ea.card.crm.admin.facade.response.RechargePayRecordResponse;
import com.ea.card.crm.admin.facade.stub.RechargePayRecordFacade;
import com.ea.card.crm.admin.facade.stub.RechargePayRecordFacade;
import com.ea.card.crm.model.RechargePayRecord;
import com.ea.card.crm.service.RechargePayRecordService;
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

@Api(description = "充值记录接口")
@RestController
@RequestMapping(value = "/rechargePayRecord")
public class RechargePayRecordFacadeImpl implements RechargePayRecordFacade {

    @Autowired
    private RechargePayRecordService rechargePayRecordService;

    @Override
    @RequestMapping(value = "/getRechargePayRecordOfPage", method = RequestMethod.POST)
    @ApiOperation(value = "获取充值记录分页数据")
    public RechargePayRecordPageResponse getRechargePayRecordOfPage(@RequestBody RechargePayRecordPageRequest request) {
        PageData<RechargePayRecord> pageData = rechargePayRecordService.getPageData(request.getPageParam(), request.getPageIndex(), request.getPageSize());

        RechargePayRecordPageResponse response = new RechargePayRecordPageResponse();
        response.setSuccess(true);
        response.setData(pageData);
        response.setMessage("获取充值记录分页数据成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/getRechargePayRecord", method = RequestMethod.POST)
    @ApiOperation(value = "获取充值记录数据")
    public RechargePayRecordResponse getRechargePayRecord(@RequestBody StringRequest request) {
        String id = request.getReqData();

        RechargePayRecord rechargePayRecord = rechargePayRecordService.get(id);

        RechargePayRecordResponse response = new RechargePayRecordResponse();
        response.setData(rechargePayRecord);
        response.setSuccess(true);
        response.setMessage("获取充值记录数据成功");
        return response;
    }


    @Override
    @RequestMapping(value = "/removeRechargePayRecord", method = RequestMethod.POST)
    @ApiOperation(value = "删除充值记录分类")
    public NormalResponse removeRechargePayRecord(@RequestBody StringRequest request) {
        String id = request.getReqData();
        rechargePayRecordService.remove(id);
        NormalResponse response = new NormalResponse();
        response.setSuccess(true);
        response.setMessage("删除充值记录分类成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/addRechargePayRecord", method = RequestMethod.POST)
    @ApiOperation(value = "添加充值记录分类")
    public StringResponse addRechargePayRecord(@RequestBody RechargePayRecordRequest request) {
        RechargePayRecord rechargePayRecord = request.getReqData();

        String id = rechargePayRecordService.add(rechargePayRecord);

        StringResponse response = new StringResponse(id);
        response.setSuccess(true);
        response.setMessage("添加充值记录分类成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/editRechargePayRecord", method = RequestMethod.POST)
    @ApiOperation(value = "编辑充值记录分类")
    public StringResponse editRechargePayRecord(@RequestBody RechargePayRecordRequest request) {
        RechargePayRecord rechargePayRecord = request.getReqData();
        Assert.notNull(rechargePayRecord.getId(), "传入充值记录id不允许为空");

        rechargePayRecordService.edit(rechargePayRecord);

        StringResponse response = new StringResponse();
        response.setSuccess(true);
        response.setMessage("编辑充值记录分类成功");
        return response;
    }
}
