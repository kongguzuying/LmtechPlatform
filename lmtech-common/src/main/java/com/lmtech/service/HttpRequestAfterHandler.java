package com.lmtech.service;

import com.lmtech.common.PlatformService;
import com.lmtech.model.HttpResult;

/**
 * http request after handler
 * @author huangjb
 *
 */
public interface HttpRequestAfterHandler extends PlatformService {
	/**
	 * handle
	 * @param url
	 * @param httpResult
	 */
	void handle(String url, HttpResult httpResult);
}
