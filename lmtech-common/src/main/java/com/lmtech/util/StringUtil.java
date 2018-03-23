package com.lmtech.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * 
 * @author hjb
 * 
 */
public class StringUtil {
	/**
	 * 字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNullOrEmpty(Object str) {
		if (str == null)
			return true;

		str = str.toString().trim();
		return str.equals("");
	}

	/**
	 * 忽略大小写比较
	 * @param src
	 * @param dest
     * @return
     */
	public static boolean equalsIgnoreNullCase(String src, String dest) {
		if (!StringUtil.isNullOrEmpty(src)) {
			return src.equalsIgnoreCase(dest);
		} else {
			return false;
		}
	}

	/**
	 * 删除开头字符串
	 * 
	 * @param str
	 * @param startStr
	 * @return
	 */
	public static String deleteStartStr(String str, String startStr) {
		if (str.startsWith(startStr)) {
			return str.substring(str.indexOf(startStr));
		} else {
			return str;
		}
	}

	/**
	 * 删除结尾字符串
	 * 
	 * @param str
	 *            原字符串
	 * @param endStr
	 *            结尾的字符串
	 * @return 返回新的字符串
	 */
	public static String deleteEndStr(String str, String endStr) {
		if (str.endsWith(endStr)) {
			return str.substring(0, str.lastIndexOf(endStr));
		} else {
			return str;
		}
	}

	/**
	 * 设置首字母小写
	 * 
	 * @param str
	 *            原字符串
	 * @return 设置后的字符串
	 */
	public static String setFirstLower(String str) {
		if (!isNullOrEmpty(str)) {
			char firstChar = str.charAt(0);
			if (Character.isUpperCase(firstChar)) {
				char[] strChars = str.toCharArray();
				strChars[0] = Character.toLowerCase(firstChar);

				return new String(strChars);
			}
		}
		return str;
	}

	/**
	 * 设置首字母大写
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static String setFirstUpper(String str) {
		if (!isNullOrEmpty(str)) {
			char firstChar = str.charAt(0);
			if (Character.isLowerCase(firstChar)) {
				char[] strChars = str.toCharArray();
				strChars[0] = Character.toUpperCase(firstChar);

				return new String(strChars);
			}
		}
		return str;
	}

	/**
	 * 如果字符串尾部不存在指定字符串则附加
	 * 
	 * @param str
	 *            原字符串
	 * @param appendStr
	 *            要附加的字符串
	 * @return 附加后的字符串
	 */
	public static String appendWhenNotExist(String str, String appendStr) {
		if (!str.endsWith(appendStr)) {
			str += appendStr;
		}
		return str;
	}

	/**
	 * 替换字符串
	 * 
	 * @param str
	 *            字符串
	 * @param oldStr
	 *            旧的字符串
	 * @param newStr
	 *            新的字符串
	 * @return
	 */
	public static String replace(String str, String oldStr, String newStr) {
		if (oldStr == null)
			oldStr = "";
		if (newStr == null)
			newStr = "";

		if (!isNullOrEmpty(str)) {
			return str.replace(oldStr, newStr);
		} else {
			return str;
		}
	}

	private static String[] str = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

	/**
	 * 获取随机字符串
	 * 
	 * @return
	 */
	public static String getRandomString(int length) {
		if (length <= 0) {
			throw new IllegalArgumentException("随机字符串的长度必须大于0");
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int value = (int) (Math.random() * 48);
			sb.append(str[value]);
		}
		return sb.toString();
	}

	/**
	 * 获取随机参数名
	 * 
	 * @param prefix
	 *            参数前缀
	 * @param length
	 *            参数长度
	 * @return
	 */
	public static String getRandomString(String prefix, int length) {
		String randomStr = getRandomString(length);
		if (!isNullOrEmpty(prefix)) {
			return prefix + randomStr;
		} else {
			return randomStr;
		}
	}

	/**
	 * 格式化字符串，忽略NULL值，将NULL值转为空
	 * 
	 * @param format
	 * @param args
	 * @return
	 */
	public static String formatStringIgnoreNull(String format, String... args) {
		String[] params = args;
		if (params != null && params.length > 0) {
			Object[] formatArgs = new Object[params.length];
			for (int i = 0; i < params.length; i++) {
				if (params[i] == null) {
					formatArgs[i] = "";
				} else {
					formatArgs[i] = params[i];
				}
			}
			return String.format(format, formatArgs);
		} else {
			return format;
		}
	}

	/**
	 * 转变字符串列表为字符串
	 * 
	 * @param strs
	 * @return
	 */
	public static String convertListToString(List<String> strs) {
		return convertListToString(strs, ",");
	}

