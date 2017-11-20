package com.ea.card.crm.admin.facade;

import com.ea.card.crm.admin.facade.request.MemberRegisterPageRequest;
import com.ea.card.crm.admin.facade.request.MemberRegisterRequest;
import com.ea.card.crm.admin.facade.response.MemberRegisterPageResponse;
import com.ea.card.crm.admin.facade.response.MemberRegisterResponse;
import com.ea.card.crm.admin.facade.stub.MemberRegisterFacade;
import com.ea.card.crm.model.MemberRegister;
import com.ea.card.crm.service.MemberRegisterService;
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

@Api(description = "会员注册接口")
@RestController
@RequestMapping(value = "/memberRegister")
public class MemberRegisterFacadeImpl implements MemberRegisterFacade {

    @Autowired
    private MemberRegisterService memberRegisterService;

    @Override
    @RequestMapping(value = "/getMemberRegisterOfPage", method = RequestMethod.POST)
    @ApiOperation(value = "获取会员注册分页数据")
    public MemberRegisterPageResponse getMemberRegisterOfPage(@RequestBody MemberRegisterPageRequest request) {
        PageData<MemberRegister> pageData = memberRegisterService.getPageData(request.getPageParam(), request.getPageIndex(), request.getPageSize());

        MemberRegisterPageResponse response = new MemberRegisterPageResponse();
        response.setSuccess(true);
        response.setData(pageData);
        response.setMessage("获取会员注册分页数据成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/getMemberRegister", method = RequestMethod.POST)
    @ApiOperation(value = "获取会员注册数据")
    public MemberRegisterResponse getMemberRegister(@RequestBody StringRequest request) {
        String id = request.getReqData();

        MemberRegister giftCategory = memberRegisterService.get(id);

        MemberRegisterResponse response = new MemberRegisterResponse();
        response.setData(giftCategory);
        response.setSuccess(true);
        response.setMessage("获取会员注册数据成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/removeMemberRegister", method = RequestMethod.POST)
    @ApiOperation(value = "删除会员注册分类")
    public NormalResponse removeMemberRegister(@RequestBody StringRequest request) {
        String id = request.getReqData();
        memberRegisterService.remove(id);
        NormalResponse response = new NormalResponse();
        response.setSuccess(true);
        response.setMessage("删除会员注册分类成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/addMemberRegister", method = RequestMethod.POST)
    @ApiOperation(value = "添加会员注册分类")
    public StringResponse addMemberRegister(@RequestBody MemberRegisterRequest request) {
        MemberRegister giftCategory = request.getReqData();

        String id = memberRegisterService.add(giftCategory);

        StringResponse response = new StringResponse(id);
        response.setSuccess(true);
        response.setMessage("添加会员注册分类成功");
        return response;
    }

    @Override
    @RequestMapping(value = "/editMemberRegister", method = RequestMethod.POST)
    @ApiOperation(value = "编辑会员注册分类")
    public StringResponse editMemberRegister(@RequestBody MemberRegisterRequest request) {
        MemberRegister giftCategory = request.getReqData();
        Assert.notNull(giftCategory.getId(), "传入会员注册id不允许为空");

        memberRegisterService.edit(giftCategory);

        StringResponse response = new StringResponse();
        response.setSuccess(true);
        response.setMessage("编辑会员注册分类成功");
        return response;
    }
}
