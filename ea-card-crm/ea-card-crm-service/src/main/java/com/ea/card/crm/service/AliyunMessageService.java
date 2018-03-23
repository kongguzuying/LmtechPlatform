package com.ea.card.crm.service;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.lmtech.common.ExeResult;

/**
 * 阿里云消息服务
 * @author huang.jb
 */
public interface AliyunMessageService {
    /**
     * 发送短信验证码
     * @param phone
     * @param validCode
     * @return
     */
    ExeResult sendSmsValidCode(String phone, String validCode);

    /**
     * 校验短信验证码
     * @param validId
     * @param validCode
     * @return
     */
    ExeResult checkSmsValidCode(String validId, String validCode);
}
