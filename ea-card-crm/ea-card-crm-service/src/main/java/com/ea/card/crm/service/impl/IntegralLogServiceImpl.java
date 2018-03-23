package com.ea.card.crm.service.impl;

import com.ea.card.crm.dao.IntegralLogDao;
import com.ea.card.crm.model.IntegralLog;
import com.ea.card.crm.service.IntegralLogService;
import com.lmtech.dao.Dao;
import com.lmtech.service.support.AbstractDbManagerBaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IntegralLogServiceImpl extends AbstractDbManagerBaseImpl<IntegralLog> implements IntegralLogService {

    @Autowired
    private IntegralLogDao integralLogDao;

    @Override
    public Dao getDao() {
        return integralLogDao;
    }
}
