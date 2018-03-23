package com.ea.card.trade.util;

import com.google.common.collect.Maps;
import com.lmtech.util.MD5Util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by Administrator on 2015/10/10 0010.
 */
public class WechatUtil {


    private final static String baseChars = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static SortedMap<String, String> sortMap(Object o) {
        SortedMap<String, String> parameters = Maps.newTreeMap();
        Field[] fields = o.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            if (null != WechatUtil.getFieldValueByName(fields[i].getName(), o)) {

                parameters.put(fields[i].getName(), WechatUtil.getFieldValueByName(fields[i].getName(),
                        o) + "");
            }
        }
        return parameters;
    }

    public static String createNoncestr(int length) {
        if (length <= 0) {
            return "";
        }
        String res = "";
        for (int i = 0; i < length; i++) {
            Random rd = new Random();
            res += baseChars.charAt(rd.nextInt(baseChars.length() - 1));
        }
        return res;
    }

    public static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[]{});
            Object value = method.invoke(o, new Object[]{});
            return value;
        } catch (Exception e) {
            return null;
        }
    }

    public static String createSign(String wxPayApiKey, SortedMap<String, String> parameters) {
        return createSign("UTF-8", parameters, wxPayApiKey);
    }

    private static String createSign(String charSet,SortedMap<String,String> parameters,String apiKey){
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            if(null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + apiKey);
        String sign = MD5Util.getMD5String(sb.toString()).toUpperCase();

        return sign;
    }


}
