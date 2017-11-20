package com.lmtech.infrastructure.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lmtech.model.DbEntityBase;

/**
 * 平台Action
 * @author huang.jb
 *
 */
@TableName("LM_ACTION")
public class Action extends DbEntityBase {
	private static final long serialVersionUID = 1L;
	@TableField("URL")
	private String url;
	@TableField("URL_CN_NAME")
	private String urlCnName;
	@TableField("CATEGORY")
	private String category;
	@TableField("CATEGORY_CN_NAME")
	private String categoryCnName;
	@TableField("METHOD")
	private String method;
	
	// property
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrlCnName() {
		return urlCnName;
	}
	public void setUrlCnName(String urlCnName) {
		this.urlCnName = urlCnName;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCategoryCnName() {
		return categoryCnName;
	}
	public void setCategoryCnName(String categoryCnName) {
		this.categoryCnName = categoryCnName;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
}
