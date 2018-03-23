package com.ea.card.crm.facade;

import com.ea.card.crm.constants.ErrorConstants;
import com.ea.card.crm.facade.request.*;
import com.ea.card.crm.facade.response.GetSmsCodeResult;
import com.ea.card.crm.facade.stub.MemberCardFacade;
import com.ea.card.crm.model.CardPayRecord;
import com.ea.card.crm.model.MemberRegister;
import com.ea.card.crm.service.*;
import com.ea.card.crm.service.exception.*;
import com.ea.card.crm.service.vo.PageAuthResponse;
import com.ea.card.crm.service.vo.RechargePayResult;
import com.lmtech.common.ContextManager;
import com.lmtech.common.ExeResult;
import com.lmtech.common.StateResult;
import com.lmtech.exceptions.ErrorCodeException;
import com.lmtech.infrastructure.facade.stub.CodeFacade;
import com.lmtech.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.util.*;

@Api(description = "会员卡接口")
@RefreshScope
@RestController
@RequestMapping(value = "/card")
public class MemberCardFacadeImpl implements MemberCardFacade {

	//vpass会员 微信卡面背景图片
    @Value("${member_card.vpass_wx_bg}")
    private String VPASS_WX_BG;
    
    //普通会员 微信 卡面背景图片
    @Value("${member_card.pass_wx_bg}")
    private String PASS_WX_BG;
    
    //vpass会员 微信卡面背景图片
    @Value("${member_card.vpass_h5_bg}")
    private String VPASS_H5_BG;
    
    //普通会员 微信 卡面背景图片
    @Value("${member_card.pass_h5_bg}")
    private String PASS_H5_BG;

    //vpass会员 卡金额
    @Value("${card.paymen.level_vpass_price}")
    private String LEVEL_VPASS_PRICE;
    
    @Autowired
    private WxService wxService;
    @Autowired
    private MemberCardService memberCardService;
    @Autowired
    private CardPayRecordService cardPayRecordService;
    @Autowired
    private MemberRegisterService memberRegisterService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private CodeAdaptorService codeAdaptorService;
    @Autowired
    private AliyunMessageService aliyunMessageService;


    @ApiOperation(value = "获取用户授权")
    @Override
    public StateResult getUserAuth(@RequestParam String code) {
        return memberCardService.userAuth(code);
    }

    @ApiOperation(value = "获取用户授权")
    @Override
    public StateResult getAppletUserAuth(@RequestParam String code, @RequestParam String encryptedData, @RequestParam String iv) throws UnsupportedEncodingException, InvalidAlgorithmParameterException {
        return memberCardService.appletUserAuth(code,encryptedData,iv);
    }

    @ApiOperation(value = "获取短信验证码")
    @RequestMapping(value = "/getSmsCode", method = RequestMethod.GET)
    public StateResult getSmsCode(HttpServletRequest request, @RequestParam String phone) {
        //短信接口暂时使用appname校验，防止恶意攻击
        String appName = request.getHeader("appname");
        if (StringUtil.isNullOrEmpty(appName)) {
            throw new IllegalArgumentException("必填参数不允许为空");
        }
        String validCode = RandomUtil.genRandomNumberString(4);
        ExeResult result = aliyunMessageService.sendSmsValidCode(phone, validCode);
        return result.getResult();
    }

    @ApiOperation(value = "激活会员卡")
    @RequestMapping(value = "/activeMemberCard", method = RequestMethod.POST)
    public StateResult activeMemberCard(@RequestBody ActiveMemberCardRequest request) {
        ExeResult result = new ExeResult();
        if (StringUtil.isNullOrEmpty(request.getOpenId())) {
            throw new IllegalArgumentException("用户授权OpenId为空");
        }

        memberCardService.activeMemberCardInTrans(request, result);
        result.setSuccess(true);
        return result.getResult();
    }

