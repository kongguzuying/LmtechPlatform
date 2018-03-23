package com.ea.card.crm.dao.impl;

import com.ea.card.crm.dao.IntegralLogDao;
import com.ea.card.crm.mapper.IntegralLogMapper;
import com.ea.card.crm.model.IntegralLog;
import com.lmtech.dao.MyBatisDaoBase;
import org.springframework.stereotype.Service;

@Service
public class IntegralLogDaoImpl extends MyBatisDaoBase<IntegralLogMapper, IntegralLog> implements IntegralLogDao {
}
