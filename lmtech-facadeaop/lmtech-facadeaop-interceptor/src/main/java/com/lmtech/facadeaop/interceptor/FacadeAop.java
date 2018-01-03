package com.lmtech.facadeaop.interceptor;

import com.lmtech.common.ContextManager;
import com.lmtech.common.ExeResult;
import com.lmtech.common.StateResult;
import com.lmtech.constants.LmErrorConstants;
import com.lmtech.exceptions.ErrorCodeException;
import com.lmtech.exceptions.LmExceptionBase;
import com.lmtech.facade.response.CommonResponse;
import com.lmtech.util.ClassUtil;
import com.lmtech.util.JsonUtil;
import com.lmtech.util.LoggerManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Aspect
public class FacadeAop {
    private List<Interceptor> interceptors = new ArrayList<>();

    // 切点表达式
    @Pointcut("execution(public * com..facade..*.*(..))")
    // 切点表达式
    public void pointCut() {
    }

    @Autowired
    private SerialNumberInterceptor serialNumberInterceptor;
    @Autowired
    private CounterInterceptor counterInterceptor;
    @Autowired
    private ValidateInterceptor validateInterceptor;
    @Autowired
    private ExecuteInterceptor excecuteInterceptor;

    @PostConstruct
    public void init() {
        interceptors.add(serialNumberInterceptor);
        interceptors.add(counterInterceptor);
        interceptors.add(validateInterceptor);
        interceptors.add(excecuteInterceptor);

        Collections.sort(interceptors, new Comparator<Interceptor>() {
            @Override
            public int compare(Interceptor o1, Interceptor o2) {
                if (o1.getOrder() > o2.getOrder()) {
                    return 1;
                } else if (o1.getOrder() == o2.getOrder()) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
    }

    @Around("pointCut()")
    public Object invoke(ProceedingJoinPoint pjp) throws Throwable {
        ExeResult result = new ExeResult();

        try {
            for (Interceptor interceptor : interceptors) {
                interceptor.invoke(pjp);
            }

            Object object = ContextManager.getValue("result");
            ContextManager.cleanContext();
            return object;
        } catch (IllegalArgumentException e) {
            buildErrorResult(result);
            result.setErrCode(LmErrorConstants.ERR_ARG_ERROR);
            result.setMessage(e.getMessage());
            printResult(result);
            LoggerManager.warn(e.getMessage());
            return returnErrorResult(result, pjp);
        } catch (MissingServletRequestParameterException e) {
            buildErrorResult(result);
            result.setErrCode(LmErrorConstants.ERR_ARG_ERROR);
            result.setMessage(e.getMessage());
            LoggerManager.warn(e.getMessage());
            printResult(result);
            return returnErrorResult(result, pjp);
        } catch (MethodArgumentNotValidException e) {
            buildErrorResult(result);
            result.setErrCode(LmErrorConstants.ERR_ARG_ERROR);
            List<FieldError> errors = e.getBindingResult().getFieldErrors();
            for (FieldError fieldError : errors) {
                result.setMessage(fieldError.getDefaultMessage());
            }
            LoggerManager.warn(e.getMessage());
            printResult(result);
            return returnErrorResult(result, pjp);
        } catch (ErrorCodeException e) {
            buildErrorResult(result);
            result.setErrCode(e.getErrorCode());
            result.setMessage(e.getMessage());
            LoggerManager.warn(e.getMessage());
            printResult(result);
            return returnErrorResult(result, pjp);
        } catch (Exception e) {
            buildErrorResult(result);
            result.setErrCode(LmErrorConstants.ERR_UNKNOW);
            result.setMessage(LmErrorConstants.ERR_UNKNOW_MSG);
            LoggerManager.error(e);
            printResult(result);
            return returnErrorResult(result, pjp);
        } catch (Throwable e) {
            if (e instanceof Exception) {
                LoggerManager.error((Exception) e);
            } else {
                LoggerManager.error(e.getMessage());
            }
            throw e;
        } finally {
            ContextManager.cleanContext();
        }
    }

    /**
     * 打印返回结果
     *
     * @param object
     */
    public void printResult(Object object) {
        String objectJson = (object != null ? JsonUtil.toJson(object) : "null");
        LoggerManager.debug("返回结果:" + objectJson);
    }

    private void buildErrorResult(ExeResult result) {
        String serialNumber = ContextManager.getValue("serialNumber");
        result.settId(serialNumber);
        result.setSuccess(false);
    }

    private Object returnErrorResult(ExeResult result, ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        Class<?> returnType = method.getReturnType();
        Object rtObject = ClassUtil.newInstance(returnType);
        if (rtObject instanceof CommonResponse) {
            CommonResponse errorResponse = (CommonResponse) rtObject;
            errorResponse.setSuccess(result.isSuccess());
            errorResponse.setMessage(result.getMessage());
            errorResponse.setCode(result.getErrCode());
            errorResponse.setData(result.getData());
            return errorResponse;
        } else if (rtObject instanceof StateResult) {
            return result.getResult();
        } else if (rtObject instanceof ExeResult) {
            return result;
        } else {
            return result;
        }
    }
}
