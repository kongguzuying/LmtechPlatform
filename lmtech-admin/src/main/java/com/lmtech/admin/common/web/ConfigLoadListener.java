package com.lmtech.admin.common.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.lmtech.admin.common.config.ConfigInfo;
import com.lmtech.util.LoggerManager;
import com.lmtech.util.PropertyUtil;

/**
 * Created by huang.jb on 2016-7-25.
 */
public class ConfigLoadListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        LoggerManager.info("加载系统配置 => 开始");
        PropertyUtil.loadProperties("config.properties");

        ConfigInfo.WORKER_ID = PropertyUtil.getPropertyOfLong("workerId", 1);
        ConfigInfo.DATACENTER_ID = PropertyUtil.getPropertyOfLong("datacenterId", 2);
        ConfigInfo.DB_DIALECT = PropertyUtil.getPropertyOfInt("dbDialect", ConfigInfo.DIALECT_MYSQL);
        ConfigInfo.PAGE_SIZE = PropertyUtil.getPropertyOfInt("pageSize", 12);
        ConfigInfo.SUPER_ADMIN_ROLE_ID=PropertyUtil.getProperty("superAdminRoleId");
        ConfigInfo.ADMIN_ROLE_ID=PropertyUtil.getProperty("adminRoleId");
        ConfigInfo.MAX_FILE_SIZE=PropertyUtil.getPropertyOfLong("maxFileSize", 50);
        ConfigInfo.FILE_SERVER_URL = PropertyUtil.getProperty("fileServerUrl");
        
        LoggerManager.info("加载系统配置 => 结束");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        PropertyUtil.destroy();
    }
}