	/**
	 * 转变字符串列表为字符串
	 * 
	 * @param strs
	 *            字符串列表
	 * @param insertText
	 *            中间插入的字符
	 * @return
	 */
	public static String convertListToString(List<String> strs, String insertText) {
		if (strs == null) {
			throw new IllegalArgumentException("传入的字符串列表必须不为空。");
		}

		StringBuilder sb = new StringBuilder();
		int i = 0;
		boolean needInsert = !StringUtil.isNullOrEmpty(insertText);
		for (String str : strs) {
			if (i > 0 && needInsert) {
				sb.append(insertText);
			}
			sb.append(str);
			i++;
		}

		return sb.toString();
	}

	/**
	 * 分割字符串 ，默认使用逗号分隔
	 * 
	 * @param str
	 * @return
	 */
	public static List<String> splitString(String str) {
		return splitString(str, ",");
	}

	/**
	 * 分割字符串
	 * 
	 * @param str
	 * @param splitStr
	 * @return
	 */
	public static List<String> splitString(String str, String splitStr) {
		if (StringUtil.isNullOrEmpty(str)) {
			return new ArrayList<>();
		}
		if (StringUtil.isNullOrEmpty(splitStr)) {
			throw new IllegalArgumentException("分割字符串参数不允许为空。");
		}

		String[] splits = str.split(splitStr);
		List<String> result = new ArrayList<String>();
		if (splits != null) {
			for (int i = 0; i < splits.length; i++) {
				result.add(splits[i].trim());
			}
		}
		return result;
	}

	/**
	 * 重叠字符串
	 * 
	 * @param str
	 * @param times
	 * @return
	 */
	public static String duplicateString(String str, int times) {
		return duplicateString(str, times, ",");
	}

	/**
	 * 重叠字符串
	 * 
	 * @param str
	 * @param times
	 * @param insertStr
	 * @return
	 */
	public static String duplicateString(String str, int times, String insertStr) {
		if (StringUtil.isNullOrEmpty(str)) {
			throw new IllegalArgumentException("输入的字符串参数不允许为空。");
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < times; i++) {
			if (i > 0) {
				sb.append(insertStr);
			}
			sb.append(str);
		}
		return sb.toString();
	}

	/**
	 * escape字符串对应js中的escape
	 * @param src
	 * @return
	 */
	public static String escape(String src) {
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);

		for (i = 0; i < src.length(); i++) {

			j = src.charAt(i);

			if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j))
				tmp.append(j);
			else if (j < 256) {
				tmp.append("%");
				if (j < 16)
					tmp.append("0");
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}

	/**
	 * unescape字符串对应js中的unescape
	 * @param src
	 * @return
	 */
	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}
	/**
	 * 如果字符窜为null或者为空或者=='null' 用指定字符窜替代
	 * @param str
	 * @param replaceStr
	 * @return
	 */
	public static String isNullReplace(String str,String replaceStr){
		if(StringUtil.isNullOrEmpty(replaceStr)){
			replaceStr = "";
		}
		if(StringUtil.isNullOrEmpty(str) || "null".equals(str)){
			return replaceStr;
		}
		return str.trim();
	}
	
	/**
	 * 字符串右补齐空格
	 * @param n 字符总长度
	 * @param s 字符传
	 * @return
	 */
	public static String rightFill(int n,String s){
			if(s.length()>=n)
				return s ;
			return  String.format("%-"+n+"s", s) ;
	}

	public static final String ENCRYPT_KEY_SPLIT_STR = "^@^";
	public static final String DECRYPT_KEY_SPLIT_STR = "\\^@\\^";

	/**
	 * 加密字符串列表
	 * @param keys
	 * @return
	 */
	public static String encryptStrings(String... keys) {
		try {
			if (keys != null && keys.length > 0) {
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < keys.length; i++) {
					if (i > 0) {
						sb.append(ENCRYPT_KEY_SPLIT_STR);
					}
					sb.append(keys[i]);
				}
				return AESUtil.encrypt(sb.toString());
			} else {
				throw new RuntimeException("keys could not be null or empty");
			}
		} catch (Exception e) {
			LoggerManager.error(e);
			return null;
		}
	}

	/**
	 * 逆向解析Token
	 * @param encryptString
	 * @return
	 */
	public static List<String> decryptStrings(String encryptString) {
		if (!StringUtil.isNullOrEmpty(encryptString)) {
			try {
				String str = AESUtil.decrypt(encryptString);
				String[] keys = str.split(DECRYPT_KEY_SPLIT_STR);
				return (List<String>) CollectionUtil.convertArrayToList(keys);
			} catch (Exception e) {
				LoggerManager.error(e);
			}
		}
		return null;
	}

	/**
	 * 删除字符串空白和换行字符
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str!=null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	public static String defaultIfEmpty(String str, String defaultStr) {
		return isEmpty(str) ? defaultStr : str;
	}

	public static boolean isEmpty(String str)
	{
		if (str == null)
			return true;
		return "".equals(str.trim());
	}
}