package com.lmtech.auth.constants;

public class AuthErrorConstants {
    /** Token过期错误 **/
    public static final long ERR_TOKEN_EXPIRE_TIME = 60000002;
    public static final String ERR_TOKEN_EXPIRE_TIME_MSG = "Token过期错误";

    /** Token校验错误 **/
    public static final long ERR_TOKEN_VALIDATE = 60000004;
    public static final String ERR_TOKEN_VALIDATE_MSG = "Token校验错误";
}
