package com.lmtech.facadeaop.interceptor;

import com.lmtech.common.ContextManager;
import com.lmtech.common.ExeResult;
import com.lmtech.constants.LmErrorConstants;
import com.lmtech.exceptions.ErrorCodeException;
import com.lmtech.util.JsonUtil;
import com.lmtech.util.LoggerManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import javax.annotation.PostConstruct;
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
    private ExecuteInterceptor excecuteInterceptor;

    @PostConstruct
    public void init() {
        interceptors.add(serialNumberInterceptor);
        interceptors.add(counterInterceptor);
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
            return object;
        } catch (IllegalArgumentException e) {
            buildErrorResult(result);
            result.setErrCode(LmErrorConstants.ERR_ARG_ERROR);
            result.setMessage(e.getMessage());
            printResult(result);
            LoggerManager.warn(e.getMessage());
            return result.getResult();
        } catch (MissingServletRequestParameterException e) {
            buildErrorResult(result);
            result.setErrCode(LmErrorConstants.ERR_ARG_ERROR);
            result.setMessage(e.getMessage());
            LoggerManager.warn(e.getMessage());
            printResult(result);
            return result.getResult();
        } catch (MethodArgumentNotValidException e) {
            buildErrorResult(result);
            result.setErrCode(LmErrorConstants.ERR_ARG_ERROR);
            List<FieldError> errors = e.getBindingResult().getFieldErrors();
            for (FieldError fieldError : errors) {
                result.setMessage(fieldError.getDefaultMessage());
            }
            LoggerManager.warn(e.getMessage());
            printResult(result);
            return result.getResult();
        } catch (ErrorCodeException e) {
            buildErrorResult(result);
            result.setErrCode(e.getErrorCode());
            result.setMessage(e.getMessage());
            LoggerManager.warn(e.getMessage());
            printResult(result);
            return result.getResult();
        } catch (Exception e) {
            buildErrorResult(result);
            result.setErrCode(LmErrorConstants.ERR_UNKNOW);
            result.setMessage(LmErrorConstants.ERR_UNKNOW_MSG);
            LoggerManager.error(e);
            printResult(result);
            return result.getResult();
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
}
