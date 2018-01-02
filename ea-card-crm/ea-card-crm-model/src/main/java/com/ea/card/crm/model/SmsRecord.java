package com.ea.card.crm.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lmtech.model.EntityBase;

/**
 * 短信记录表
 * @author huang.jb
 */
@TableName("ea_sms_record")
public class SmsRecord extends EntityBase {
    @TableField("request_id")
    private String requestId;
    @TableField("biz_id")
    private String bizId;
    @TableField("code")
    private String code;
    @TableField("code_remark")
    private String codeRemark;
    @TableField("phone")
    private String phone;
    @TableField("valid_code")
    private String validCode;
    @TableField("content")
    private String content;
    @TableField("template_code")
    private String templateCode;
    @TableField("sign_name")
    private String signName;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeRemark() {
        return codeRemark;
    }

    public void setCodeRemark(String codeRemark) {
        this.codeRemark = codeRemark;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getValidCode() {
        return validCode;
    }

    public void setValidCode(String validCode) {
        this.validCode = validCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }
}
