package com.lmtech.infrastructure.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lmtech.model.DbEntityBase;

/**
 * 代码项
 * @author huang.jb
 *
 */
@TableName("lm_code_item")
public class CodeItem extends DbEntityBase {
	private static final long serialVersionUID = 1L;
	@TableField("CODE")
	private String code;
	@TableField("NAME")
	private String name;
	@TableField("TYPE_CODE")
	private String typeCode;
	@TableField("VALUE")
	private String value;
	@TableField("SORT_NO")
	private int sortNo;
	
	public CodeItem() {
		
	}
	
	public CodeItem(String code, String typeCode, String value, String name, int sortNo) {
		this.setCode(code);
		this.setName(name);
		this.typeCode = typeCode;
		this.value = value;
		this.sortNo = sortNo;
	}

	// property
	public String getTypeCode() {
		return typeCode;
	}
	
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getSortNo() {
		return sortNo;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
