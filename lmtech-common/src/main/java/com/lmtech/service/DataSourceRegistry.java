package com.lmtech.service;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import com.lmtech.model.DataSource;
import com.lmtech.util.FileUtil;
import com.lmtech.util.LoggerManager;
import com.lmtech.util.XmlUtil;

/**
 * 数据源注册器
 * @author huang.jb
 *
 */
public class DataSourceRegistry {
	private DataSourceRegistry() { this.initDataSource(); }
	private static Object lockObj = new Object();
	private static DataSourceRegistry instance;
	public static DataSourceRegistry getInstance() {
		if (instance == null) {
			synchronized (lockObj) {
				if (instance == null) {
					instance = new DataSourceRegistry();
				}
			}
		}
		return instance;
	}
	private String filePath = "datasource.xml";
	private List<DataSource> dss = new ArrayList<DataSource>();
	
	/**
	 * 获取数据源
	 * @param code
	 * @return
	 */
	public DataSource getDataSource(String code) {
		if (dss != null && dss.size() > 0) {
			for (DataSource ds : dss) {
				if (ds.getCode().equals(code)) {
					return ds;
				}
			}
		}
		return null;
	}

	/**
	 * 获取所有数据源
	 * @return
     */
	public List<DataSource> getAllDataSource() {
		return dss;
	}
	
	private void initDataSource() {
		try {
			LoggerManager.info("数据源注册器初始加载 => 开始");
			URL url = this.getClass().getClassLoader().getResource(filePath);
			if (url != null) {
				if (url.toString().startsWith("jar")) {
					//从jar包内读取数据源配置文件
					InputStream stream = this.getClass().getResourceAsStream("/" + filePath);
					if (stream != null) {
						String content = FileUtil.readFileContent(stream);
						dss = XmlUtil.fromXml(content);
					} else {
						throw new Exception(String.format("initDataSource failed jar file %1$s does not exist.", "/" + filePath));
					}
				} else {
					//从文件目录中读取数据源配置文件
					File file = new File(URLDecoder.decode(url.getFile(), "UTF-8"));
					if (file.exists()) {
						String content = FileUtil.readFileContent(file.getAbsolutePath());
						dss = XmlUtil.fromXml(content);
					} else {
						throw new Exception(String.format("initDataSource failed file %1$s does not exist.", file.getAbsolutePath()));
					}
				}
			} else {
				throw new Exception(String.format("initDataSource failed file %1$s does not exist,url is null.", filePath));
			}
			LoggerManager.info(String.format("数据源注册器初始加载 => 结束，共%1$d个数据源", dss != null ? dss.size() : "0"));
		} catch (Exception e) {
			LoggerManager.error(e);
		}
	}
	
	//property
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
