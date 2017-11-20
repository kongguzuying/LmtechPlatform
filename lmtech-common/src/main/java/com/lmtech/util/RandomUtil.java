package com.lmtech.util;

import java.util.Random;

public class RandomUtil {
	/**
	 * 生成随机数字串
	 * 
	 * @param length
	 *            随机数字串的长度
	 * @return
	 */
	public static String genRandomNumberString(int length) {
		if (length <= 0) {
			throw new IllegalArgumentException("length 必须大于 0");
		}

		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			int result = random.nextInt(9);
			sb.append(result + 1);
		}
		return sb.toString();
	}
	/**
	 * 生成随机数
	 * @param maxInt 最大整形值
	 * @return
	 */
	public static int genRandomNumber(int maxInt) {
		if (maxInt <= 0) {
			return maxInt;
		}
		Random random = new Random();
		int result = random.nextInt(maxInt);
		if (result == 0) {
			//为0时重新生成
			return genRandomNumber(maxInt);
		} else {
			return result;
		}
	}
	/**
	 * 生成某一范围内的随机数
	 * @param rangeStart
	 * @param rangeEnd
	 * @return
	 */
	public static int genRandomNumber(int rangeStart, int rangeEnd) {
		if (rangeStart >= rangeEnd) {
			throw new IllegalArgumentException("结束范围必须大于开始范围");
		}
		
		Random random = new Random();
		return random.nextInt(rangeEnd - rangeStart) + rangeStart;
	}
}
