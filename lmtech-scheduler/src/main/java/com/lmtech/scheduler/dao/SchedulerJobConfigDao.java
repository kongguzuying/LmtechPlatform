package com.lmtech.scheduler.dao;

import com.lmtech.dao.Dao;
import com.lmtech.scheduler.config.SchedulerJobConfig;

import java.util.List;

public interface SchedulerJobConfigDao extends Dao<SchedulerJobConfig> {
    List<SchedulerJobConfig> getConfigList();

    List<SchedulerJobConfig> getConfigListOfType(int type);

    List<SchedulerJobConfig> getConfigListOfTypeAndDependence(int type, String dependenceJob);
}
