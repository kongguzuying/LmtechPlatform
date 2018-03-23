package com.ea.card.crm.facade;

import com.ea.card.crm.facade.request.LoginWapRequest;
import com.ea.card.crm.facade.request.TrailMemberRequest;
import com.ea.card.crm.facade.response.MemberInfoData;
import com.ea.card.crm.facade.stub.MemberFacade;
import com.ea.card.crm.model.MemberRegister;
import com.ea.card.crm.service.MemberRegisterService;
import com.ea.card.crm.service.MemberService;
import com.ea.card.crm.service.WxService;
import com.ea.card.crm.service.exception.IntfInvokeException;
import com.ea.card.crm.service.exception.NoneRegisterException;
import com.ea.card.crm.service.exception.WxIntfInvokeException;
import com.ea.card.crm.service.vo.LoginWapResult;
import com.ea.card.crm.service.vo.UCMemberExtData;
import com.ea.card.crm.service.vo.UCMemberExtResult;
import com.ea.card.crm.service.vo.WxDecryptCodeResponse;
import com.lmtech.common.ExeResult;
import com.lmtech.common.StateResult;
import com.lmtech.common.StateResultT;
import com.lmtech.util.DateUtil;
import com.lmtech.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Api(description = "会员接口")
@RestController
@RequestMapping(value = "/member")
public class MemberFacadeImpl implements MemberFacade {

    @Autowired
    private MemberRegisterService memberRegisterService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private WxService wxService;

    @ApiOperation(value = "登录门户")
    @Override
    public StateResult login4wap(@RequestBody LoginWapRequest request) {
        ExeResult result = new ExeResult();
        LoginWapResult loginWapResult = memberService.loginWap(request.getOpenId(), request.getCode());

        if (loginWapResult.isSuccess()) {
            result.setSuccess(true);
            result.setData(loginWapResult.getData());
        } else {
            result.setSuccess(false);
            result.setMessage(loginWapResult.getMsg());
        }
        return result.getResult();
    }

    @ApiOperation(value = "校验token")
    @Override
    public StateResult validToken(@RequestParam String token) {
        ExeResult result = new ExeResult();
        memberService.validToken(token);
        result.setSuccess(true);
        return result.getResult();
    }

    @ApiOperation(value = "获取会员信息")
    @Override
    public StateResult getMemberInfo(@RequestParam String openId) {
        ExeResult result = new ExeResult();
        MemberRegister register = memberRegisterService.getByOpenId(openId);
        if (register != null) {
            UCMemberExtResult ucMemberExtResult = memberService.getUcMemberExt(register.getUserId());
            if (!ucMemberExtResult.isSuccess()) {
                throw new IntfInvokeException(ucMemberExtResult.getMsg(), ucMemberExtResult.getState());
            }

            if (ucMemberExtResult.getData() == null) {
                ucMemberExtResult.setData(new UCMemberExtData());
            }

            MemberInfoData memberInfo = new MemberInfoData();
            memberInfo.setUserId(register.getUserId());
            memberInfo.setBeginDate(register.getBeginDate());
            memberInfo.setEndDate(register.getEndDate());
            memberInfo.setBonus(ucMemberExtResult.getData().getSumIntegralNumber());
            memberInfo.setCode(register.getCode());
            memberInfo.setMembershipNum(register.getMembershipNum());
            memberInfo.setmLevel(register.getmLevel());
            memberInfo.setOpenId(register.getOpenId());
            memberInfo.setPhone(register.getPhone());
            memberInfo.setCardBackground(register.getCardBackground());
            memberInfo.setTrail(register.isTrial());
            memberInfo.setTrailing(register.isTrialing());
            memberInfo.setTrailDate(register.getTrialDate());
            memberInfo.setForever(register.isForever());
            if (!memberInfo.isForever()) {
                memberInfo.setOverTimeDays(DateUtil.getDaySub(new Date(), register.getEndDate()));
            }
            result.setSuccess(true);
            result.setData(memberInfo);
            return result.getResult();
        } else {
            throw new NoneRegisterException();
        }
    }

