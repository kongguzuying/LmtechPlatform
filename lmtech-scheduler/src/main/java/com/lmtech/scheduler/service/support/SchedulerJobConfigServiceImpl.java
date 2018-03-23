package com.lmtech.scheduler.service.support;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.lmtech.dao.Dao;
import com.lmtech.scheduler.config.SchedulerCompConfig;
import com.lmtech.scheduler.config.SchedulerJobConfig;
import com.lmtech.scheduler.dao.SchedulerJobConfigDao;
import com.lmtech.scheduler.service.SchedulerCompConfigService;
import com.lmtech.scheduler.service.SchedulerJobConfigService;
import com.lmtech.service.support.AbstractDbManagerBaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchedulerJobConfigServiceImpl extends AbstractDbManagerBaseImpl<SchedulerJobConfig> implements SchedulerJobConfigService {

	@Autowired
	private SchedulerJobConfigDao schedulerJobConfigDao;
	@Autowired
	private SchedulerCompConfigService schedulerCompConfigService;

	@Override
	public Dao getDao() {
		return schedulerJobConfigDao;
	}
	
	@Override
	public SchedulerJobConfig get(Serializable id) {
		SchedulerJobConfig jobConfig =  super.get(id);
		this.setJobConfigComponent(jobConfig);
		return jobConfig;
	}

	@Override
	public List<SchedulerJobConfig> getSchedulerJobConfigs() {
		List<SchedulerJobConfig> jobConfigs = schedulerJobConfigDao.getConfigList();
		return setConfigComponent(jobConfigs);
	}

	@Override
	public List<SchedulerJobConfig> getNormalSchedulerJobConfigs() {
		List<SchedulerJobConfig> jobConfigs = schedulerJobConfigDao.getConfigListOfType(SchedulerJobConfig.TYPE_NORMAL);
		return setConfigComponent(jobConfigs);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SchedulerJobConfig> getTriggerSchedulerJobConfigs(String dependenceJobCode) {
		List<SchedulerJobConfig> jobConfigs = schedulerJobConfigDao.getConfigListOfTypeAndDependence(SchedulerJobConfig.TYPE_TRIGGER, dependenceJobCode);
		return setConfigComponent(jobConfigs);
	}

	private List<SchedulerJobConfig> setConfigComponent(List<SchedulerJobConfig> jobConfigs) {
		if (jobConfigs != null) {
			for (SchedulerJobConfig jobConfig : jobConfigs) {
				setJobConfigComponent(jobConfig);
			}
			return jobConfigs;
		} else {
			return new ArrayList<>();
		}
	}

	private void setJobConfigComponent(SchedulerJobConfig jobConfig) {
		SchedulerCompConfig compConfig = schedulerCompConfigService.get(jobConfig.getCompConfigCode());
		jobConfig.setComponentConfig(compConfig);
	}
}
