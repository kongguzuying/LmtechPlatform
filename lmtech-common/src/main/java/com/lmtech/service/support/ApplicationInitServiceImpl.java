package com.lmtech.service.support;

import java.util.ArrayList;
import java.util.List;

import com.lmtech.common.ApplicationInitListener;
import com.lmtech.service.ApplicationInitService;
import com.lmtech.util.LoggerManager;

public class ApplicationInitServiceImpl implements ApplicationInitService {
	
	private List<ApplicationInitListener> listeners = new ArrayList<ApplicationInitListener>();
	
	@Override
	public void initApplication() {
		if (listeners != null) {
			for (ApplicationInitListener listener : listeners) {
				try {
					listener.init();
				} catch (Exception e) {
					LoggerManager.error(e);
				}
			}
		}
	}

	// property
	public List<ApplicationInitListener> getListeners() {
		return listeners;
	}

	public void setListeners(List<ApplicationInitListener> listeners) {
		this.listeners = listeners;
	}

}
