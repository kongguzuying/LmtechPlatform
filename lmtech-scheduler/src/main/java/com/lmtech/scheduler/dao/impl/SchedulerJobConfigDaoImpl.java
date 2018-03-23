package com.lmtech.scheduler.dao.impl;

import com.lmtech.dao.MyBatisDaoBase;
import com.lmtech.scheduler.config.SchedulerJobConfig;
import com.lmtech.scheduler.dao.SchedulerJobConfigDao;
import com.lmtech.scheduler.mapper.SchedulerJobConfigMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchedulerJobConfigDaoImpl extends MyBatisDaoBase<SchedulerJobConfigMapper, SchedulerJobConfig> implements SchedulerJobConfigDao {
    @Override
    public List<SchedulerJobConfig> getConfigList() {
        return baseMapper.selectList();
    }

    public List<SchedulerJobConfig> getConfigListOfType(int type) {
        return baseMapper.selectListOfType(type);
    }

    @Override
    public List<SchedulerJobConfig> getConfigListOfTypeAndDependence(int type, String dependenceJob) {
        return null;
    }
}
