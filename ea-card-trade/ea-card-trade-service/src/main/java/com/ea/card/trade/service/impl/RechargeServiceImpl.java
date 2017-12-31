package com.ea.card.trade.service.impl;

import com.ea.card.trade.service.RechargeService;
import com.ea.card.trade.util.WechatUtil;
import com.ea.card.trade.vo.UnifiedOrderData;
import com.ea.card.trade.vo.UnifiedOrderParam;
import com.lmtech.util.IdWorkerUtil;
import com.lmtech.util.LoggerManager;
import com.lmtech.util.StringUtil;
import com.lmtech.util.XmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.SortedMap;
import java.util.TreeMap;

@Service
public class RechargeServiceImpl implements RechargeService {

    private String UNIFIEDORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    @Value("${trade.pay_api_key}")
    private String payApiKey;   //支付apikey
    @Value("${trade.app_id}")
    private String appId;   //公众账号ID
    @Value("${trade.mch_id}")
    private String mchId;   //商户号
    @Value("${trade.body}")
    private String body;
    @Value("${trade.spbill_create_ip}")
    private String spbillCreateIp;
    @Value("${trade.notify_url}")
    private String notifyUrl;
    @Value("${trade.trade_type}")
    private String tradeType;

    @Autowired
    private RestTemplate restTemplate;

    public String recharge(UnifiedOrderParam param) {
        //TODO 参数校验

        //支付签名
        String nonceStr = IdWorkerUtil.generateStringId();
        SortedMap<String, String> signParam = new TreeMap<String, String>();
        signParam.put("appid", appId);
        signParam.put("mch_id", mchId);
        signParam.put("device_info", param.getDevice_info());
        signParam.put("body", body);
        signParam.put("nonce_str", nonceStr);
        String sign = WechatUtil.createSign(payApiKey, signParam);

        //公共参数填充
        param.setAppid(appId);
        param.setMch_id(mchId);
        param.setNonce_str(nonceStr);
        param.setSign(sign);
        param.setBody(body);
        param.setSpbill_create_ip(spbillCreateIp);
        param.setNotify_url(notifyUrl);
        param.setTrade_type(tradeType);

        //调用下单接口
        String xml = XmlUtil.toXml(param);
        String result = restTemplate.postForObject(UNIFIEDORDER_URL, xml, String.class);
        if (StringUtil.isNullOrEmpty(result)) {
            throw new RuntimeException("调用下单出现未知错误");
        }
        UnifiedOrderData unifiedOrderData = XmlUtil.fromXml(result);
        if ("SUCCESS".equals(unifiedOrderData.getResult_code())) {
            return unifiedOrderData.getPrepay_id();
        } else {
            LoggerManager.error(unifiedOrderData.getReturn_msg() + ",xml:" + result);
            throw new RuntimeException("充值下单失败");
        }
    }
}
