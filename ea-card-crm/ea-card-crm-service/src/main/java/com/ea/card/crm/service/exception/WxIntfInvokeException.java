package com.ea.card.crm.service.exception;

import com.ea.card.crm.constants.ErrorConstants;
import com.ea.card.crm.service.vo.WxResponseBase;
import com.lmtech.exceptions.ErrorCodeException;
import com.lmtech.exceptions.LmExceptionBase;

public class WxIntfInvokeException extends LmExceptionBase {
    public WxIntfInvokeException(WxResponseBase wxResponse) {
        super(ErrorConstants.ERR_WX_INVOKEAPI_ERROR_MSG + ",errCode:" + wxResponse.getErrcode() + ",errMsg:" + wxResponse.getErrmsg());
    }
}
