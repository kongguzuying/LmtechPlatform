package com.ea.card.crm.facade;

import com.ea.card.crm.facade.request.CardApiSignRequest;
import com.ea.card.crm.facade.request.JsApiSignRequest;
import com.ea.card.crm.facade.request.WxApiParam;
import com.ea.card.crm.facade.stub.WxTokenFacade;
import com.ea.card.crm.service.MemberRegisterService;
import com.ea.card.crm.service.WxService;
import com.ea.card.crm.service.constants.WxConstants;
import com.ea.card.crm.service.util.WxUrlSignUtil;
import com.ea.card.crm.service.vo.JsapiAddressRequest;
import com.ea.card.crm.service.vo.PageAuthResponse;
import com.lmtech.common.ExeResult;
import com.lmtech.common.StateResult;
import com.lmtech.util.LoggerManager;
import com.lmtech.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(description = "微信Token接口")
@RestController
@RequestMapping(value = "/wxtoken")
public class WxTokenFacadeImpl implements WxTokenFacade {

    @Autowired
    private WxService wxService;
    @Autowired
    private MemberRegisterService memberRegisterService;

    @ApiOperation(value = "获取微信AccessToken")
    @RequestMapping(value = "/getAccessToken", method = RequestMethod.GET)
    public StateResult getAccessToken() {
        ExeResult result = new ExeResult();
        try {
            String token = wxService.getAccessToken();
            Map<String, String> takenData = new HashMap<String, String>();
            takenData.put("token", token);

            result.setSuccess(true);
            result.setData(takenData);
            result.setMessage("成功");
        } catch (Exception e) {
            LoggerManager.error("获取微信AccessToken失败", e);
            result.setSuccess(false);
            result.setMessage("获取微信AccessToken失败");
        }
        return result.getResult();
    }

    @ApiOperation(value = "获取微信Ticket")
    @RequestMapping(value = "/getTicket", method = RequestMethod.GET)
    public StateResult getTicket() {
        ExeResult result = new ExeResult();
        try {
            String ticket = wxService.getTicket(WxConstants.TICKET_TYPE_WXCARD);
            Map<String, String> takenData = new HashMap<String, String>();
            takenData.put("ticket", ticket);

            result.setSuccess(true);
            result.setData(takenData);
            result.setMessage("成功");
        } catch (Exception e) {
            LoggerManager.error("获取微信Ticket失败", e);
            result.setSuccess(false);
            result.setMessage("获取微信Ticket失败");
        }
        return result.getResult();
    }

    @ApiOperation(value = "获取微信JsApi签名")
    @RequestMapping(value = "/getJsApiSign", method = RequestMethod.POST)
    public StateResult getJsApiSign(@RequestBody JsApiSignRequest request) {
        ExeResult result = new ExeResult();
        try {
            Map<String, String> data = wxService.getJsApiSign(request.getUrl());

            result.setSuccess(true);
            result.setData(data);
            result.setMessage("成功");
        } catch (Exception e) {
            LoggerManager.error("获取微信JsApi签名失败", e);
            result.setSuccess(false);
            result.setMessage("获取微信JsApi签名失败");
        }
        return result.getResult();
    }

    @ApiOperation(value = "获取微信卡券Api签名")
    @RequestMapping(value = "/getCardApiSign", method = RequestMethod.POST)
    public StateResult getCardApiSign(@RequestBody CardApiSignRequest request) {
        ExeResult result = new ExeResult();
        try {
            Map<String, String> data = wxService.getCardApiSign(request.getCardId(), request.getCode(), request.getOpenId());

            result.setSuccess(true);
            result.setData(data);
            result.setMessage("成功");
        } catch (Exception e) {
            LoggerManager.error("获取微信卡券Api签名失败", e);
            result.setSuccess(false);
            result.setMessage("获取微信卡券Api签名失败");
        }
        return result.getResult();
    }

    @ApiOperation(value = "获取默认微信卡券Api签名")
    @RequestMapping(value = "/getDefaultCardApiSign", method = { RequestMethod.GET, RequestMethod.POST })
    @Override
    public StateResult getDefaultCardApiSign() {
        ExeResult result = new ExeResult();
        try {
            Map<String, String> data = wxService.getCardApiSign(WxConstants.WX_CARD_ID, null, null);

            result.setSuccess(true);
            result.setData(data);
            result.setMessage("成功");
        } catch (Exception e) {
            LoggerManager.error("获取微信卡券Api签名失败", e);
            result.setSuccess(false);
            result.setMessage("获取微信卡券Api签名失败");
        }
        return result.getResult();
    }

