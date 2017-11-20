package com.ea.card.crm.service.vo;

import com.lmtech.common.StateResultT;
import com.lmtech.util.StringUtil;

public class GetMyBalanceResult extends StateResultT<GetMyBalanceData> {

    public double getMyBalance() {
        if (getData() != null) {
            String myTotalBalance = getData().getMyTotalBalance();
            if (!StringUtil.isNullOrEmpty(myTotalBalance)) {
                return Double.parseDouble(myTotalBalance);
            }
        }
        return 0;
    }
}
