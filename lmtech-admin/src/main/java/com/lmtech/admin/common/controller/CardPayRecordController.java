package com.lmtech.admin.common.controller;

import com.ea.card.crm.model.CardPayRecord;
import com.lmtech.admin.common.adaptor.CardPayRecordAdaptor;
import com.lmtech.admin.common.adaptor.ControllerManager;
import com.lmtech.common.ContextManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
@RequestMapping("/platform/cardPayRecord")
public class CardPayRecordController extends ManagerControllerBase<CardPayRecord>{

    @Autowired
    private CardPayRecordAdaptor cardPayRecordAdaptor;

    @Override
    public String getRequestMapping() {
        return "platform/cardPayRecord";
    }

    @Override
    public ControllerManager<CardPayRecord> getManager() {
        return cardPayRecordAdaptor;
    }

    @Override
    public void fillEntity(CardPayRecord dbEntity, CardPayRecord pageEntity) {
        dbEntity.setPayDate(pageEntity.getPayDate());
    }
}
