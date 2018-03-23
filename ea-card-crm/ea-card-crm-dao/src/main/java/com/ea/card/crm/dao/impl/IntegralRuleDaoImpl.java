package com.ea.card.crm.dao.impl;

import com.ea.card.crm.dao.IntegralRuleDao;
import com.ea.card.crm.mapper.IntegralRuleMapper;
import com.ea.card.crm.model.BonusType;
import com.ea.card.crm.model.IntegralRule;
import com.lmtech.dao.MyBatisDaoBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IntegralRuleDaoImpl extends MyBatisDaoBase<IntegralRuleMapper, IntegralRule> implements IntegralRuleDao {

    @Autowired
    private IntegralRuleMapper integralRuleMapper;

	@Override
	public List<IntegralRule> selectByType(String type) {
		return integralRuleMapper.selectByType(type);
	}
}
