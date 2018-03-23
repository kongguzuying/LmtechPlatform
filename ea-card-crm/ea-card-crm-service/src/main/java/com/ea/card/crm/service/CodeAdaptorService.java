package com.ea.card.crm.service;

import com.lmtech.infrastructure.model.CodeItem;
import org.springframework.stereotype.Service;


@Service
public interface CodeAdaptorService{


    CodeItem getCodeItemByValue(String codeType, String codeItemValue);

    String getNameByCodeItemValue(String codeItemValue);
}
