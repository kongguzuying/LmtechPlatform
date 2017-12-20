package com.lmtech.admin.common.controller;

import com.lmtech.admin.common.adaptor.ControllerManager;
import com.lmtech.admin.common.adaptor.TenancyAdaptor;
import com.lmtech.infrastructure.model.Tenancy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by huang.jb on 2016-7-12.
 */
@Controller
@RequestMapping("/platform/tenancy")
public class TenancyController extends ManagerControllerBase<Tenancy> {

    @Autowired
    private TenancyAdaptor tenancyAdaptor;

    @Override
    public ControllerManager<Tenancy> getManager() {
        return tenancyAdaptor;
    }

    @Override
    public void fillEntity(Tenancy dbEntity, Tenancy pageEntity) {
        dbEntity.setName(pageEntity.getName());
        dbEntity.setCode(pageEntity.getCode());
        dbEntity.setAddress(pageEntity.getAddress());
        dbEntity.setInfo(pageEntity.getInfo());
        dbEntity.setMobile(pageEntity.getMobile());
        dbEntity.setPhone(pageEntity.getPhone());
        dbEntity.setQq(pageEntity.getQq());
        dbEntity.setStatus(pageEntity.getStatus());
        dbEntity.setUserId(pageEntity.getUserId());
        dbEntity.setUserName(pageEntity.getUserName());
        dbEntity.setWeixin(pageEntity.getWeixin());
        dbEntity.setAppId(pageEntity.getAppId());
        dbEntity.setSecret(pageEntity.getSecret());
        dbEntity.setPayApiKey(pageEntity.getPayApiKey());
        dbEntity.setAppletAppId(pageEntity.getAppletAppId());
        dbEntity.setAppletSecret(pageEntity.getAppletSecret());
        dbEntity.setAppletPayApiKey(pageEntity.getAppletPayApiKey());
    }

    @Override
    public String getRequestMapping() {
        return "platform/tenancy";
    }

}
