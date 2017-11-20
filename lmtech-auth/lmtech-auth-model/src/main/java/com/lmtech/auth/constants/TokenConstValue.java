package com.lmtech.auth.constants;

/**
 * Token相关常量
 * Created by huang.jb on 2017-1-12.
 */
public class TokenConstValue {

    public static final String REDIS_PREFIX = "lmtech:auth:token:";//Redis存储前缀

    public static final int EXPIRE_SECONDS = 60 * 30;//过期时间30分钟
}
