package com.lmtech.service.support;

import com.lmtech.service.Encryption;
import com.lmtech.util.LoggerManager;
import com.lmtech.util.MD5Util;
 
public class MD5EncryptionImpl implements Encryption {

	@Override
	public String decription(String encryptionStr, String key) {
		return null;
	}

	@Override
	public String decryption(String encryptionStr) {
		return null;
	}

	@Override
	public String encryption(String value) {
		try {
            return MD5Util.getMD5String(value);
		} catch (Exception e) {
			LoggerManager.error("MD5加密过程中出现未知错误", e);
			return null;
		}
	}

	@Override
	public String encryption(String value, String key) {
		return null;
	}
}
