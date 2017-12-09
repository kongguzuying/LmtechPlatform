package com.ea.card.crm.service.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.ea.card.crm.model.SmsRecord;
import com.ea.card.crm.service.AliyunMessageService;
import com.ea.card.crm.service.exception.SendSmsException;
import com.lmtech.common.ExeResult;
import com.lmtech.redis.service.RedisDataService;
import com.lmtech.util.DateUtil;
import com.lmtech.util.IdWorkerUtil;
import com.lmtech.util.JsonUtil;
import com.lmtech.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

@Service
@RefreshScope
public class AliyunMessageServiceImpl implements AliyunMessageService {

    private String product = "Dysmsapi";    //产品名称:云通信短信API产品
    private String domain = "dysmsapi.aliyuncs.com";    //产品域名
    private String cacheTableName = "sms_record";   //缓存表前缀

    @Value("${sms.expire_time}")
    private long smsExpireTime;     //验证码过期时间(秒)
    @Value("${sms.access_key_id}")
    private String accessKeyId;     //accessKey
    @Value("${sms.access_key_secret}")
    private String accessKeySecret; //accessSecret
    @Value("${sms.sign_name}")
    private String signName;        //签名
    @Value("${sms.template_code}")
    private String templateCode;    //短信模板code

    @Autowired
    private RedisDataService redisDataService;

    @Override
    public ExeResult sendSmsValidCode(String phone, String validCode) {
        try {
            //TODO 数据入库
            SmsRecord smsRecord = new SmsRecord();

            System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
            System.setProperty("sun.net.client.defaultReadTimeout", "10000");

            //初始化acsClient,暂不支持region化
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);

            //组装请求对象-具体描述见控制台-文档部分内容
            SendSmsRequest request = new SendSmsRequest();
            //必填:待发送手机号
            request.setPhoneNumbers(phone);
            //必填:短信签名-可在短信控制台中找到
            request.setSignName(signName);
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(templateCode);
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            request.setTemplateParam("{\"code\":\"" + validCode + "\"}");

            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

            ExeResult result = new ExeResult();
            if ("OK".equalsIgnoreCase(sendSmsResponse.getCode())) {
                String id = IdWorkerUtil.generateStringId();
                String key = cacheTableName + ":" + id;
                Map<String, String> data = new HashMap<>();
                data.put("validCode", validCode);
                data.put("phone", phone);
                boolean setKeyResult = redisDataService.setKey(key, JsonUtil.toJson(data), smsExpireTime);

                if (setKeyResult) {
                    result.setSuccess(true);
                    result.setData(id);
                } else {
                    result.setSuccess(false);
                    result.setMessage("短信写入失败");
                }
            } else {
                result.setSuccess(false);
                result.setMessage("短信发送失败");
            }
            return result;
        } catch (Exception e) {
            throw new SendSmsException();
        }
    }

    public ExeResult checkSmsValidCode(String validId, String validCode) {
        if (StringUtil.isNullOrEmpty(validId)) {
            throw new IllegalArgumentException("传入validId不允许为空");
        }
        if (StringUtil.isNullOrEmpty(validCode)) {
            throw new IllegalArgumentException("传入validCode不允许为空");
        }

        ExeResult result = new ExeResult();
        String key = cacheTableName + ":" + validId;
        String value = redisDataService.getKey(key);
        Map<String, String> data = (Map<String, String>) JsonUtil.fromJson(value, Map.class);
        if (data != null) {
            String cacheValidCode = data.get("validCode");
            if (validCode.equalsIgnoreCase(cacheValidCode)) {
                result.setSuccess(true);
            } else {
                result.setSuccess(false);
                result.setMessage("无效验证码");
            }
        } else {
            result.setSuccess(false);
            result.setMessage("验证码无效或已过期，请重新发送");
        }

        return result;
    }
}
