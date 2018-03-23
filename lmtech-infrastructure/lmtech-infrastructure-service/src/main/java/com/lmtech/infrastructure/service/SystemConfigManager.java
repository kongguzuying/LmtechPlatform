package com.lmtech.infrastructure.service;

import com.lmtech.infrastructure.model.SystemConfig;
import com.lmtech.service.DbManagerBase;
import com.lmtech.service.NotAdminDbManager;

public interface SystemConfigManager extends DbManagerBase<SystemConfig>, NotAdminDbManager {
}
