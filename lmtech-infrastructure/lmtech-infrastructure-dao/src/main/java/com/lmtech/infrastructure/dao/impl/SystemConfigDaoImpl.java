package com.lmtech.infrastructure.dao.impl;

import com.lmtech.dao.MyBatisDaoBase;
import com.lmtech.infrastructure.dao.SystemConfigDao;
import com.lmtech.infrastructure.mapper.SystemConfigMapper;
import com.lmtech.infrastructure.model.SystemConfig;
import org.springframework.stereotype.Service;

/**
 * Created by huang.jb on 2017-3-13.
 */
@Service
public class SystemConfigDaoImpl extends MyBatisDaoBase<SystemConfigMapper, SystemConfig> implements SystemConfigDao {
}
