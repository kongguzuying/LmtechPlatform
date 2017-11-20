package com.lmtech.admin.common.controller;

import com.ea.card.crm.model.RechargePayRecord;
import com.lmtech.admin.common.adaptor.RechargePayRecordAdaptor;
import com.lmtech.admin.common.adaptor.ControllerManager;
import com.lmtech.common.ContextManager;
import com.lmtech.common.PageData;
import com.lmtech.util.PropertyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/platform/rechargePayRecord")
public class RechargePayRecordController extends ManagerControllerBase<RechargePayRecord>{

    @Autowired
    private RechargePayRecordAdaptor rechargePayRecordAdaptor;

    @Override
    public String getRequestMapping() {
        return "platform/rechargePayRecord";
    }

    @Override
    public ControllerManager<RechargePayRecord> getManager() {
        return rechargePayRecordAdaptor;
    }

    @Override
    public void fillEntity(RechargePayRecord dbEntity, RechargePayRecord pageEntity) {
        dbEntity.setPayType(pageEntity.getPayType());
    }
}
