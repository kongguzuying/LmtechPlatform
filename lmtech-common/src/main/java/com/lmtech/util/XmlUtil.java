package com.lmtech.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * xml util
 * @author huang.jb
 *
 */
public class XmlUtil {
	/**
	 * serialize object to xml
	 * @param object
	 * @return
	 */
	public static String toXml(Object object) {
		XStream stream = new XStream(new DomDriver());
		return stream.toXML(object);
	}
	
	/**
	 * deserialize xml to object
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fromXml(String xml) {
		XStream stream = new XStream(new DomDriver());
		Object object = stream.fromXML(xml);
		if (object != null) {
			return (T) object;
		} else {
			return null;
		}
	}

	/**
	 * deserialize xml to object
	 * @param xml
	 * @return
	 */
	public static <T> T fromXml(String xml, String root, Class<?> clazz) {
		XStream stream = new XStream(new DomDriver());
		stream.alias(root, clazz);
		stream.ignoreUnknownElements();
		Object object = stream.fromXML(xml);
		if (object != null) {
			return (T) object;
		} else {
			return null;
		}
	}
}
