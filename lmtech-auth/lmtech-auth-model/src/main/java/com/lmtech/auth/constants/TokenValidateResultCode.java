package com.lmtech.auth.constants;

/**
 * 凭据验证结果代码常量
 * Created by huang.jb on 2017-1-5.
 */
public class TokenValidateResultCode {
    /**
     * 验证成功
     */
    public static final int SUCCESS = 60000001;
    /**
     * Token过期
     */
    public static final int EXPIRE_TIME = 60000002;
    /**
     * 验证出现错误
     */
    public static final int ERROR = 60000003;
}
