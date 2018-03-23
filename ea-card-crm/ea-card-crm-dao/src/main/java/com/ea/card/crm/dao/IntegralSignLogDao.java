package com.ea.card.crm.dao;

import com.ea.card.crm.model.IntegralDO;
import com.ea.card.crm.model.IntegralSignLog;
import com.lmtech.dao.Dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IntegralSignLogDao extends Dao<IntegralSignLog> {
    Map<String,Object> getIntegralSignLog(String userId,int integralSource);
    int updateIntegralSignLog(IntegralDO integralDO);
    boolean existOfUserId(String userId,int integralSource);
    List<IntegralSignLog> getIntegralSignLogList(String userId, int integralSource);
    int updateIsSignLog(Date date);
    int updateSignCountLog(Date date);
}
