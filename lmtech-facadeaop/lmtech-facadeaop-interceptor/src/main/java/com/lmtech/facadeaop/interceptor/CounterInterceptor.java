package com.lmtech.facadeaop.interceptor;

import com.lmtech.util.LoggerManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.stereotype.Component;

@Component
public class CounterInterceptor extends InterceptorBase implements Interceptor  {


    @Autowired
    private CounterService counterService;

    @Override
    public void invoke(ProceedingJoinPoint pjp) {
        try {
            //spring boot admin每个请求次数统计
            counterService.increment("meter." + pjp.getSignature().toString());
        } catch (Exception e) {
            LoggerManager.error(e);
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
