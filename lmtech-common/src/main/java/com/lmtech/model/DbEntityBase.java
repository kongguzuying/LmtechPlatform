package com.lmtech.model;

import com.baomidou.mybatisplus.annotations.TableId;

public abstract class DbEntityBase extends EntityBase implements IdEntity {
	private static final long serialVersionUID = 1L;
	
	@TableId
	private String id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
