package com.lmtech.infrastructure.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lmtech.model.DbEntityBase;

/**
 * 页面资源
 * Created by huang.jb on 2016-7-15.
 */
@TableName("lm_resource")
public class Resource extends DbEntityBase {

    public static final String TYPE_BUTTON = "button";

    public static final String DEVICE_WEB = "web";
    public static final String DEVICE_MOBILE = "mobile";
    public static final String DEVICE_ANDROID = "android";
    public static final String DEVICE_IOS = "ios";

    @TableField("NAME")
    private String name;
    @TableField("TYPE")
    private String type;
    @TableField("REMARK")
    private String remark;
    @TableField("MODULECODE")
    private String moduleCode;
    @TableField("MODULENAME")
    private String moduleName;
    @TableField("CATEGORYCODE")
    private String categoryCode;
    @TableField("CATEGORYNAME")
    private String categoryName;
    @TableField("DEVICE")
    private String device;
    @TableField("ENABLE")
    private boolean enable;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
