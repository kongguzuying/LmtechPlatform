package com.lmtech.scheduler.dao.impl;

import com.lmtech.dao.MyBatisDaoBase;
import com.lmtech.scheduler.dao.SchedulerLogDao;
import com.lmtech.scheduler.mapper.SchedulerLogMapper;
import com.lmtech.scheduler.model.SchedulerLog;
import org.springframework.stereotype.Service;

@Service
public class SchedulerLogDaoImpl extends MyBatisDaoBase<SchedulerLogMapper, SchedulerLog> implements SchedulerLogDao {
}
