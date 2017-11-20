package com.ea.card.crm.admin.facade;

import com.ea.card.crm.admin.facade.request.IntegralRulePageRequest;
import com.ea.card.crm.admin.facade.request.IntegralRuleRequest;
import com.ea.card.crm.admin.facade.response.IntegralRulePageResponse;
import com.ea.card.crm.admin.facade.response.IntegralRuleResponse;
import com.ea.card.crm.admin.facade.stub.IntegralRuleFacade;
import com.ea.card.crm.model.IntegralRule;
import com.ea.card.crm.service.IntegralRuleService;
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

@Api(value = "积分规则接口")
@RestController
@RequestMapping(value = "/integralRule")
public class IntegralRuleFacadeImpl implements IntegralRuleFacade {

    @Autowired
    private IntegralRuleService integralRuleService;

    @Override
    @RequestMapping(value = "/getIntegralRuleOfPage", method = RequestMethod.POST)
    @ApiOperation(value = "获取积分规则分页数据")
    public IntegralRulePageResponse getIntegralRuleOfPage(@RequestBody IntegralRulePageRequest request) {
        //TODO 根据参数条件过滤
        IntegralRule integralRule = request.getPageParam();

        PageData<IntegralRule> pageData = integralRuleService.getPageData(integralRule, request.getPageIndex(), request.getPageSize());

        IntegralRulePageResponse response = new IntegralRulePageResponse();
        response.setSuccess(true);
        response.setData(pageData);
        response.setMessage("获取积分规则分页数据成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/getIntegralRule", method = RequestMethod.POST)
    @ApiOperation(value = "获取积分规则数据")
    public IntegralRuleResponse getIntegralRule(@RequestBody StringRequest request) {
        String id = request.getReqData();

        IntegralRule integralRule = integralRuleService.get(id);

        IntegralRuleResponse response = new IntegralRuleResponse();
        response.setData(integralRule);
        response.setSuccess(true);
        response.setMessage("获取积分规则数据成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/removeIntegralRule", method = RequestMethod.POST)
    @ApiOperation(value = "删除积分规则")
    public NormalResponse removeIntegralRule(@RequestBody StringRequest request) {
        String id = request.getReqData();
        integralRuleService.remove(id);
        NormalResponse response = new NormalResponse();
        response.setSuccess(true);
        response.setMessage("删除积分规则成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/addIntegralRule", method = RequestMethod.POST)
    @ApiOperation(value = "添加积分规则")
    public StringResponse addIntegralRule(@RequestBody IntegralRuleRequest request) {
        IntegralRule integralRule = request.getReqData();

        String id = integralRuleService.add(integralRule);

        StringResponse response = new StringResponse(id);
        response.setSuccess(true);
        response.setMessage("添加积分规则成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/editIntegralRule", method = RequestMethod.POST)
    @ApiOperation(value = "编辑积分规则")
    public StringResponse editIntegralRule(@RequestBody IntegralRuleRequest request) {
        IntegralRule integralRule = request.getReqData();
        Assert.notNull(integralRule.getId(), "传入积分规则id不允许为空");

        integralRuleService.edit(integralRule);

        StringResponse response = new StringResponse();
        response.setSuccess(true);
        response.setMessage("编辑积分规则成功");
        return response;
    }
}
