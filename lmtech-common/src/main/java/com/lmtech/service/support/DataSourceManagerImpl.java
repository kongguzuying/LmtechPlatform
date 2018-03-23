package com.lmtech.service.support;

import com.lmtech.model.DataSource;
import com.lmtech.service.DataSourceManager;
import org.springframework.stereotype.Service;

@Service
public class DataSourceManagerImpl extends AbstractFileManagerBaseImpl<DataSource> implements DataSourceManager {
	
	private static final long serialVersionUID = 1L;

	private String filePath = "datasource.xml";
	
	@Override
	protected String getFilePath() {
		return filePath;
	}

	protected void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
