package com.lmtech.infrastructure.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lmtech.dao.Dao;
import com.lmtech.infrastructure.dao.SystemConfigDao;
import com.lmtech.infrastructure.exceptions.SystemConfigNotExistException;
import com.lmtech.infrastructure.model.SystemConfig;
import com.lmtech.infrastructure.service.SystemConfigService;
import com.lmtech.service.support.AbstractDbServiceBaseImpl;
import com.lmtech.util.ConvertUtil;
import com.lmtech.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemConfigServiceImpl extends AbstractDbServiceBaseImpl<SystemConfig> implements SystemConfigService {

	private static final long serialVersionUID = 1L;

	@Autowired
	private SystemConfigDao systemConfigDao;

	@Override
	public Dao<SystemConfig> getDao() {
		return systemConfigDao;
	}

	@Override
	public SystemConfig getSystemConfig(String code) throws SystemConfigNotExistException {
		if (StringUtil.isNullOrEmpty(code)) {
			throw new IllegalArgumentException(String.format("参数:%1$s不允许为空。", code));
		}

		SystemConfig sc = getDao().selectById(code);

		if (sc != null) {
			return sc;
		} else {
			throw new SystemConfigNotExistException(String.format("系统参数：%1$s不存在，请检查配置。", code));
		}
	}

	@Override
	public String getStringValue(String code) throws SystemConfigNotExistException {
		SystemConfig sc = this.getSystemConfig(code);
		return sc.getValue();
	}

	@Override
	public int getIntValue(String code) throws SystemConfigNotExistException {
		SystemConfig sc = this.getSystemConfig(code);
		return ConvertUtil.convertToInt(sc.getValue());
	}

	@Override
	public long getLongValue(String code) throws SystemConfigNotExistException {
		SystemConfig sc = this.getSystemConfig(code);
		return ConvertUtil.convertToLong(sc.getValue());
	}
	
	@Override
	public double getDoubleValue(String code) throws SystemConfigNotExistException {
		SystemConfig sc = this.getSystemConfig(code);
		return ConvertUtil.convertToDouble(sc.getValue());
	}

	@Override
	public boolean getBooleanValue(String code) throws SystemConfigNotExistException {
		SystemConfig sc = this.getSystemConfig(code);
		return ConvertUtil.convertToBoolean(sc.getValue());
	}

	public Map<String, String> getAllOfMap() {
		Map<String, String> result = new HashMap<String ,String>();
		List<SystemConfig> systemConfigs = (List<SystemConfig>) getDao().selectList(null);
		if (systemConfigs != null) {
			for (SystemConfig systemConfig : systemConfigs) {
				result.put(systemConfig.getCode(), systemConfig.getValue());
			}
		}
		return result;
	}
}
