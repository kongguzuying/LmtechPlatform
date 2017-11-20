package com.ea.card.crm.service.impl;

import com.ea.card.crm.dao.IntegralSetDao;
import com.ea.card.crm.model.IntegralSet;
import com.ea.card.crm.service.IntegralSetService;
import com.lmtech.dao.Dao;
import com.lmtech.service.support.AbstractDbManagerBaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IntegralSetServiceImpl extends AbstractDbManagerBaseImpl<IntegralSet> implements IntegralSetService {

    @Autowired
    private IntegralSetDao integralSetDao;

    @Override
    public Dao getDao() {
        return integralSetDao;
    }

}
