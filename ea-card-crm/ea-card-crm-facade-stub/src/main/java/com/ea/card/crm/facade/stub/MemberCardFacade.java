package com.ea.card.crm.facade.stub;

import com.ea.card.crm.facade.request.ActiveMemberCardRequest;
import com.ea.card.crm.facade.request.IncreaseLevelRequest;
import com.ea.card.crm.facade.request.IncreaseLevelSuccessRequest;
import com.ea.card.crm.facade.response.GetSmsCodeResult;
import com.lmtech.common.StateResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;

@FeignClient(name = "ea-card-crm")
@RequestMapping(value = "/card")
public interface MemberCardFacade {
    /**
     * 获取用户授权
     * @param code
     * @return
     */
    @RequestMapping(value = "/getUserAuth", method = RequestMethod.GET)
    StateResult getUserAuth(@RequestParam String code);

    /**
     * 获取用户授权
     * @param code
     * @return
     */
    @RequestMapping(value = "/getAppletUserAuth", method = RequestMethod.GET)
    StateResult getAppletUserAuth(@RequestParam String code, @RequestParam String encryptedData, @RequestParam String iv) throws UnsupportedEncodingException, InvalidAlgorithmParameterException;

    /**
     * 获取短信验证码
     * @param phone
     * @return
     */
    @RequestMapping(value = "/getSmsCode", method = RequestMethod.GET)
    StateResult getSmsCode(HttpServletRequest request, @RequestParam String phone);

    /**
     * 激活会员卡
     * @param request
     * @return
     */
    @RequestMapping(value = "/activeMemberCard", method = RequestMethod.POST)
    StateResult activeMemberCard(@RequestBody ActiveMemberCardRequest request);

    /**
     * 提升会员等级
     * @param request
     * @return
     */
    @RequestMapping(value = "/increaseLevel", method = RequestMethod.POST)
    StateResult increaseLevel(@RequestBody IncreaseLevelRequest request);

    /**
     * 提升会员等级成功处理
     * @param request
     * @return
     */
    @RequestMapping(value = "/increaseLevelSuccess", method = RequestMethod.POST)
    StateResult increaseLevelSuccess(@RequestBody IncreaseLevelSuccessRequest request);
}
