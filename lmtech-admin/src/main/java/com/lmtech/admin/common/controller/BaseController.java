package com.lmtech.admin.common.controller;

import com.alibaba.fastjson.JSON;
import com.lmtech.admin.common.constants.Constant;
import com.lmtech.util.LoggerManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;


public class BaseController {

	protected HttpServletRequest request;
	protected HttpServletResponse response;

	@SuppressWarnings("rawtypes")
	public void out(HttpServletResponse response, Map _result) {
		try {
			response.setContentType("text/plain");
			response.setHeader("Cache-Control", "no-cache");
			response.setCharacterEncoding(Constant.UTF8);
			PrintWriter writer = response.getWriter();
			writer.print(JSON.toJSONString(_result));
		} catch (Exception e) {
			LoggerManager.error("response out exception",e);
		}
	}

}