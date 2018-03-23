package com.lmtech.scheduler.service.support;

import com.lmtech.dao.Dao;
import com.lmtech.scheduler.config.SchedulerCompConfig;
import com.lmtech.scheduler.dao.SchedulerCompConfigDao;
import com.lmtech.scheduler.service.SchedulerCompConfigService;
import com.lmtech.service.support.AbstractDbManagerBaseImpl;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchedulerCompConfigServiceImpl extends AbstractDbManagerBaseImpl<SchedulerCompConfig> implements SchedulerCompConfigService {

	@Autowired
	private SchedulerCompConfigDao schedulerCompConfigDao;

	@Override
	public Dao getDao() {
		return schedulerCompConfigDao;
	}
}
