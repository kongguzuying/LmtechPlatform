package com.lmtech.admin.common.tags;

import com.lmtech.admin.common.adaptor.CodeAdaptor;
import com.lmtech.infrastructure.model.CodeItem;
import com.lmtech.util.SpringUtil;
import com.lmtech.util.StringUtil;

import javax.servlet.jsp.JspException;
import java.util.List;

/**
 * 代码表列表标签
 * Created by huang.jb on 2016-8-11.
 */
public class CodeDropDownListTag extends DropDownListTag {
    private String codeType;

    public CodeDropDownListTag() {
        super();
        super.setTextField("name");
        super.setValueField("value");
    }

    @Override
    public int doStartTag() throws JspException {
        if (!StringUtil.isNullOrEmpty(codeType)) {
            CodeAdaptor codeAdaptor = SpringUtil.getObjectT(CodeAdaptor.class);
            List<CodeItem> codeItems = codeAdaptor.getCodeItemOfType(codeType);
            super.setItems(codeItems);
        }
        return super.doStartTag();
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }
}
