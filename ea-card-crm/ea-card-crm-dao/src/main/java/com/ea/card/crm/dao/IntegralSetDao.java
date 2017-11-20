package com.ea.card.crm.dao;

import com.ea.card.crm.model.BonusType;
import com.ea.card.crm.model.IntegralSet;
import com.lmtech.dao.Dao;

import java.util.List;

public interface IntegralSetDao extends Dao<IntegralSet> {
    Integer getIntegralSet(int dayNo,int integralSource);
    List<IntegralSet> getIntegralSetAll(int integralSource);
    List<BonusType> groupbytype();
}
