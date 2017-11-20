package com.ea.card.crm.admin.facade;

import com.ea.card.crm.admin.facade.request.IntegralSetPageRequest;
import com.ea.card.crm.admin.facade.request.IntegralSetRequest;
import com.ea.card.crm.admin.facade.response.IntegralSetPageResponse;
import com.ea.card.crm.admin.facade.response.IntegralSetResponse;
import com.ea.card.crm.admin.facade.stub.IntegralSetFacade;
import com.ea.card.crm.model.IntegralSet;
import com.ea.card.crm.service.IntegralSetService;
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

@Api(value = "积分配置接口")
@RestController
@RequestMapping(value = "/integralSet")
public class IntegralSetFacadeImpl implements IntegralSetFacade {

    @Autowired
    private IntegralSetService integralSetService;

    @Override
    @RequestMapping(value = "/getIntegralSetOfPage", method = RequestMethod.POST)
    @ApiOperation(value = "获取积分配置分页数据")
    public IntegralSetPageResponse getIntegralSetOfPage(@RequestBody IntegralSetPageRequest request) {
        PageData<IntegralSet> pageData = integralSetService.getPageData(request.getPageParam(), request.getPageIndex(), request.getPageSize());

        IntegralSetPageResponse response = new IntegralSetPageResponse();
        response.setSuccess(true);
        response.setData(pageData);
        response.setMessage("获取积分配置分页数据成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/getIntegralSet", method = RequestMethod.POST)
    @ApiOperation(value = "获取积分配置数据")
    public IntegralSetResponse getIntegralSet(@RequestBody StringRequest request) {
        String id = request.getReqData();

        IntegralSet giftCategory = integralSetService.get(id);

        IntegralSetResponse response = new IntegralSetResponse();
        response.setData(giftCategory);
        response.setSuccess(true);
        response.setMessage("获取积分配置数据成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/removeIntegralSet", method = RequestMethod.POST)
    @ApiOperation(value = "删除积分配置分类")
    public NormalResponse removeIntegralSet(@RequestBody StringRequest request) {
        String id = request.getReqData();
        integralSetService.remove(id);
        NormalResponse response = new NormalResponse();
        response.setSuccess(true);
        response.setMessage("删除积分配置分类成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/addIntegralSet", method = RequestMethod.POST)
    @ApiOperation(value = "添加积分配置分类")
    public StringResponse addIntegralSet(@RequestBody IntegralSetRequest request) {
        IntegralSet giftCategory = request.getReqData();

        String id = integralSetService.add(giftCategory);

        StringResponse response = new StringResponse(id);
        response.setSuccess(true);
        response.setMessage("添加积分配置分类成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/editIntegralSet", method = RequestMethod.POST)
    @ApiOperation(value = "编辑积分配置分类")
    public StringResponse editIntegralSet(@RequestBody IntegralSetRequest request) {
        IntegralSet giftCategory = request.getReqData();
        Assert.notNull(giftCategory.getId(), "传入积分配置id不允许为空");

        integralSetService.edit(giftCategory);

        StringResponse response = new StringResponse();
        response.setSuccess(true);
        response.setMessage("编辑积分配置分类成功");
        return response;
    }
}
