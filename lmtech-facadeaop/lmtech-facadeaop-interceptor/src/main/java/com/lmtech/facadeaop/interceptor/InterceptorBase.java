package com.lmtech.facadeaop.interceptor;

import com.lmtech.facade.request.CommonRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * 拦截器基类
 * Created by huang.jb on 2017-2-22.
 */
public abstract class InterceptorBase {

    public static int order_high = 1;
    public static int order_middle = 5;
    public static int order_low = 10;

    public int order = 10;

    /**
     * 获取请求参数
     * @param pjp
     * @return
     */
    protected CommonRequest getAuthRequestArg(ProceedingJoinPoint pjp) {
        Object[] args = pjp.getArgs();
        Object requestArg = null;
        if (args != null && args.length > 0) {
            requestArg = args[0];
        }
        if (requestArg != null && requestArg instanceof CommonRequest) {
            return (CommonRequest) requestArg;
        } else {
            return null;
        }
    }

    /**
     * 获取方法
     * @param pjp
     * @return
     */
    protected Method getMethod(ProceedingJoinPoint pjp) {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        return methodSignature.getMethod();
    }

    /**
     * 获取返回值类型
     * @param pjp
     * @return
     */
    protected Class<?> getReturnType(ProceedingJoinPoint pjp) {
        Method method = this.getMethod(pjp);
        if (method != null) {
            return method.getReturnType();
        } else {
            return null;
        }
    }
}
