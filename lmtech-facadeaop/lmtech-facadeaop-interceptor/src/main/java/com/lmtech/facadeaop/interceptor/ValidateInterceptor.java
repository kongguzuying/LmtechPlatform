package com.lmtech.facadeaop.interceptor;

import com.lmtech.facade.request.CommonRequest;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 校验拦截器
 * Created by huang.jb on 2017-3-30.
 */
public class ValidateInterceptor extends InterceptorBase {
    public Object interceptor(ProceedingJoinPoint pjp) throws Throwable {
        CommonRequest request = super.getAuthRequestArg(pjp);

        if (request != null) {
            request.validate();
        }

        Object data = pjp.proceed();
        return data;
    }
}
