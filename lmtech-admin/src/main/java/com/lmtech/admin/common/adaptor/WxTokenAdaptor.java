package com.lmtech.admin.common.adaptor;

import com.ea.card.crm.facade.stub.WxTokenFacade;
import com.lmtech.common.StateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class WxTokenAdaptor extends ServiceAdaptorBase{

    @Autowired
    private WxTokenFacade wxTokenFacade;

    /**
     * 获取微信AccessToken
     * @return
     */
    public StateResult getAccessToken() {
        return wxTokenFacade.getAccessToken();
    }

}
