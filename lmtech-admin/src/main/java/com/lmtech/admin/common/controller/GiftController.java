package com.lmtech.admin.common.controller;

import com.ea.card.crm.model.GiftCategory;
import com.lmtech.admin.common.adaptor.ControllerManager;
import com.lmtech.admin.common.adaptor.GiftAdaptor;
import com.lmtech.common.ContextManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
@RequestMapping("/platform/gift")
public class GiftController extends ManagerControllerBase<GiftCategory>{

    @Autowired
    private GiftAdaptor giftAdaptor;

    @Override
    public String getRequestMapping() {
        return "platform/gift";
    }

    @Override
    public ControllerManager<GiftCategory> getManager() {
        return giftAdaptor;
    }

    @Override
    public void fillEntity(GiftCategory dbEntity, GiftCategory pageEntity) {
        dbEntity.setTitle(pageEntity.getTitle());
        dbEntity.setPrice(pageEntity.getPrice());
        dbEntity.setSortNo(pageEntity.getSortNo());
    }
}
