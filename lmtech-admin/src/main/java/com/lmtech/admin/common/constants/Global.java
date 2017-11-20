package com.lmtech.admin.common.constants;

import com.google.common.collect.Maps;

import java.util.Map;


public class Global {
	
	/**
	 * 保存全局属性值
	 */
	private static Map<String, String> map = Maps.newHashMap();
	
	/**
	 * 属性文件加载对象
	 */
	private static PropertiesLoader propertiesLoader = new PropertiesLoader("config.properties");

	/**
	 * 获取配置
	 */
	public static String getConfig(String key) {
		String value = map.get(key);
		if (value == null){
			value = propertiesLoader.getProperty(key);
			map.put(key, value);
		}
		return value;
	}
    
    public static String getFtpIp()
    {
        return Global.getConfig("ftp.ip");
    }
    
    public static String getFtpUsername()
    {
        return Global.getConfig("ftp.username");
    }
    
    public static String getFtpPassword()
    {
        return Global.getConfig("ftp.password");
    }
    
    public static String getImgServerpath(String path)
    {
        return Global.getConfig(path);
    }

    public static String getBasepath()
    {
        return Global.getConfig("img.basepath");
    }

    public static String getImgUrlHead()
    {
        return Global.getConfig("img.url_head");
    }

    public static String getWxUploadimg()
    {
        return Global.getConfig("wx.uploadimg");
    }
}