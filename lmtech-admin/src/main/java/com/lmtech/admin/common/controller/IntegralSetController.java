package com.lmtech.admin.common.controller;

import com.ea.card.crm.model.IntegralSet;
import com.lmtech.admin.common.adaptor.ControllerManager;
import com.lmtech.admin.common.adaptor.IntegralSetAdaptor;
import com.lmtech.common.ContextManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
@RequestMapping("/platform/integralSet")
public class IntegralSetController extends ManagerControllerBase<IntegralSet>{

    @Autowired
    private IntegralSetAdaptor integralSetAdaptor;

    @Override
    public String getRequestMapping() {
        return "platform/integralSet";
    }

    @Override
    public ControllerManager<IntegralSet> getManager() {
        return integralSetAdaptor;
    }

    @Override
    public void fillEntity(IntegralSet dbEntity, IntegralSet pageEntity) {
        dbEntity.setDayNo(pageEntity.getDayNo());
        dbEntity.setBonus(pageEntity.getBonus());
        dbEntity.setBonusType(pageEntity.getBonusType());
        dbEntity.setDayMax(pageEntity.getDayMax());
        dbEntity.setMainTitle(pageEntity.getMainTitle());
        dbEntity.setSubTitle(pageEntity.getSubTitle());
        dbEntity.setEndTitle(pageEntity.getEndTitle());
        dbEntity.setOrderNo(pageEntity.getOrderNo());
    }
}
