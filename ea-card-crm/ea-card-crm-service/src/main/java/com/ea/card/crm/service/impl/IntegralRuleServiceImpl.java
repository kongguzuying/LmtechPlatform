package com.ea.card.crm.service.impl;

import com.ea.card.crm.dao.IntegralRuleDao;
import com.ea.card.crm.model.IntegralRule;
import com.ea.card.crm.service.IntegralRuleService;
import com.lmtech.dao.Dao;
import com.lmtech.service.support.AbstractDbManagerBaseImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IntegralRuleServiceImpl extends AbstractDbManagerBaseImpl<IntegralRule> implements IntegralRuleService {

    @Autowired
    private IntegralRuleDao integralRuleDao;

    @Override
    public Dao getDao() {
        return integralRuleDao;
    }

    //线上
    private static final int TYPE_ONLINE = 1;
    //线下
    private static final int TYPE_OFFLINE = 2;
    //普通会员
    private static final int MER_TYPE_VIP = 1;
    //VPASS会员
    private static final int MER_TYPE_VPASS = 2;
    //普通会员和Vpass会员
    private static final int MER_TYPE_VIP_AND_VPASS = 3;
    
	@Override
	public IntegralRule selectByType(String type,int level) {
		List<IntegralRule> ruleList = integralRuleDao.selectByType(type);
		for(IntegralRule rule : ruleList) {
			if(Integer.parseInt(type)==TYPE_OFFLINE) {
				return rule;
			}else {
				if(rule.getMerberType()==level) {
					return rule;
				}
			}
		}
		return null;
	}

}
