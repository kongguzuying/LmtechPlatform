package com.lmtech.common;

/**
 * Excel表头
 * @author huang.jb
 *
 */
public class ExcelHeader extends ExcelCell {
	/**
	 * 行头
	 */
	public static final int TYPE_ROW = 0;
	/**
	 * 列头
	 */
	public static final int TYPE_COL = 1;
	
	private int type;
	private String name;
	
	// property
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
