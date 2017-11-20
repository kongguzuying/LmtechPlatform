package com.ea.card.crm.service.vo;

import com.lmtech.common.StateResultT;

public class FindUserResult extends StateResultT<Object> {
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
