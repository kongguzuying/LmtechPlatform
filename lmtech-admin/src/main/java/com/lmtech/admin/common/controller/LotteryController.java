package com.lmtech.admin.common.controller;

import com.ea.card.crm.model.LotteryProduct;
import com.lmtech.admin.common.adaptor.ControllerManager;
import com.lmtech.admin.common.adaptor.LotteryAdaptor;
import com.lmtech.common.ContextManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
@RequestMapping("/platform/lottery")
public class LotteryController extends ManagerControllerBase<LotteryProduct>{

    @Autowired
    private LotteryAdaptor lotteryAdaptor;

    @Override
    public String getRequestMapping() {
        return "platform/lottery";
    }

    @Override
    public ControllerManager<LotteryProduct> getManager() {
        return lotteryAdaptor;
    }

    @Override
    public void fillEntity(LotteryProduct dbEntity, LotteryProduct pageEntity) {
        dbEntity.setName(pageEntity.getName());
        dbEntity.setImageUrl(pageEntity.getImageUrl());
        dbEntity.setSortNo(pageEntity.getSortNo());
        dbEntity.setPrize(pageEntity.isPrize());
        dbEntity.setType(pageEntity.getType());
        dbEntity.setJoin(pageEntity.isJoin());
    }
}
