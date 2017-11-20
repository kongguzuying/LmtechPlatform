package com.lmtech.scheduler.service.support;

import com.lmtech.dao.Dao;
import com.lmtech.scheduler.dao.SchedulerLogDao;
import com.lmtech.scheduler.model.SchedulerLog;
import com.lmtech.scheduler.service.SchedulerLogService;
import com.lmtech.service.support.AbstractDbManagerBaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchedulerLogServiceImpl extends AbstractDbManagerBaseImpl<SchedulerLog> implements SchedulerLogService {

    @Autowired
    private SchedulerLogDao schedulerLogDao;

    @Override
    public Dao getDao() {
        return schedulerLogDao;
    }
}
