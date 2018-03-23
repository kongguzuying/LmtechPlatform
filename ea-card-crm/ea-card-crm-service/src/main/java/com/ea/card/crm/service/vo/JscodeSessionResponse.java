package com.ea.card.crm.service.vo;

public class JscodeSessionResponse {
	
	private String session_key;
	
    private String unionid;
    
    private String openid;
    
    private long errcode;
    
    private String errmsg;

	public String getSession_key() {
		return session_key;
	}

	public void setSession_key(String session_key) {
		this.session_key = session_key;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public long getErrcode() {
		return errcode;
	}

	public void setErrcode(long errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
    
    
}
