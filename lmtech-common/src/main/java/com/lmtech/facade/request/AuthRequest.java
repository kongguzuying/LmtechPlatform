package com.lmtech.facade.request;

import java.io.Serializable;

/**
 * 服务带权限认证控制的请求
 * Created by huang.jb on 2017-1-11.
 */
public abstract class AuthRequest<T> extends CommonRequest<T> implements Serializable {
    private String token;           //Token

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
