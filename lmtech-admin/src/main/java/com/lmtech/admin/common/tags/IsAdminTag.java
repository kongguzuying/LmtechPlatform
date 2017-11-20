package com.lmtech.admin.common.tags;

import com.lmtech.admin.common.adaptor.UserAdaptor;
import com.lmtech.util.SpringUtil;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 是否管理权限标签
 * Created by huang.jb on 2016-8-31.
 */
public class IsAdminTag extends TagSupport {
    public int doStartTag() throws JspException {
        UserAdaptor userAdaptor = SpringUtil.getObjectT(UserAdaptor.class);
        boolean isAdmin = userAdaptor.isAdminCurrentUser();
        if (isAdmin) {
            return EVAL_BODY_INCLUDE;
        } else {
            return SKIP_BODY;
        }
    }
}
