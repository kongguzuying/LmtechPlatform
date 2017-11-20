package com.lmtech.service.support;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.lmtech.model.ConfigEntityBase;
import com.lmtech.model.DbEntityBase;
import com.lmtech.model.EntityBase;
import com.lmtech.service.FileManagerBase;
import com.lmtech.util.ClassUtil;
import com.lmtech.util.FileUtil;
import com.lmtech.util.LoggerManager;
import com.lmtech.util.XmlUtil;

public abstract class AbstractFileManagerBaseImpl<T extends EntityBase> implements FileManagerBase<T> {

	private static final long serialVersionUID = 1L;

	protected abstract String getFilePath();
	
	private List<T> ts = null;
	
	public void init() {
		try {
			URL url = this.getClass().getClassLoader().getResource(this.getFilePath());
			if (url != null) {
				if (url.toString().startsWith("jar")) {
					//从jar包内读取数据源配置文件
					InputStream stream = this.getClass().getResourceAsStream("/" + this.getFilePath());
					if (stream != null) {
						String content = FileUtil.readFileContent(stream);
						ts = XmlUtil.fromXml(content);
					} else {
						ts = new ArrayList<T>();
					}
				} else {
					File file = new File(url.getFile());
					if (file.exists()) {
						String content = FileUtil.readFileContent(file.getAbsolutePath());
						ts = XmlUtil.fromXml(content);
					} else {
						ts = new ArrayList<T>();
					}
				}
			} else {
				ts = new ArrayList<T>();
			}
		} catch (Exception e) {
			LoggerManager.error(e);
		}
	}
	
	@Override
	public String add(T t) {
		try {
			ts.add(t);
			this.serializeConfig();
		} catch (Exception e) {
			LoggerManager.error("add entity failed", e);
		} finally {
			if (t != null) {
				if (ClassUtil.isSameType(t, DbEntityBase.class)) {
					DbEntityBase dbt = (DbEntityBase) t;
					return dbt.getId();
				} else if (ClassUtil.isSameType(t, ConfigEntityBase.class)) {
					ConfigEntityBase ct = (ConfigEntityBase) t;
					return ct.getCode();
				}
			}
		}
		return null;
	}

	@SuppressWarnings("unused")
	@Override
	public void update(T t) {
		try {
			if (ClassUtil.isSameType(t, DbEntityBase.class)) {
				DbEntityBase dbt = (DbEntityBase) t;
				T entity = this.get(dbt.getId());
				entity = t;
			} else if (ClassUtil.isSameType(t, ConfigEntityBase.class)) {
				ConfigEntityBase ct = (ConfigEntityBase) t;
				T entity = this.get(ct.getCode());
				entity = t;
			}
			this.serializeConfig();
		} catch (Exception e) {
			LoggerManager.error("update entity failed", e);
		}
	}

	@Override
	public void remove(Serializable id) {
		try {
			T t = this.get(id);
			ts.remove(t);
			this.serializeConfig();
		} catch (Exception e) {
			LoggerManager.error("remove entity failed", e);
		}
	}

	@Override
	public T get(Serializable id) {
		for (T t : ts) {
			if (ClassUtil.isSameType(t, DbEntityBase.class)) {
				DbEntityBase dbt = (DbEntityBase) t;
				if (dbt.getId().equals(id)) {
					return t;
				}
			} else if (ClassUtil.isSameType(t, ConfigEntityBase.class)) {
				ConfigEntityBase ct = (ConfigEntityBase) t;
				if (ct.getCode().equals(id)) {
					return t;
				}
			}
		}
		return null;
	}

	@Override
	public List<T> getAll() {
		return ts;
	}

	private void serializeConfig() throws IOException {
		URL url = this.getClass().getClassLoader().getResource(this.getFilePath());
		File file = new File(url.getFile());
		String content = XmlUtil.toXml(ts);
		FileUtil.writeFileContent(file.getAbsolutePath(), content);
	}
}
