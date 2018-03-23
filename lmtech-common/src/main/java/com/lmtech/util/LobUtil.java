package com.lmtech.util;

import java.io.BufferedReader;
import java.io.StringWriter;
import java.sql.Clob;

import javax.sql.rowset.serial.SerialClob;

/**
 * 大字段转换工具类
 * @author huang.jb
 *
 */
public class LobUtil {
	/**
	 * 将Clob转成字符串
	 * @param clob
	 * @return
	 */
	public static String clobToString(Clob clob) {
		try {
			BufferedReader in = new BufferedReader(clob.getCharacterStream());
			StringWriter out = new StringWriter();
			int c;
			while ((c = in.read()) != -1) {
				out.write(c);
			}
			return out.toString();
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 将字符串转成Clob
	 * @param content
	 * @return
	 */
	public static Clob stringToClob(String content) {
		try {
			Clob clob = new SerialClob(content.toCharArray());
			return clob;
		} catch (Exception e) {
			return null;
		}
	}
}
