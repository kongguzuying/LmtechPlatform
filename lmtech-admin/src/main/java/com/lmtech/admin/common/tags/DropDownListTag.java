package com.lmtech.admin.common.tags;

import com.lmtech.util.ClassUtil;
import com.lmtech.util.CollectionUtil;
import com.lmtech.util.LoggerManager;
import com.lmtech.util.StringUtil;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

/**
 * 下拉列表框标签
 * Created by huang.jb on 2016-8-5.
 */
public class DropDownListTag extends TagSupport {
    private String name;
    private String cssClass;
    private String value;
    private List<?> items;
    private String textField;
    private String valueField;
    private Boolean emptyFirst=false;

    public int doStartTag() throws JspException {
        //TODO 标签内容
        StringBuilder sb = new StringBuilder();
        sb.append("<select");
        sb.append(" id=\"" + id + "\"");
        sb.append(" name=\"" + name + "\"");
        if (!StringUtil.isNullOrEmpty(cssClass)) {
            sb.append(" class=\"" + cssClass + "\"");
        }
        sb.append(">");

        if (!CollectionUtil.isNullOrEmpty(items)) {
        	if(emptyFirst){
        		sb.append("<option value=\"\" ></option>");
        	}
            for (Object item : items) {
                String itemText = "", itemValue = "";
                try {
                    Object itemTextObj = ClassUtil.getFieldValue(item, textField);
                    if (itemTextObj != null) {
                        itemText = String.valueOf(itemTextObj);
                    }

                    Object itemValueObj = ClassUtil.getFieldValue(item, valueField);
                    if (itemValueObj != null) {
                        itemValue = String.valueOf(itemValueObj);
                    }

                    String selected = (!StringUtil.isNullOrEmpty(itemValue) && itemValue.equals(value) ? "selected" : "");
                    sb.append(String.format("<option value=\"%1$s\" %2$s>%3$s</option>", itemValue, selected, itemText));
                } catch (Exception e) {
                    LoggerManager.error(e);
                }
            }
        }

        sb.append("</select>");

        //把生成的HTML输出到响应中
        try {
            pageContext.getOut().println(sb.toString());
        } catch (IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<?> getItems() {
        return items;
    }

    public void setItems(List<?> items) {
        this.items = items;
    }

    public String getTextField() {
        return textField;
    }

    public void setTextField(String textField) {
        this.textField = textField;
    }

    public String getValueField() {
        return valueField;
    }

    public void setValueField(String valueField) {
        this.valueField = valueField;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

	public boolean isEmptyFirst() {
		return emptyFirst;
	}

	public void setEmptyFirst(boolean emptyFirst) {
		this.emptyFirst = emptyFirst;
	}
    
}
