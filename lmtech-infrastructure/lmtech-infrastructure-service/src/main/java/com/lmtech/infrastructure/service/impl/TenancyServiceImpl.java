package com.lmtech.infrastructure.service.impl;

import com.lmtech.dao.Dao;
import com.lmtech.infrastructure.dao.TenancyDao;
import com.lmtech.infrastructure.model.Tenancy;
import com.lmtech.infrastructure.service.TenancyService;
import com.lmtech.service.support.AbstractDbManagerBaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TenancyServiceImpl extends AbstractDbManagerBaseImpl<Tenancy> implements TenancyService {

    @Autowired
    private TenancyDao tenancyDao;

    @Override
    public Dao getDao() {
        return tenancyDao;
    }
}
