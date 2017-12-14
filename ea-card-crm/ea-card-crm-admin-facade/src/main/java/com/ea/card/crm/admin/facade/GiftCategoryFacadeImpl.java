package com.ea.card.crm.admin.facade;

import com.ea.card.crm.admin.facade.request.GiftPageRequest;
import com.ea.card.crm.admin.facade.request.GiftRequest;
import com.ea.card.crm.admin.facade.response.GiftPageResponse;
import com.ea.card.crm.admin.facade.response.GiftResponse;
import com.ea.card.crm.admin.facade.stub.GiftCategoryFacade;
import com.ea.card.crm.model.GiftCategory;
import com.ea.card.crm.service.GiftCategoryService;
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

@Api(description = "礼品分类接口")
@RestController
@RequestMapping(value = "/giftcategory")
public class GiftCategoryFacadeImpl implements GiftCategoryFacade {

    @Autowired
    private GiftCategoryService giftCategoryService;

    @Override
    @RequestMapping(value = "/getGiftOfPage", method = RequestMethod.POST)
    @ApiOperation(value = "获取礼品分页数据")
    public GiftPageResponse getGiftOfPage(@RequestBody GiftPageRequest request) {
        GiftCategory giftParam = request.getReqData();
        PageData<GiftCategory> pageData = giftCategoryService.getPageData(giftParam, request.getPageIndex(), request.getPageSize());

        GiftPageResponse response = new GiftPageResponse();
        response.setSuccess(true);
        response.setData(pageData);
        response.setMessage("获取礼品分页数据成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/getGift", method = RequestMethod.POST)
    @ApiOperation(value = "获取礼品数据")
    public GiftResponse getGift(@RequestBody StringRequest request) {
        String id = request.getReqData();

        GiftCategory giftCategory = giftCategoryService.get(id);

        GiftResponse response = new GiftResponse();
        response.setData(giftCategory);
        response.setSuccess(true);
        response.setMessage("获取礼品数据成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/removeGift", method = RequestMethod.POST)
    @ApiOperation(value = "删除礼品分类")
    public NormalResponse removeGift(@RequestBody StringRequest request) {
        String id = request.getReqData();
        giftCategoryService.remove(id);
        NormalResponse response = new NormalResponse();
        response.setSuccess(true);
        response.setMessage("删除礼品分类成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/addGift", method = RequestMethod.POST)
    @ApiOperation(value = "添加礼品分类")
    public StringResponse addGift(@RequestBody GiftRequest request) {
        GiftCategory giftCategory = request.getReqData();

        String id = giftCategoryService.add(giftCategory);

        StringResponse response = new StringResponse(id);
        response.setSuccess(true);
        response.setMessage("添加礼品分类成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/editGift", method = RequestMethod.POST)
    @ApiOperation(value = "编辑礼品分类")
    public StringResponse editGift(@RequestBody GiftRequest request) {
        GiftCategory giftCategory = request.getReqData();
        Assert.notNull(giftCategory.getId(), "传入礼品分类id不允许为空");

        giftCategoryService.edit(giftCategory);

        StringResponse response = new StringResponse();
        response.setSuccess(true);
        response.setMessage("编辑礼品分类成功");
        return response;
    }
}
