package com.ea.card.crm.admin.facade.stub;

import com.ea.card.crm.admin.facade.request.MemberRegisterPageRequest;
import com.ea.card.crm.admin.facade.request.MemberRegisterRequest;
import com.ea.card.crm.admin.facade.response.MemberRegisterPageResponse;
import com.ea.card.crm.admin.facade.response.MemberRegisterResponse;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.facade.response.NormalResponse;
import com.lmtech.facade.response.StringResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "ea-card-crm-admin")
@RequestMapping(value = "/memberRegister")
public interface MemberRegisterFacade {
    /**
     * 获取会员注册分页数据
     * @param request
     * @return
     */
    @RequestMapping(value = "/getMemberRegisterOfPage", method = RequestMethod.POST)
    MemberRegisterPageResponse getMemberRegisterOfPage(@RequestBody MemberRegisterPageRequest request);

    /**
     * 获取会员注册数据
     * @param request
     * @return
     */
    @RequestMapping(value = "/getMemberRegister", method = RequestMethod.POST)
    MemberRegisterResponse getMemberRegister(@RequestBody StringRequest request);

    @RequestMapping(value = "/removeMemberRegister", method = RequestMethod.POST)
    NormalResponse removeMemberRegister(@RequestBody StringRequest request);

    @RequestMapping(value = "/addMemberRegister", method = RequestMethod.POST)
    StringResponse addMemberRegister(@RequestBody MemberRegisterRequest request);

    @RequestMapping(value = "/editMemberRegister", method = RequestMethod.POST)
    StringResponse editMemberRegister(@RequestBody MemberRegisterRequest request);
}
