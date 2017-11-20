package com.lmtech.facadeaop.interceptor;

import com.lmtech.exceptions.ErrorCodeException;
import com.lmtech.exceptions.LmExceptionBase;
import com.lmtech.facade.exceptions.RequestValidateException;
import com.lmtech.facade.response.CommonResponse;
import com.lmtech.util.ClassUtil;
import com.lmtech.util.LoggerManager;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 异常拦截器
 * Created by huang.jb on 2017-3-10.
 */
public class ExceptionInterceptor extends InterceptorBase {

    public Object interceptor(ProceedingJoinPoint pjp) throws Throwable {
        LoggerManager.debug("exception interceptor ==> begin");
        try {
            Object object = pjp.proceed();
            return object;
        } catch (IllegalArgumentException e) {
            return returnErrorResult(e, pjp);
        } catch (RequestValidateException e) {
            return returnErrorResult(e, pjp);
        } catch (Exception e) {
            LoggerManager.error(e);
            return returnErrorResult(e, pjp);
        } finally {
            LoggerManager.debug("exception interceptor ==> end");
        }
    }

    private Object returnErrorResult(Exception e, ProceedingJoinPoint pjp) throws Throwable {
        Class<?> returnType = super.getReturnType(pjp);
        Object rtObject = ClassUtil.newInstance(returnType);
        if (rtObject instanceof CommonResponse) {
            CommonResponse errorResponse = (CommonResponse) rtObject;
            errorResponse.setSuccess(false);
            if (e instanceof ErrorCodeException) {
                errorResponse.setCode(((ErrorCodeException)e).getErrorCode());
            }
            if (e instanceof LmExceptionBase || e instanceof IllegalArgumentException) {
                errorResponse.setMessage(e.getMessage());
            } else {
                errorResponse.setMessage("接口调用过程中出现错误，请联系管理员。");
            }
            return errorResponse;
        } else {
            throw e;
        }
    }
}
