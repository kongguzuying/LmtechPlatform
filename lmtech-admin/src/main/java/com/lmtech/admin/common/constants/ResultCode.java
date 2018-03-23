package com.lmtech.admin.common.constants;

/**
 * Created by Administrator on 2015/10/15 0015.
 */
public interface ResultCode {

    /**
     * 获取错误码
     * <p>不能返回<code>NULL</code></p>
     *
     * @return 错误码
     */
    public String getErrorCode();

    /**
     * 获取状态码
     * <p>状态码应该是经过约定的</p>
     *
     * @return 状态码
     */
    public int getStatusCode();

    public String getErrorMsg();
}
