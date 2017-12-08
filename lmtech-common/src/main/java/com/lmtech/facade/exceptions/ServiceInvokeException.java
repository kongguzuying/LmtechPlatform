package com.lmtech.facade.exceptions;

import com.lmtech.constants.LmErrorConstants;
import com.lmtech.exceptions.ErrorCodeException;

/**
 * 服务调用过程出现异常
 * @author huang.jb
 */
public class ServiceInvokeException extends ErrorCodeException {

    private String serviceName;
    private String methodName;

    public ServiceInvokeException(String serviceName, String methodName) {
        super(LmErrorConstants.ERR_SERVICE_INVOKE + ",serviceName:" + serviceName + ",methodName:" + methodName, LmErrorConstants.ERR_SERVICE_INVOKE);
        this.serviceName = serviceName;
        this.methodName = methodName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
