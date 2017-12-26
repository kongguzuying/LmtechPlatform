package com.lmtech.admin.common.interceptors;

import com.lmtech.common.ContextManager;
import com.lmtech.util.ClassUtil;
import feign.Target;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

import java.lang.reflect.Proxy;

/**
 * 服务适配器拦截
 * @author huang.jb
 */
@Service
@Aspect
public class ServiceAdaptorInterceptor {
    // 切点表达式
    @Pointcut("execution(public * com..facade.stub..*.*(..))")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object invoke(ProceedingJoinPoint pjp) throws Throwable {
        Proxy proxy = (Proxy) pjp.getTarget();
        Object h = ClassUtil.getFieldValue(proxy, "h");
        Target target = (Target) ClassUtil.getFieldValue(h, "target");

        try {
            ContextManager.setValue("service_name", target.name());
            ContextManager.setValue("service_url", target.url());
            return pjp.proceed();
        } finally {
            ContextManager.setValue("service_url", "");
        }
    }
}
