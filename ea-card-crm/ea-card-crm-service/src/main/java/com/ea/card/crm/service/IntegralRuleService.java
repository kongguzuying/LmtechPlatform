package com.ea.card.crm.service;

import com.ea.card.crm.model.IntegralRule;
import com.lmtech.service.DbManagerBase;

public interface IntegralRuleService extends DbManagerBase<IntegralRule> {
	
	IntegralRule selectByType(String type, int level);
}
