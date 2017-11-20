package com.lmtech.scheduler.mapper;

import com.lmtech.dao.LmtechBaseMapper;
import com.lmtech.scheduler.config.SchedulerJobConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SchedulerJobConfigMapper extends LmtechBaseMapper<SchedulerJobConfig> {
    List<SchedulerJobConfig> selectList();

    List<SchedulerJobConfig> selectListOfType(@Param("type") int type);

    List<SchedulerJobConfig> selectListOfTypeAndDependence(@Param("type") int type, @Param("dependenceJob") String dependenceJob);
}
