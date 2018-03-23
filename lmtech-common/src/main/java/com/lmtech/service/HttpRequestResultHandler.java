package com.lmtech.service;

import com.lmtech.common.PlatformService;
import com.lmtech.model.HttpResult;

/**
 * http request result handler
 * @author huangjb
 *
 */
public interface HttpRequestResultHandler extends PlatformService {
	/**
	 * handle
	 * @param url
	 * @param httpResult
	 * @return
	 */
	HttpResult handle(String url, HttpResult httpResult);
}
