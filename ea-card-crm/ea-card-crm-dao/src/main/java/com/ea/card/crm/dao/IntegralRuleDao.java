package com.ea.card.crm.dao;

import java.util.List;

import com.ea.card.crm.model.IntegralRule;
import com.lmtech.dao.Dao;

public interface IntegralRuleDao extends Dao<IntegralRule> {
	
	List<IntegralRule> selectByType(String type);
}
