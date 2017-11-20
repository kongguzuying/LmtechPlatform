package com.ea.card.crm.service.util;

import com.lmtech.util.LoggerManager;
import com.lmtech.util.StringUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author Administrator
 */
public class WxCardSign {

    public WxCardSign() {
        m_param_to_sign = new ArrayList<String>();
    }

    public void addData(String value) {
        if (!StringUtil.isNullOrEmpty(value)) {
            m_param_to_sign.add(value);
        }
    }

    public void addData(Integer value) {
        m_param_to_sign.add(value.toString());
    }

    public String getSignature() {
        Collections.sort(m_param_to_sign);
        StringBuilder string_to_sign = new StringBuilder();
        for (String str : m_param_to_sign) {
            string_to_sign.append(str);
        }
        LoggerManager.debug("string_to_sign:" + string_to_sign);
        try {
            MessageDigest hasher = MessageDigest.getInstance("SHA-1");
            byte[] digest = hasher.digest(string_to_sign.toString().getBytes());
            return byteToHexString(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String byteToHexString(byte[] data) {
        StringBuilder str = new StringBuilder();
        for (byte b : data) {
            String hv = Integer.toHexString(b & 0xFF);
            if (hv.length() < 2)
                str.append("0");
            str.append(hv);
        }
        return str.toString();
    }

    private ArrayList<String> m_param_to_sign;
}
