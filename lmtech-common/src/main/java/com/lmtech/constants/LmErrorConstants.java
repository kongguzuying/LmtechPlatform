package com.lmtech.constants;

/**
 * 平台错误常量
 * @author huang.jb
 */
public class LmErrorConstants {

    /** 未知错误 **/
    public static final long ERR_UNKNOW = 3600000;
    public static final String ERR_UNKNOW_MSG = "系统繁忙";

    /** 系统参数错误 **/
    public static final long ERR_ARG_ERROR = 3600002;
    public static final String ERR_ARG_ERROR_MSG = "系统参数错误";

    /** 服务调用出现错误 **/
    public static final long ERR_SERVICE_INVOKE = 3600004;
    public static final String ERR_SERVICE_INVOKE_MSG = "服务调用出现错误";
}
