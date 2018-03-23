package com.lmtech.infrastructure.service;

import com.lmtech.infrastructure.model.Menu;
import com.lmtech.service.DbManagerBase;
import com.lmtech.service.NotAdminDbManager;

/**
 * 菜单管理接口
 * @author huang.jb
 *
 */
public interface MenuManager extends DbManagerBase<Menu>, NotAdminDbManager {
}
