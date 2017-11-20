package com.lmtech.admin.common.controller;

import com.lmtech.common.ExeResult;
import com.lmtech.util.JsonUtil;
import com.lmtech.util.LoggerManager;
import com.lmtech.util.StringUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * 基础控制器
 * Created by huang.jb on 2016-7-13.
 */
public abstract class ControllerBase {
    protected int pageSize = 10;

    public abstract String getRequestMapping();

    public ModelAndView returnSuccess(HttpServletResponse response) {
        ExeResult result = new ExeResult(true, null, null);
        responseObjectJson(response, result);
        return null;
    }

    public ModelAndView returnSuccess(HttpServletResponse response, String message) {
        ExeResult result = new ExeResult(true, null, message);
        responseObjectJson(response, result);
        return null;
    }

    public ModelAndView returnSuccess(HttpServletResponse response, String message, Object data) {
        ExeResult result = new ExeResult(true, data, message);
        responseObjectJson(response, result);
        return null;
    }
    public ModelAndView returnSuccess(HttpServletResponse response, String message, Object data,String contentType) {
    	ExeResult result = new ExeResult(true, data, message);
    	responseObjectJson(response, result,contentType);
    	return null;
    }

    public ModelAndView returnFailed(HttpServletResponse response) {
        ExeResult result = new ExeResult(false, null, null);
        responseObjectJson(response, result);
        return null;
    }

    public ModelAndView returnFailed(HttpServletResponse response, String message) {
        ExeResult result = new ExeResult(false, null, message);
        responseObjectJson(response, result);
        return null;
    }

    public ModelAndView returnFailed(HttpServletResponse response, String message, Object data) {
        ExeResult result = new ExeResult(false, data, message);
        responseObjectJson(response, result);
        return null;
    }
    
    public ModelAndView returnFailed(HttpServletResponse response, String message, Object data,String contentType) {
    	ExeResult result = new ExeResult(false, data, message);
    	responseObjectJson(response, result,contentType);
    	return null;
    }

    public ModelAndView returnFailed(HttpServletResponse response, String message, Object data, long errCode) {
        ExeResult result = new ExeResult(false, data, message, errCode);
        responseObjectJson(response, result);
        return null;
    }

    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public String getRequestParameter(String paramName) {
        return getRequest().getParameter(paramName);
    }

    public String getArrayStringFromRequest(String paramName) {
        return getArrayStringFromRequest(paramName, ",");
    }

    public String getArrayStringFromRequest(String paramName, String insertStr) {
        if (StringUtil.isNullOrEmpty(paramName)) {
            return null;
        }

        String[] valueArray;
        String strValue = "";
        Map<String, String[]> params = getRequest().getParameterMap();
        Object values = params.get(paramName);
        if (values instanceof String[]) {
            valueArray = (String[]) values;
            if (valueArray != null) {
                for (int i = 0; i < valueArray.length; i++) {
                    if (i > 0) {
                        strValue += insertStr;
                    }
                    strValue += valueArray[i];
                }
            }
        } else {
            strValue = (String) values;
        }
        return strValue;
    }

    protected void responseObjectJson(HttpServletResponse response, Object object) {
        responseObjectJson(response, object, "application/json");
    }
    
    protected void responseObjectJson(HttpServletResponse response, Object object,String contentType) {
    	String jsonStr = JsonUtil.toJson(object);
    	responseText(response, jsonStr, contentType);
    }

    protected void responseText(HttpServletResponse response, String text, String contentType) {
        PrintWriter writer = null;
        try {
            response.setContentType((!StringUtil.isNullOrEmpty(contentType) ? contentType : "text/html"));
            writer = response.getWriter();
            writer.write(text);
            writer.flush();
        } catch (Exception e) {
            LoggerManager.error(e);
        }finally{
            if(null != writer){
                writer.close();
            }
        }
    }
}
