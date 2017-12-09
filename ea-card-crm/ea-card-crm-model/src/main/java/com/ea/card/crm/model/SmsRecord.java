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
    @TableField("request_id")
    private String bizId;
    @TableField("request_id")
    private String code;
    @TableField("request_id")
    private String codeRemark;
    @TableField("request_id")
    private String phone;
    @TableField("request_id")
    private String validCode;
    @TableField("request_id")
    private String content;
    @TableField("request_id")
    private String templateCode;
    @TableField("request_id")
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
