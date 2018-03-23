package com.lmtech.service;
 
/**
 * 加解密接口
 * @author Administrator
 *
 */
public interface Encryption {
	/**
	 * 加密
	 * @param value
	 */
	String encryption(String value);
	/**
	 * 带密匙的加密
	 * @param value
	 * @param key
	 */
	String encryption(String value, String key);
	/**
	 * 解密
	 * @param encryptionStr
	 */
	String decryption(String encryptionStr);
	/**
	 * 带密匙的解密
	 * @param encryptionStr
	 * @param key
	 */
	String decription(String encryptionStr, String key);
}
