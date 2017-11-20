package com.lmtech.auth.model;

import java.io.Serializable;

/**
 * 凭据校验结果
 * Created by huang.jb on 2017-1-6.
 */
public class TokenValidateResult implements Serializable {
    private int code;
    private String message;
    private boolean validSuccess;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isValidSuccess() {
        return validSuccess;
    }

    public void setValidSuccess(boolean validSuccess) {
        this.validSuccess = validSuccess;
    }
}
