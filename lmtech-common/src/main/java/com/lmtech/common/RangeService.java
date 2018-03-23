package com.lmtech.common;

import java.util.Date;
import java.util.List;

/**
 * 范围服务
 * @author huang.jb
 *
 */
public interface RangeService {
	/**
	 * 获取大于等于开区间小于闭区间内的所有对象
	 * @param date
	 * @return
	 */
	List<? extends RangeConfig> getRangeObjects(Date date);
}
