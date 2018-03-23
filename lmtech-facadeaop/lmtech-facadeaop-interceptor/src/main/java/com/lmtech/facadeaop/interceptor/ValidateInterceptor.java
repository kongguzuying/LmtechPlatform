package com.lmtech.facadeaop.interceptor;

import com.lmtech.facade.request.CommonRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

/**
 * 校验拦截器
 * Created by huang.jb on 2017-3-30.
 */
@Component
public class ValidateInterceptor extends InterceptorBase implements Interceptor {

    @Override
    public void invoke(ProceedingJoinPoint pjp) throws Throwable {
        CommonRequest request = super.getAuthRequestArg(pjp);

        if (request != null) {
            request.validate();
        }
    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public int getOrder() {
        return order_high;
    }
}
