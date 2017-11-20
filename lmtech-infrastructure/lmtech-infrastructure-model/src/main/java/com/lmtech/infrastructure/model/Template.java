package com.lmtech.infrastructure.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lmtech.model.DbEntityBase;

/**
 * 模板
 * Created by huang.jb on 2016-8-11.
 */
@TableName("LM_TEMPLATE")
public class Template extends DbEntityBase {
    @TableField("TEMPLATE_NAME")
    private String templateName; //模板名称
    @TableField("REMARK")
    private String remark; //模板描述
    @TableField("CATEGORY_ID")
    private String categoryId; //模板分类
    @TableField("CONTENT")
    private String content; //模板内容
    @TableField("SORT")
    private int sort; //排序号

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