    @ApiOperation(value = "通过用户id获取会员信息")
    @Override
    public StateResult getByUserId(@RequestParam String userId) {
        ExeResult result = new ExeResult();
        if (StringUtil.isNullOrEmpty(userId)) {
            throw new IllegalArgumentException("传入userId不允许为空");
        }

        MemberRegister memberRegister = memberRegisterService.getByUserId(userId);
        if (memberRegister != null) {
            if (!memberRegister.isForever()) {
                memberRegister.setOverTimeDays(DateUtil.getDaySub(new Date(), memberRegister.getEndDate()));
            }
            result.setSuccess(true);
            result.setData(memberRegister);
        } else {
            throw new NoneRegisterException();
        }
        return result.getResult();
    }

    @ApiOperation(value = "通过公众号openid获取会员信息")
    public StateResult getByOfficialOpenId(@RequestParam String officialOpenId) {
        ExeResult result = new ExeResult();
        MemberRegister memberRegister = memberRegisterService.getByOfficialOpenId(officialOpenId);
        if (memberRegister != null) {
            if (!memberRegister.isForever()) {
                memberRegister.setOverTimeDays(DateUtil.getDaySub(new Date(), memberRegister.getEndDate()));
            }
            result.setData(memberRegister);
            result.setSuccess(true);
            return result.getResult();
        } else {
            throw new NoneRegisterException();
        }
    }

    @ApiOperation(value = "通过微信激活回传的真正的公众号openid来获取会员信息")
    @Override
    public StateResultT<MemberRegister> getByWxActiveOpenId(@RequestParam String wxActiveOpenId) {
        ExeResult result = new ExeResult();
        MemberRegister memberRegister = memberRegisterService.getByWxActiveOpenId(wxActiveOpenId);
        if (memberRegister != null) {
            if (!memberRegister.isForever()) {
                memberRegister.setOverTimeDays(DateUtil.getDaySub(new Date(), memberRegister.getEndDate()));
            }
            result.setSuccess(true);
            result.setData(memberRegister);
            StateResult stateResult = result.getResult();

            StateResultT<MemberRegister> stateResultT = new StateResultT<>();
            stateResultT.setState(stateResult.getState());
            stateResultT.settId(stateResult.gettId());
            stateResultT.setData((MemberRegister) stateResult.getData());
            stateResultT.setMsg(stateResult.getMsg());
            return stateResultT;
        } else {
            throw new NoneRegisterException();
        }
    }

    @ApiOperation(value = "通过卡片加密code获取会员信息")
    @Override
    public StateResult getByEncryptCode(@RequestParam String encryptCode) {
        ExeResult result = new ExeResult();
        if (StringUtil.isNullOrEmpty(encryptCode)) {
            throw new IllegalArgumentException("传入encryptCode为空");
        }

        WxDecryptCodeResponse response = wxService.decryptCode(encryptCode);
        if (response.isSuccess()) {
            MemberRegister register = memberRegisterService.getByCode(response.getCode());
            if (register == null) {
                throw new NoneRegisterException();
            }
            //设置到期时间
            if (!register.isForever()) {
                register.setOverTimeDays(DateUtil.getDaySub(new Date(), register.getEndDate()));
            }
            //设置token
            LoginWapResult loginWapResult = memberService.reLoginWap(register);
            if (loginWapResult.isSuccess()) {
                register.setToken(loginWapResult.getData().getToken());
            }
            result.setSuccess(true);
            result.setData(register);
        } else {
            throw new WxIntfInvokeException(response);
        }
        return result.getResult();
    }

    @ApiOperation(value = "试用会员资格")
    @Override
    public StateResult trailMember(@RequestBody TrailMemberRequest request) {
        ExeResult result = new ExeResult();
        if (StringUtil.isNullOrEmpty(request.getOpenId())) {
            throw new IllegalArgumentException("传入openid为空");
        }
        if (request.getmLevel() <= 0) {
            throw new IllegalArgumentException("会员等级传入为0");
        }

        memberService.trailMember(request.getOpenId(), request.getmLevel());
        result.setSuccess(true);
        return result.getResult();
    }

    @ApiOperation(value = "能否试用会员")
    @Override
    public StateResult hasTrail(@RequestBody TrailMemberRequest request) {
        ExeResult result = new ExeResult();
        if (StringUtil.isNullOrEmpty(request.getOpenId())) {
            throw new IllegalArgumentException("传入openid为空");
        }
        if (request.getmLevel() <= 0) {
            throw new IllegalArgumentException("会员等级传入为0");
        }

        boolean hasTrail = memberService.hasTrail(request.getOpenId(), request.getmLevel());
        result.setSuccess(hasTrail);
        return result.getResult();
    }

}