    @ApiOperation(value = "获取微信OpenId")
    @RequestMapping(value = "/getOpenId", method = RequestMethod.GET)
    public StateResult getOpenId(@RequestParam String code) {
        ExeResult result = new ExeResult();
        try {
            if (StringUtil.isNullOrEmpty(code)) {
                throw new RuntimeException("code不允许为空");
            }

            PageAuthResponse response = wxService.getPageAuthInfo(code);
            boolean success = (response.getErrcode() == 0);
            result.setSuccess(success);
            result.setData(response);
            if (success) {
                result.setMessage("成功");
            } else {
                result.setMessage(response.getErrmsg());
            }
        } catch (Exception e) {
            LoggerManager.error("获取微信OpenId失败", e);
            result.setSuccess(false);
            result.setMessage("获取微信OpenId失败");
        }
        return result.getResult();
    }

    @ApiOperation(value = "获取微信小程序OpenId")
    @RequestMapping(value = "/getAppletOpenId", method = RequestMethod.GET)
    @Override
    public StateResult getAppletOpenId(@RequestParam String code) {
        ExeResult result = new ExeResult();
        try {
            if (StringUtil.isNullOrEmpty(code)) {
                throw new RuntimeException("code不允许为空");
            }

            PageAuthResponse response = wxService.getAppletPageAuthInfo(code);
            boolean success = (response.getErrcode() == 0);
            result.setSuccess(success);
            result.setData(response);
            if (success) {
                result.setMessage("成功");
            } else {
                result.setMessage(response.getErrmsg());
            }
        } catch (Exception e) {
            LoggerManager.error("获取微信小程序OpenId失败", e);
            result.setSuccess(false);
            result.setMessage("获取微信小程序OpenId失败");
        }
        return result.getResult();
    }

    @ApiOperation(value = "获取卡激活用户填写的信息")
    @RequestMapping(value = "/getActiveTempInfo", method = RequestMethod.GET)
    @Override
    public Object getActiveTempInfo(String activateTicket) {
        try {
            String result = wxService.getActiveTempInfo(activateTicket);
            return result;
        } catch (Exception e) {
            LoggerManager.error("调用微信API接口getActiveTempInfo失败", e);
            return "";
        }
    }

    @ApiOperation(value = "执行微信API")
    @RequestMapping(value = "/executeWxApi", method = RequestMethod.POST)
    public Object executeWxApi(@RequestBody WxApiParam param) {
        try {
            LoggerManager.debug("appId:" + WxConstants.getWxAppId() + ",secret:" + WxConstants.getWxSecret());
            String result = wxService.executeApi(param.getApiUri(), param.getApiParam());
            return result;
        } catch (Exception e) {
            LoggerManager.error("调用微信API接口失败:" + param.getApiUri(), e);
            return "";
        }
    }

    @ApiOperation(value = "微信消息接收处理")
    @RequestMapping(value = "/wxMessage")
    public Object wxMessage(String signature, String timestamp, String nonce, String echostr, @RequestBody(required = false) String xml) {
        /*String token = "weixinAccessToken";
        if(WxUrlSignUtil.checkSignature(token, signature, timestamp, nonce)){
            return echostr;
        }
        return echostr;*/

        LoggerManager.debug("接收微信XML：" + xml);
        String result = wxService.handleWxMessag(xml);
        LoggerManager.debug("返回微信消息：" + result);
        if (!StringUtil.isNullOrEmpty(result)) {
            return result;
        } else {
            return echostr;
        }
    }

    @ApiOperation(value = "通知微信余额变更")
    @RequestMapping(value = "/noticeBalance", method = RequestMethod.GET)
    public Object noticeBalance(@RequestParam String userId, @RequestParam(defaultValue = "0") double balance) {
        //TODO
        return null;
    }
    
    @ApiOperation(value = "获取微信JsApiAddress签名")
    @RequestMapping(value = "/getJsApiAddressSign", method = RequestMethod.POST)
    public StateResult getJsApiAddressSign(@RequestBody JsapiAddressRequest request) {
        ExeResult result = new ExeResult();
        try {
        	PageAuthResponse authResponse = wxService.getPageAuthInfo(request.getCode());
            Map<String, String> data = wxService.getJsApiAddressSign(authResponse.getAccess_token(), request.getUrl());

            result.setSuccess(true);
            result.setData(data);
            result.setMessage("成功");
        } catch (Exception e) {
            LoggerManager.error("获取微信JsApiAddress签名失败", e);
            result.setSuccess(false);
            result.setMessage("获取微信JsApiAddress签名失败");
        }
        return result.getResult();
    }
}
