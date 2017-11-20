package com.ea.card.crm.dao.impl;

import com.ea.card.crm.dao.IntegralSignLogDao;
import com.ea.card.crm.mapper.IntegralSignLogMapper;
import com.ea.card.crm.model.IntegralDO;
import com.ea.card.crm.model.IntegralSignLog;
import com.lmtech.dao.MyBatisDaoBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class IntegralSignLogDaoImpl extends MyBatisDaoBase<IntegralSignLogMapper, IntegralSignLog> implements IntegralSignLogDao {

    @Autowired
    private IntegralSignLogMapper integralSignLogMapper;

    @Override
    public Map<String,Object> getIntegralSignLog(String userId,int integralSource){
        return integralSignLogMapper.getIntegralSignLog(userId,integralSource);
    }

    public int updateIntegralSignLog(IntegralDO integralDO){
        return integralSignLogMapper.updateIntegralSignLog(integralDO);
    }

    @Override
    public boolean existOfUserId(String userId,int integralSource) {
        int count = integralSignLogMapper.countByUserId(userId,integralSource);
        return count > 0;
    }

    @Override
    public List<IntegralSignLog> getIntegralSignLogList(String userId,int integralSource){
         return integralSignLogMapper.getIntegralSignLogList(userId,integralSource);
    }

    @Override
    public int updateIsSignLog(Date date){
        return integralSignLogMapper.updateIsSignLog(date);
    }

    @Override
    public int updateSignCountLog(Date date){
        return integralSignLogMapper.updateSignCountLog(date);
    }
}
