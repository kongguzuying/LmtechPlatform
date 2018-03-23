package com.lmtech.facadeaop.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 拦截器接口
 */
public interface Interceptor {

    void invoke(ProceedingJoinPoint pjp) throws Throwable;

    String getType();

    int getOrder();
}
