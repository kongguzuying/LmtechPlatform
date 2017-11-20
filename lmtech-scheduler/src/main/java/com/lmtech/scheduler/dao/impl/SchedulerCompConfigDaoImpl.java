package com.lmtech.scheduler.dao.impl;

import com.lmtech.dao.MyBatisDaoBase;
import com.lmtech.scheduler.config.SchedulerCompConfig;
import com.lmtech.scheduler.dao.SchedulerCompConfigDao;
import com.lmtech.scheduler.mapper.SchedulerCompConfigMapper;
import org.springframework.stereotype.Service;

@Service
public class SchedulerCompConfigDaoImpl extends MyBatisDaoBase<SchedulerCompConfigMapper, SchedulerCompConfig> implements SchedulerCompConfigDao {
}
