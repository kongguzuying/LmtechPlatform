package com.lmtech.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 * @author huang.jb
 *
 */
public class RegexUtil {
	/**
	 * 是否匹配正则表达式
	 * @param text
	 * @param regex
	 * @return
	 */
	public static boolean isMatch(String text, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		return matcher.find();
	}
	/**
	 * 获取匹配的分组列表
	 * @param text
	 * @param leftRegex
	 * @param rightRegex
	 * @return
	 */
	public static List<String> getMatchGroups(String text, String leftRegex, String rightRegex) {
		Pattern pattern = Pattern.compile(".*?" + leftRegex + "(.*?)" + rightRegex + ".*?");
		Matcher matcher = pattern.matcher(text);
		List<String> groupStrings = new ArrayList<String>();
		while (matcher.find()) {
			int count = matcher.groupCount();
			for (int i = 1; i <= count; i++) {
				groupStrings.add(matcher.group(i));
			}
		}
		return groupStrings;
	}

	/**
	 * 获取匹配的分组
	 * @param text
	 * @param leftRegex
	 * @param rightRegex
     * @return
     */
	public static String getMatchGroup(String text, String leftRegex, String rightRegex) {
		List<String> results = getMatchGroups(text, leftRegex, rightRegex);
		if (!CollectionUtil.isNullOrEmpty(results)) {
			return results.get(0);
		} else {
			return null;
		}
	}
}
