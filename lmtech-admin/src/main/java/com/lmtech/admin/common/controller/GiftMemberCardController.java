package com.lmtech.admin.common.controller;

import com.ea.card.crm.model.GiftMemberCard;
import com.lmtech.admin.common.adaptor.ControllerManager;
import com.lmtech.admin.common.adaptor.GiftMemberCardAdaptor;
import com.lmtech.common.ContextManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
@RequestMapping("/platform/giftMemberCard")
public class GiftMemberCardController extends ManagerControllerBase<GiftMemberCard>{

    @Autowired
    private GiftMemberCardAdaptor giftMemberCardAdaptor;

    @Override
    public String getRequestMapping() {
        return "platform/giftMemberCard";
    }

    @Override
    public ControllerManager<GiftMemberCard> getManager() {
        return giftMemberCardAdaptor;
    }

    @Override
    public void fillEntity(GiftMemberCard dbEntity, GiftMemberCard pageEntity) {
        dbEntity.setCardLevel(pageEntity.getCardLevel());
        dbEntity.setCardTitle(pageEntity.getCardTitle());
        dbEntity.setCardBackground(pageEntity.getCardBackground());
    }
}
