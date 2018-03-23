package com.lmtech.admin.common.controller;

import com.ea.card.crm.model.CardActiveRecord;
import com.lmtech.admin.common.adaptor.CardActiveRecordAdaptor;
import com.lmtech.admin.common.adaptor.ControllerManager;
import com.lmtech.common.ContextManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
@RequestMapping("/platform/cardActiveRecord")
public class CardActiveRecordController extends ManagerControllerBase<CardActiveRecord>{

    @Autowired
    private CardActiveRecordAdaptor cardActiveRecordAdaptor;

    @Override
    public String getRequestMapping() {
        return "platform/cardActiveRecord";
    }

    @Override
    public ControllerManager<CardActiveRecord> getManager() {
        return cardActiveRecordAdaptor;
    }

    @Override
    public void fillEntity(CardActiveRecord dbEntity, CardActiveRecord pageEntity) {
        dbEntity.setUserType(pageEntity.getUserType());
        dbEntity.setAppType(pageEntity.getAppType());
        dbEntity.setNickname(pageEntity.getNickname());
    }
}
