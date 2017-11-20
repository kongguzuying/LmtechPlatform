package com.ea.card.crm.facade.stub;

import com.ea.card.crm.facade.request.CardApiSignRequest;
import com.ea.card.crm.facade.request.JsApiSignRequest;
import com.ea.card.crm.facade.request.WxApiParam;
import com.lmtech.common.StateResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 微信Token接口
 */
@FeignClient("ea-card-crm")
@RequestMapping(value = "/wxtoken")
public interface WxTokenFacade {
    /**
     * 获取微信AccessToken
     * @return
     */
    @RequestMapping(value = "/getAccessToken", method = RequestMethod.GET)
    StateResult getAccessToken();

    /**
     * 获取微信Ticket
     * @return
     */
    @RequestMapping(value = "/getTicket", method = RequestMethod.GET)
    StateResult getTicket();

    /**
     * 获取微信JsApi签名
     * @param request
     * @return
     */
    @RequestMapping(value = "/getJsApiSign", method = RequestMethod.POST)
    StateResult getJsApiSign(@RequestBody JsApiSignRequest request);

    /**
     * 获取微信卡券Api签名
     * @param request
     * @return
     */
    @RequestMapping(value = "/getCardApiSign", method = RequestMethod.POST)
    StateResult getCardApiSign(@RequestBody CardApiSignRequest request);

    /**
     * 获取微信卡券Api签名
     * @return
     */
    @RequestMapping(value = "/getDefaultCardApiSign", method = { RequestMethod.GET })
    StateResult getDefaultCardApiSign();

    /**
     * 获取微信OpenId
     * @param code
     * @return
     */
    @RequestMapping(value = "/getOpenId", method = RequestMethod.GET)
    StateResult getOpenId(@RequestParam("code") String code);

    /**
     * 获取微信OpenId
     * @param code
     * @return
     */
    @RequestMapping(value = "/getAppletOpenId", method = RequestMethod.GET)
    StateResult getAppletOpenId(@RequestParam("code") String code);

    /**
     * 获取卡激活用户填写的信息
     * @param activateTicket
     * @return
     */
    @RequestMapping(value = "/getActiveTempInfo", method = RequestMethod.GET)
    Object getActiveTempInfo(@RequestParam("activateTicket") String activateTicket);

    /**
     * 执行微信API
     * @param param
     * @return
     */
    @RequestMapping(value = "/executeWxApi", method = RequestMethod.POST)
    Object executeWxApi(@RequestBody WxApiParam param);

    /**
     * 微信消息接收处理
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @param xml
     * @return
     */
    @RequestMapping(value = "/wxMessage")
    Object wxMessage(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp, @RequestParam("nonce") String nonce, @RequestParam("echostr") String echostr, @RequestBody String xml);

    /**
     * 通知微信余额变更
     * @param userId
     * @param balance
     * @return
     */
    @RequestMapping(value = "/noticeBalance", method = RequestMethod.GET)
    Object noticeBalance(@RequestParam("userId") String userId, @RequestParam("balance") double balance);
}
