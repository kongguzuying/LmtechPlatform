package com.lmtech.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.naming.NameCoder;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

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
		NameCoder nameCoder = new XmlFriendlyNameCoder("_-", "_");
		XStream stream = new XStream(new DomDriver(null, nameCoder));
		return stream.toXML(object);
	}
	
	/**
	 * deserialize xml to object
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fromXml(String xml) {
		NameCoder nameCoder = new XmlFriendlyNameCoder("_-", "_");
		XStream stream = new XStream(new DomDriver(null, nameCoder));
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
		NameCoder nameCoder = new XmlFriendlyNameCoder("_-", "_");
		XStream stream = new XStream(new DomDriver(null, nameCoder));
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
