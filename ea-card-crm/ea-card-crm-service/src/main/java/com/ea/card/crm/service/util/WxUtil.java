package com.ea.card.crm.service.util;

import com.lmtech.common.ContextManager;
import com.lmtech.util.StringUtil;

/**
 * 微信工具类
 */
public class WxUtil {
    /**
     * 是否小程序请求
     * @return
     */
    public static boolean isAppletRequest() {
        String appType = ContextManager.getValue("appType");
        if (StringUtil.isNullOrEmpty(appType)) {
            return false;
        } else {
            return appType.equalsIgnoreCase("wxApplet");
        }
    }
}
