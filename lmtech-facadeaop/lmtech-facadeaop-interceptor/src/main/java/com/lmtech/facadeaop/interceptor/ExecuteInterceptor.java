package com.lmtech.facadeaop.interceptor;

import com.lmtech.common.ContextManager;
import com.lmtech.common.ExeResult;
import com.lmtech.common.StateResult;
import com.lmtech.facade.request.CommonRequest;
import com.lmtech.facade.response.CommonResponse;
import com.lmtech.util.JsonUtil;
import com.lmtech.util.LoggerManager;
import com.lmtech.util.StringUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.swing.plaf.nimbus.State;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 打印请求参数
 */
@Component
public class ExecuteInterceptor extends InterceptorBase implements Interceptor {

    @Override
    public void invoke(ProceedingJoinPoint pjp) throws Throwable {
        //打印请求参数
        printParam(pjp);

        //执行业务
        Object object = pjp.proceed();

        //返回业务流水号
        String serialNumber = ContextManager.getValue("serialNumber");
        if (object instanceof StateResult) {
            ((StateResult) object).settId(serialNumber);
        } else if (object instanceof ExeResult) {
            ((ExeResult) object).settId(serialNumber);
        } else if (object instanceof CommonResponse) {
            ((CommonResponse) object).setTid(serialNumber);
        }

        ContextManager.setValue("result", object);

        //打印返回结果
        printResult(object);
    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public int getOrder() {
        return order_middle;
    }

    /**
     * 打印请求参数
     * @param pjp
     */
    public void printParam(ProceedingJoinPoint pjp) {
        String method = pjp.getTarget().getClass().getName() + "." + pjp.getSignature().getName();
        Object[] args = pjp.getArgs();
        List<Object> argList = new ArrayList<>();
        if (args != null && args.length > 0) {
            for (Object arg : args) {
                if (arg instanceof ServletRequest || arg instanceof ServletResponse) {
                    //排队request,response参数
                    continue;
                } else {
                    argList.add(arg);
                }
            }
        }

        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        if (ra != null) {
            ServletRequestAttributes sra = (ServletRequestAttributes) ra;
            HttpServletRequest request = sra.getRequest();
            String appType = request.getHeader("AppType");
            if (!StringUtil.isNullOrEmpty(appType)) {
                ContextManager.setValue("appType", appType);
            }

            List<String> headers = new ArrayList<>();
            Enumeration<String> headerNameElems = request.getHeaderNames();
            while (headerNameElems.hasMoreElements()) {
                String headerName = headerNameElems.nextElement();
                headers.add(headerName + ":" + request.getHeader(headerName));
            }
            LoggerManager.debug("请求URL:" + request.getRequestURI() + ",Headers:" + JsonUtil.toJson(headers));
        }

        LoggerManager.debug("访问入口:" + method + ",传入参数:" + JsonUtil.toJson(argList));
    }


    /**
     * 打印返回结果
     * @param object
     */
    public void printResult(Object object) {
        String objectJson = (object != null ? JsonUtil.toJson(object) : "null");
        LoggerManager.debug("返回结果:" + objectJson);
    }
}
