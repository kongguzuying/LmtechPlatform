package com.lmtech.scheduler.config;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lmtech.model.ConfigEntityBase;
import com.lmtech.util.StringUtil;

/**
 * 调度组件配置
 *
 * @author huang.jb
 */
@TableName("lm_sche_comp_config")
public class SchedulerCompConfig extends ConfigEntityBase {
    public static final int COMPTYPE_SPRING_BEAN = 1;
    public static final int COMPTYPE_SERVICE_HOST = 2;

    @TableField("comp_type")
    private int compType;               //组件类型
    @TableField("bean_name")
    private String beanName;            //Spring容器中的bean
    @TableField("component_class")
    private String componentClass;      //组件类，用于动态生成实例
    @TableField("invoke_method")
    private String invokeMethod;        //调度组件执行方法
    @TableField("service_host")
    private String serviceHost;         //服务host
    @TableField("method_url")
    private String methodUrl;           //方法url
    @TableField("method_type")
    private String methodType;          //方法类型 get|post
    @TableField("description")
    private String description;         //描述

    public int getCompType() {
        return compType;
    }

    public void setCompType(int compType) {
        this.compType = compType;
    }

    public String getComponentClass() {
        return componentClass;
    }

    public void setComponentClass(String componentClass) {
        this.componentClass = componentClass;
    }

    public String getInvokeMethod() {
        return invokeMethod;
    }

    public void setInvokeMethod(String invokeMethod) {
        this.invokeMethod = invokeMethod;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getServiceHost() {
        return serviceHost;
    }

    public void setServiceHost(String serviceHost) {
        this.serviceHost = serviceHost;
    }

    public String getMethodUrl() {
        return methodUrl;
    }

    public void setMethodUrl(String methodUrl) {
        this.methodUrl = methodUrl;
    }

    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }
}
