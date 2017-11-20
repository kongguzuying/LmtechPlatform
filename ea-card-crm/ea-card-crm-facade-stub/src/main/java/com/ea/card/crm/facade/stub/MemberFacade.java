package com.ea.card.crm.facade.stub;

import com.ea.card.crm.facade.request.LoginWapRequest;
import com.ea.card.crm.facade.request.TrailMemberRequest;
import com.lmtech.common.StateResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ea-card-crm")
@RequestMapping(value = "/member")
public interface MemberFacade {

    /**
     * 登录门户
     * @param request
     * @return
     */
    @RequestMapping(value = "/login4wap", method = RequestMethod.POST)
    StateResult login4wap(@RequestBody LoginWapRequest request);

    /**
     * 校验token是否过期
     * @param token
     * @return
     */
    @RequestMapping(value = "/validToken", method = RequestMethod.GET)
    StateResult validToken(@RequestParam String token);

    /**
     * 获取会员信息
     * @param openId
     * @return
     */
    @RequestMapping(value = "/getMemberInfo", method = RequestMethod.GET)
    StateResult getMemberInfo(@RequestParam String openId);

    /**
     * 通过用户id获取会员信息
     * @param userId
     * @return
     */
    @RequestMapping(value = "/getByUserId",method = { RequestMethod.GET, RequestMethod.POST })
    StateResult getByUserId(@RequestParam String userId);

    /**
     * 通过公众号openid获取会员信息
     * @param officialOpenId
     * @return
     */
    @RequestMapping(value = "/getByOfficialOpenId",method = RequestMethod.GET)
    StateResult getByOfficialOpenId(@RequestParam String officialOpenId);

    /**
     * 通过加密卡code获取会员信息
     * @param encryptCode
     * @return
     */
    @RequestMapping(value = "/getByEncryptCode",method = RequestMethod.GET)
    StateResult getByEncryptCode(@RequestParam String encryptCode);

    /**
     * 试用会员
     * @param request
     * @return
     */
    @RequestMapping(value = "/trailMember", method = RequestMethod.POST)
    StateResult trailMember(@RequestBody TrailMemberRequest request);

    /**
     * 能否试用会员
     * @param request
     * @return
     */
    @RequestMapping(value = "/hasTrail", method = RequestMethod.POST)
    StateResult hasTrail(@RequestBody TrailMemberRequest request);
}
