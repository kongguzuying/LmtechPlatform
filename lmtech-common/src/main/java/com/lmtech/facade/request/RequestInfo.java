package com.lmtech.facade.request;

import java.io.Serializable;

/**
 * 请求信息
 * Created by huang.jb on 2017-1-11.
 */
public class RequestInfo implements Serializable {
    private String osType;          //操作系统类型，Android,iOS,Server
    private String osVersion;       //操作系统版本
    private String sysVersion;      //调用方系统版本

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getSysVersion() {
        return sysVersion;
    }

    public void setSysVersion(String sysVersion) {
        this.sysVersion = sysVersion;
    }
}
