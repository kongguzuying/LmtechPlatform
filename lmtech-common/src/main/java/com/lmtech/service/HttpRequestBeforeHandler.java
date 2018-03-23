package com.lmtech.service;

import java.util.Map;

import com.lmtech.common.PlatformService;

/**
 * http request before handler
 * @author huangjb
 *
 */
public interface HttpRequestBeforeHandler extends PlatformService {
	/**
	 * handle
	 * @param url
	 * @param context
	 */
	void handle(String url, Map<String, Object> context);
}
