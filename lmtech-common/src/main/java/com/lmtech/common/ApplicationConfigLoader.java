package com.lmtech.common;

import com.lmtech.util.LoggerManager;
import com.lmtech.util.PropertyUtil;
import com.lmtech.util.StringUtil;

/**
 * application loader
 *
 * @deprecated  instead by ApplicationStartContextListener.java
 * @author huang.jb
 *
 */
@Deprecated
public class ApplicationConfigLoader implements ApplicationInitListener {
	
	@Override
	public void init() {
		loadApplication();
	}
	
	/**
	 * load application, only execute one time
	 */
	public void loadApplication() {
		//load app_config properties
		PropertyUtil.loadProperties("app_config.properties");
		
		//appCode
		String appCode = PropertyUtil.getProperty("appCode");
		if (appCode != null && !appCode.equals("")) {
			ApplicationConfig.APP_CODE = appCode;
		}
		//rmiPort
		String rmiPort = PropertyUtil.getProperty("rmiPort");
		if (rmiPort != null && !rmiPort.equals("")) {
			ApplicationConfig.RMI_PORT = Integer.parseInt(rmiPort);
		}
		//model
		String model = PropertyUtil.getProperty("model");
		if (model != null && !model.equals("")) {
			ApplicationConfig.MODEL = Integer.parseInt(model);
		}
		//systemId
		String systemId = PropertyUtil.getProperty("systemId");
		if (!StringUtil.isNullOrEmpty(systemId)) {
			ApplicationConfig.SYSTEM_ID = Integer.parseInt(systemId);
		}
		//datacenterId
		String datacenterId = PropertyUtil.getProperty("datacenterId");
		if (!StringUtil.isNullOrEmpty(datacenterId)) {
			ApplicationConfig.DATACENTER_ID = Integer.parseInt(datacenterId);
		}
		
		LoggerManager.info(String.format("current system appCode: %1$s", ApplicationConfig.APP_CODE));
		LoggerManager.info(String.format("current system rmiPort: %1$d", ApplicationConfig.RMI_PORT));
		LoggerManager.info(String.format("current system model: %1$d", ApplicationConfig.MODEL));
		LoggerManager.info(String.format("current system systemId: %1$d", ApplicationConfig.SYSTEM_ID));
		LoggerManager.info(String.format("current system datacenterId: %1$d", ApplicationConfig.DATACENTER_ID));
	}
}
