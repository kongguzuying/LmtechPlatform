package com.lmtech.infrastructure.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lmtech.model.ConfigEntityBase;

import java.util.ArrayList;
import java.util.List;

/**
 * 代码类型
 * @author huang.jb
 *
 */
@TableName("lm_code_type")
public class CodeType extends ConfigEntityBase {

	public static final String CODE_MEMBER_CARD = "card";

	private static final long serialVersionUID = 1L;
	
	public CodeType() {
		
	}
	
	@TableField("PARENT_CODE")
	private String parentCode;
	@TableField("REMARK")
	private String remark;
	@TableField(exist = false)
	private List<CodeItem> codeItems = new ArrayList<CodeItem>();
	@TableField(exist = false)
	private CodeType parent;

	// property
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<CodeItem> getCodeItems() {
		return codeItems;
	}
	public void setCodeItems(List<CodeItem> codeItems) {
		this.codeItems = codeItems;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public CodeType getParent() {
		return parent;
	}
	public void setParent(CodeType parent) {
		this.parent = parent;
	}
}
