package com.ea.card.crm.dao.impl;

import com.ea.card.crm.dao.IntegralSetDao;
import com.ea.card.crm.mapper.IntegralSetMapper;
import com.ea.card.crm.model.BonusType;
import com.ea.card.crm.model.IntegralSet;
import com.lmtech.dao.MyBatisDaoBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IntegralSetDaoImpl extends MyBatisDaoBase<IntegralSetMapper, IntegralSet> implements IntegralSetDao {

    @Autowired
    private IntegralSetMapper integralSetMapper;

    @Override
    public Integer getIntegralSet(int dayNo,int integralSource){
        return integralSetMapper.getIntegralSet(dayNo,integralSource);
    }

    @Override
    public List<IntegralSet> getIntegralSetAll(int integralSource){
        return integralSetMapper.getIntegralSetAll(integralSource);
    }

    @Override
    public List<BonusType> groupbytype(){
        return integralSetMapper.groupByType();
    }
}
