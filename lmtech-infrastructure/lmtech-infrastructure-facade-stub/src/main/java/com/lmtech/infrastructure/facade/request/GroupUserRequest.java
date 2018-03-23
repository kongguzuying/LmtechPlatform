package com.lmtech.infrastructure.facade.request;

import com.lmtech.facade.exceptions.RequestValidateException;
import com.lmtech.facade.request.CommonRequest;
import com.lmtech.infrastructure.model.GroupUser;
import com.lmtech.util.StringUtil;

/**
 * 用户分组关联请求
 * Created by huang.jb on 2017-4-1.
 */
public class GroupUserRequest extends CommonRequest<GroupUser> {

    public void validate() {
        GroupUser groupUser = getReqData();
        if (StringUtil.isNullOrEmpty(groupUser.getUserId())) {
            throw new RequestValidateException("传入用户id不允许为空");
        }
        if (StringUtil.isNullOrEmpty(groupUser.getGroupId())) {
            throw new RequestValidateException("传入分组id不允许为空");
        }
    }
}