    @ApiOperation(value = "提升会员等级")
    @Override
    public StateResult increaseLevel(@Valid @RequestBody IncreaseLevelRequest request) {
        ExeResult result = new ExeResult();
        CardPayRecord record = new CardPayRecord();
        try {
            MemberRegister register = memberRegisterService.getByOpenId(request.getOpenId());
            if (register == null) {
                throw new NoneRegisterException();
            }
            /*if (register.getmLevel() >= request.getTargetLevel()) {
                throw new IncreaseLevelException();
            }*/

            //参数校验
            memberCardService.increaseLevelParamCheck(request);

            //构建会员卡提升等级订单
            GiftCardPayRequest payRequest = new GiftCardPayRequest();
            payRequest.setCardLevel(request.getTargetLevel());
            payRequest.setTotalAmount(Double.valueOf(LEVEL_VPASS_PRICE));
            payRequest.setTotalNumber(1);
            GiftCardPayDetail payDetail = new GiftCardPayDetail();
            payDetail.setPrice(Double.valueOf(LEVEL_VPASS_PRICE));
            payDetail.setNumber(1);
            payDetail.setPresentDays(request.getPresentDays());
            List<GiftCardPayDetail> payDetails = new ArrayList<>();
            payDetails.add(payDetail);
            payRequest.setGiftCardPayDetails(payDetails);

            record.setGiftRecord(JsonUtil.toJson(payRequest));
            record.setSource(CardPayRecord.SOURCE_INCREASELEVEL);
            ContextManager.setValue(CardPayRecord.CONTEXT_KEY, record);
            String tid = IdWorkerUtil.generateStringId();
            String orderNo = paymentService.rechargeRequest(tid, register.getUserId(), register.getPhone(), payRequest.getTotalAmount(), PaymentService.ORDER_REQUEST_TYPE_CARDPAY);
            RechargePayResult stateResult = paymentService.rechargePayment(tid, register.getUserId(), register.getPhone(), orderNo, register.getOfficialOpenId());
            if (stateResult.isSuccess()) {
                Map<String, String> signResult = wxService.getPaySign(stateResult.getData().getPrepayId());
                signResult.put("orderNo", orderNo);
                record.setStatus(CardPayRecord.STATUS_WAIT_PAY);
                result.setData(signResult);
                result.setSuccess(true);
            } else {
                throw new RechargePayException(stateResult.getMsg(), stateResult.getState());
            }
        } catch (Exception e) {
            record.setStatus(CardPayRecord.STATUS_ERROR);
            record.setErrMsg(e.getMessage());
            throw e;
        } finally {
            cardPayRecordService.add(record);
        }
        return result.getResult();
    }

    @ApiOperation(value = "提升会员等级成功操作")
    @Override
    public StateResult increaseLevelSuccess(@RequestBody IncreaseLevelSuccessRequest request) {
        ExeResult result = new ExeResult();
        MemberRegister register = memberRegisterService.getByOpenId(request.getOpenId());
        if (register != null) {
            CardPayRecord cardPayRecord = cardPayRecordService.getByOrderNo(request.getOrderNo());
            if (cardPayRecord == null) {
                throw new NotExistOrderException();
            }

            //校验微信订单 TODO
//            paymentService.checkOrderPaySuccess(request.getOrderNo());

            cardPayRecordService.orderFinished(request.getOrderNo());
            GiftCardPayRequest payRequest = (GiftCardPayRequest) JsonUtil.fromJson(cardPayRecord.getGiftRecord(), GiftCardPayRequest.class);
            GiftCardPayDetail payDetail = payRequest.getGiftCardPayDetails().get(0);

            int days = (payDetail.getNumber() * 365) + payDetail.getPresentDays();
            if (register.getmLevel() == payRequest.getCardLevel()) {
                //已经是Vpass会员，续期
                Date endDate = DateUtil.addDay(register.getEndDate(), days);
                register.setForever(false);
                register.setEndDate(endDate);
                if (register.isTrial()) {
                    //试用过的会员，结束试用
                    register.setTrialOver(true);
                }
                memberRegisterService.edit(register);
            } else {
                //升级为Vpass会员
                Date beginDate = new Date();
                Date endDate = DateUtil.addDay(beginDate, days);
                register.setmLevel(payRequest.getCardLevel());
                register.setForever(false);
                register.setBeginDate(beginDate);
                register.setEndDate(endDate);
                if (register.isTrial()) {
                    //试用过的会员，结束试用
                    register.setTrialOver(true);
                }
                memberRegisterService.edit(register);
                //升级成功，通知微信更新会员等级
                String mLevelName = codeAdaptorService.getNameByCodeItemValue(String.valueOf(MemberRegister.MLEVEL_VPASS));
                int mLevel = MemberRegister.MLEVEL_VPASS;
                String backgroundUrl = "";//VPASS_WX_BG;
                wxService.updateCardLevel(register.getCardId(), register.getCode(), mLevelName, mLevel, backgroundUrl, null, false);
            }
            result.setSuccess(true);
        } else {
            throw new NoneRegisterException();
        }
        return result.getResult();
    }
    
