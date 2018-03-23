package com.lmtech.infrastructure.facade.dto;

/**
 * 代码项查询参数
 * @author huang.jb
 */
public class CodeItemQueryParam {
    private String itemCode;
    private String typeCode;

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
}
