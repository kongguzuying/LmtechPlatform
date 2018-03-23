package com.lmtech.admin.common.constants;

/**
 * Created by Administrator on 2015/10/15 0015.
 */
public enum CommonResultCode implements ResultCode {
    /** 操作成功 */
    SUCCESS("SUCCESS", 0,"操作成功"),
    
    /**操作失败标示**/
    FAIL("FAIL",-1,"操作失败");



    private String errorMsg;

    private String errorCode;

    private int    statusCode;

    private CommonResultCode(String errorCode, int statusCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.statusCode = statusCode;
    }
    private CommonResultCode(int statusCode, String errorMsg) {
        this.errorMsg = errorMsg;
        this.statusCode = statusCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
