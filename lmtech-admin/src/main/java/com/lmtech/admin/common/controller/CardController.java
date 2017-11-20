package com.lmtech.admin.common.controller;

import com.ea.card.crm.model.CardCategory;
import com.lmtech.admin.common.adaptor.CardAdaptor;
import com.lmtech.admin.common.adaptor.ControllerManager;
import com.lmtech.common.ContextManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
@RequestMapping("/platform/card")
public class CardController extends ComplexControllerBase<CardCategory>{

    @Autowired
    private CardAdaptor cardAdaptor;

    @Override
    public String getRequestMapping() {
        return "platform/card";
    }

    @Override
    public ControllerManager<CardCategory> getManager() {
        return cardAdaptor;
    }

    @Override
    public void fillEntity(CardCategory dbEntity, CardCategory pageEntity) {
        dbEntity.setTitle(pageEntity.getTitle());
        dbEntity.setParentId(pageEntity.getParentId());
        dbEntity.setName(pageEntity.getName());
        dbEntity.setSortNo(pageEntity.getSortNo());
        dbEntity.setBackground(pageEntity.getBackground());
        dbEntity.setWx_background(pageEntity.getWx_background());
        dbEntity.setCategory(pageEntity.getCategory());
        dbEntity.setSortNo(pageEntity.getSortNo());
        dbEntity.setRemark(pageEntity.getRemark());
    }
}
