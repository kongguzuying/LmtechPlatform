package com.lmtech.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * URL工具类
 * @author huang.jb
 *
 */
public class UrlUtil {
	/**
	 * URL编码
	 * @param url
	 * @return
	 */
	public static String encodeUrl(String url) {
		if (StringUtil.isNullOrEmpty(url)) {
			throw new IllegalArgumentException("URL地址不允许为空。");
		}
		
		try {
			return URLEncoder.encode(url, "UTF8");
		} catch (UnsupportedEncodingException e) {
			LoggerManager.error(e);
			return url;
		}
	}
	/**
	 * URL解码
	 * @param url
	 * @return
	 */
	public static String decodeUrl(String url) {
		if (StringUtil.isNullOrEmpty(url)) {
			throw new IllegalArgumentException("URL地址不允许为空。");
		}
		
		try {
			return URLDecoder.decode(url, "UTF8");
		} catch (UnsupportedEncodingException e) {
			LoggerManager.error(e);
			return url;
		}
	}
}