    @ApiOperation(value = "获取用户的session_key")
    @RequestMapping(value = "/getJscodeSession", method = RequestMethod.GET)
    public StateResult getJscodeSession(@RequestParam String wxCode) {
    	ExeResult result = new ExeResult();
        if (StringUtil.isNullOrEmpty(wxCode)) {
            throw new IllegalArgumentException("用户授权Code不能为空!");
        }
        PageAuthResponse response = wxService.getAppletPageAuthInfo(wxCode);
        result.setSuccess(true);
        result.setData(response);
    	return result.getResult();
    }
    
    /*@ApiOperation(value = "解密用户敏感数据")
    @RequestMapping(value = "/getAppletUserAuth", method = RequestMethod.POST)
    public StateResult getAppletUserAuth(@RequestParam String encryptedData, @RequestParam String iv, 
    		@RequestParam String wxCode) {
    	ExeResult result = new ExeResult();
    	try {
            if (StringUtil.isNullOrEmpty(encryptedData)) {
                throw new IllegalArgumentException("用户敏感加密数据不能为空!");
            }
            if (StringUtil.isNullOrEmpty(iv)) {
                throw new IllegalArgumentException("偏移量不能为空!");
            }
            
            
            PageAuthResponse response = wxService.getAppletPageAuthInfo(wxCode);
            String sessionKey = response.getSession_key();
            if (StringUtil.isNullOrEmpty(sessionKey)) {
                throw new IllegalArgumentException("微信接口异常，获取sessionKey失败！");
            }
            
            AESUtil aes = new AESUtil();
            byte[] resultByte = aes.decrypt(Base64.decodeBase64(encryptedData), Base64.decodeBase64(sessionKey), Base64.decodeBase64(iv));
            if(null != resultByte && resultByte.length > 0){
                String userInfo = new String(resultByte, "UTF-8");
                result.setSuccess(true);
                result.setData(userInfo);
            }

        } catch (IllegalArgumentException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            result.setErrCode(LmErrorConstants.ERR_ARG_ERROR);
        } catch (ErrorCodeException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            result.setErrCode(e.getErrorCode());
        } catch (Exception e) {
            LoggerManager.error(e);
            result.setSuccess(false);
            result.setErrCode(LmErrorConstants.ERR_UNKNOW);
            result.setMessage(LmErrorConstants.ERR_UNKNOW_MSG);
        }
    	return result.getResult();
    }*/

    private Map<String, String> getRequestParams(HttpServletRequest request) {
        Map<String, String> reqParams = new HashMap<>();
        Enumeration enu = request.getHeaderNames();// 取得全部头信息
        StringBuffer buf = new StringBuffer();
        String user_agent = "";
        while (enu.hasMoreElements()) {// 以此取出头信息
            String headerName = (String) enu.nextElement();
            String headerValue = request.getHeader(headerName);// 取出头信息内容
            if (headerName.equalsIgnoreCase("user-agent")) {
                user_agent = headerValue;
            }
            buf.append(headerName + "#" + headerValue + "|");
        }
        String headerInfo = buf.toString();
        String uri = request.getRequestURI();//返回请求行中的资源名称
        String url = request.getRequestURL().toString();//获得客户端发送请求的完整url
        String ip = getIp(request);

        reqParams.put("userType", "0"); //其他
        reqParams.put("realIp", ip);
        reqParams.put("url", url);
        reqParams.put("uri", uri);
        reqParams.put("userAgent", user_agent);
        reqParams.put("headerInfo", headerInfo);

        return reqParams;
    }

    private String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (!StringUtil.isNullOrEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (!StringUtil.isNullOrEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }
}
