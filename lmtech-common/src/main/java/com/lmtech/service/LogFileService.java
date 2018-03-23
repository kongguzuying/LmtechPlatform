package com.lmtech.service;

import java.util.List;

import com.lmtech.common.PageData;
import com.lmtech.common.PlatformService;

/**
 * text file service
 * @author huang.jb
 *
 */
public interface LogFileService extends PlatformService {
	/**
	 * get log files
	 * @return
	 */
	List<String> getLogFiles();
	/**
	 * get text of page data
	 * @param filePath searched file
	 * @param pageIndex pgae index
	 * @param pageSize size of every page (kb)
	 * @return
	 */
	PageData getLogText(String filePath, int pageIndex, int pageSize);
	/**
	 * remove log file
	 * @param filePath
	 */
	void removeLogFile(String filePath);
}
