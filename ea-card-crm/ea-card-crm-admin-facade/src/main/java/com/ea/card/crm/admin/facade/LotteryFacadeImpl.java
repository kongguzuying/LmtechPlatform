package com.ea.card.crm.admin.facade;

import com.ea.card.crm.admin.facade.request.LotteryPageRequest;
import com.ea.card.crm.admin.facade.request.LotteryRequest;
import com.ea.card.crm.admin.facade.stub.LotteryFacade;
import com.ea.card.crm.admin.facade.response.LotteryPageResponse;
import com.ea.card.crm.admin.facade.response.LotteryResponse;
import com.ea.card.crm.model.LotteryProduct;
import com.ea.card.crm.service.LotteryProductService;
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

@Api(description = "积分抽奖产品接口")
@RestController
@RequestMapping(value = "/lotteryProduct")
public class LotteryFacadeImpl implements LotteryFacade {

    @Autowired
    private LotteryProductService lotteryProductService;

    @Override
    @RequestMapping(value = "/getLotteryOfPage", method = RequestMethod.POST)
    @ApiOperation(value = "获取积分抽奖产品分页数据")
    public LotteryPageResponse getLotteryOfPage(@RequestBody LotteryPageRequest request) {
        PageData<LotteryProduct> pageData = lotteryProductService.getPageData(request.getReqData(), request.getPageIndex(), request.getPageSize());

        LotteryPageResponse response = new LotteryPageResponse();
        response.setSuccess(true);
        response.setData(pageData);
        response.setMessage("获取积分抽奖产品分页数据成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/getLottery", method = RequestMethod.POST)
    @ApiOperation(value = "获取积分抽奖产品数据")
    public LotteryResponse getLottery(@RequestBody StringRequest request) {
        String id = request.getReqData();

        LotteryProduct lotteryProduct = lotteryProductService.get(id);

        LotteryResponse response = new LotteryResponse();
        response.setData(lotteryProduct);
        response.setSuccess(true);
        response.setMessage("获取积分抽奖产品数据成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/removeLottery", method = RequestMethod.POST)
    @ApiOperation(value = "删除积分抽奖产品分类")
    public NormalResponse removeLottery(@RequestBody StringRequest request) {
        String id = request.getReqData();
        lotteryProductService.remove(id);
        NormalResponse response = new NormalResponse();
        response.setSuccess(true);
        response.setMessage("删除积分抽奖产品分类成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/addLottery", method = RequestMethod.POST)
    @ApiOperation(value = "添加积分抽奖产品分类")
    public StringResponse addLottery(@RequestBody LotteryRequest request) {
        LotteryProduct lotteryProduct = request.getReqData();

        String id = lotteryProductService.add(lotteryProduct);

        StringResponse response = new StringResponse(id);
        response.setSuccess(true);
        response.setMessage("添加积分抽奖产品分类成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/editLottery", method = RequestMethod.POST)
    @ApiOperation(value = "编辑积分抽奖产品分类")
    public StringResponse editLottery(@RequestBody LotteryRequest request) {
        LotteryProduct lotteryProduct = request.getReqData();
        Assert.notNull(lotteryProduct.getId(), "传入积分抽奖产品id不允许为空");

        lotteryProductService.edit(lotteryProduct);

        StringResponse response = new StringResponse();
        response.setSuccess(true);
        response.setMessage("编辑积分抽奖产品分类成功");
        return response;
    }
}
