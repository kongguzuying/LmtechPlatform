package com.lmtech.admin.common.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 页面资源权限标签
 * Created by huang.jb on 2016-7-15.
 */
public class PageResourcePermissionTag extends TagSupport {

    private String resId;   //资源编号

    @Override
    public int doStartTag() throws JspException {
        /*PageResourceService pageResourceService = SpringContextHolder.getBean(PageResourceService.class);

        if (pageResourceService.hasPermissionCurrentUser(resId)) {
            return EVAL_BODY_INCLUDE;
        } else {
            return SKIP_BODY;
        }*/
        return EVAL_BODY_INCLUDE;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }
}
