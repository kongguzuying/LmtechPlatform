package com.lmtech.common;

public class StateResult {
    private String tId;
    private long state;
    private String msg;
    private Object data;

    public boolean isSuccess() {
        if (state == 0) {
            return true;
        } else {
            return false;
        }
    }

    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId;
    }

    public long getState() {
        return state;
    }

    public void setState(long state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
