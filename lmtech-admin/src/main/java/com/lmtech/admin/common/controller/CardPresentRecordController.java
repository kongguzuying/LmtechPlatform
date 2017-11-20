package com.lmtech.admin.common.controller;

import com.ea.card.crm.model.CardPresentRecord;
import com.lmtech.admin.common.adaptor.CardPresentRecordAdaptor;
import com.lmtech.admin.common.adaptor.ControllerManager;
import com.lmtech.common.ContextManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
@RequestMapping("/platform/cardPresentRecord")
public class CardPresentRecordController extends ManagerControllerBase<CardPresentRecord>{

    @Autowired
    private CardPresentRecordAdaptor cardPresentRecordAdaptor;

    @Override
    public String getRequestMapping() {
        return "platform/cardPresentRecord";
    }

    @Override
    public ControllerManager<CardPresentRecord> getManager() {
        return cardPresentRecordAdaptor;
    }

    @Override
    public void fillEntity(CardPresentRecord dbEntity, CardPresentRecord pageEntity) {
        dbEntity.setCardLevel(pageEntity.getCardLevel());
    }
}
