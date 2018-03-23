package com.ea.card.crm.service.impl;

import com.ea.card.crm.service.CodeAdaptorService;
import com.lmtech.facade.request.StringRequest;
import com.lmtech.infrastructure.facade.response.CodeItemListResponse;
import com.lmtech.infrastructure.facade.stub.CodeFacade;
import com.lmtech.infrastructure.model.CodeItem;
import com.lmtech.infrastructure.model.CodeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CodeAdaptorServiceImpl implements CodeAdaptorService{

    @Autowired
    private CodeFacade codeFacade;


    @Override
    public CodeItem getCodeItemByValue(String codeType, String codeItemValue) {

        if (codeType == null) {
            codeType = CodeType.CODE_MEMBER_CARD;
        }

        CodeItemListResponse response = codeFacade.getCodeItemOfType(new StringRequest(codeType));
        for (CodeItem codeItem : response.getData()) {
            if (codeItem.getValue().equals(codeItemValue)) {
                return codeItem;
            }
        }
        return null;
    }

    @Override
    public String getNameByCodeItemValue(String codeItemValue) {
        CodeItemListResponse response = codeFacade.getCodeItemOfType(new StringRequest(CodeType.CODE_MEMBER_CARD));
        for (CodeItem codeItem : response.getData()) {
            if (codeItem.getValue().equals(codeItemValue)) {
                return codeItem.getName();
            }
        }
        return "";
    }
}
