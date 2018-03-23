package com.lmtech.admin.common.tags;

import com.lmtech.admin.common.adaptor.CodeAdaptor;
import com.lmtech.util.SpringUtil;
import com.lmtech.util.StringUtil;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * 代码展示Tag
 * Created by huang.jb on 2016-8-23.
 */
public class CodeTextTag extends TagSupport {
    private String codeType;
    private String itemValue;
    private String emptyName = "";

    public int doStartTag() throws JspException {
        StringBuilder sb = new StringBuilder();
        CodeAdaptor codeAdaptor = (CodeAdaptor) SpringUtil.getObject(CodeAdaptor.class);
        String itemName = codeAdaptor.getCodeItemText(codeType, itemValue);
        if (!StringUtil.isNullOrEmpty(itemName)) {
            sb.append(itemName);
        }

        //把生成的HTML输出到响应中
        try {
            String sbStr = sb.toString();
            if (StringUtil.isNullOrEmpty(sbStr)) {
                sbStr = emptyName;
            }
            pageContext.getOut().println(sbStr);
        } catch (IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public void setEmptyName(String emptyName) {
        this.emptyName = emptyName;
    }
}
