package com.lmtech.infrastructure.facade.request;

import com.lmtech.facade.exceptions.RequestValidateException;
import com.lmtech.facade.request.CommonRequest;
import com.lmtech.util.StringUtil;

/**
 * 修改分组请求
 * Created by huang.jb on 2017-4-1.
 */
public class ChangeGroupRequest extends CommonRequest {
    private String userId;
    private String oldGrouopId;
    private String newGroupId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOldGrouopId() {
        return oldGrouopId;
    }

    public void setOldGrouopId(String oldGrouopId) {
        this.oldGrouopId = oldGrouopId;
    }

    public String getNewGroupId() {
        return newGroupId;
    }

    public void setNewGroupId(String newGroupId) {
        this.newGroupId = newGroupId;
    }

    public void validate() {
        if (StringUtil.isNullOrEmpty(userId)) {
            throw new RequestValidateException("传入用户id不允许为空");
        }
        if (StringUtil.isNullOrEmpty(oldGrouopId)) {
            throw new RequestValidateException("传入旧的分组id不允许为空");
        }
        if (StringUtil.isNullOrEmpty(newGroupId)) {
            throw new RequestValidateException("传入新的分组id不允许为空");
        }
    }
}
