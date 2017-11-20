package com.lmtech.admin.common.controller;

import com.ea.card.crm.model.MemberRegister;
import com.lmtech.admin.common.adaptor.ControllerManager;
import com.lmtech.admin.common.adaptor.MemberRegisterAdaptor;
import com.lmtech.common.ContextManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
@RequestMapping("/platform/memberRegister")
public class MemberRegisterController extends ManagerControllerBase<MemberRegister>{

    @Autowired
    private MemberRegisterAdaptor memberRegisterAdaptor;

    @Override
    public String getRequestMapping() {
        return "platform/memberRegister";
    }

    @Override
    public ControllerManager<MemberRegister> getManager() {
        return memberRegisterAdaptor;
    }

    @Override
    public void fillEntity(MemberRegister dbEntity, MemberRegister pageEntity) {
        dbEntity.setUserType(pageEntity.getUserType());
        dbEntity.setAppType(pageEntity.getAppType());
        dbEntity.setNickname(pageEntity.getNickname());
    }
}
