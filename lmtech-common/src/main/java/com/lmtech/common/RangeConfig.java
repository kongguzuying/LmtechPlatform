package com.lmtech.common;

import java.util.Date;

/**
 * 范围配置
 * @author huang.jb
 *
 */
public interface RangeConfig {
	/**
	 * 获取开始日期
	 * @return
	 */
	Date getStartDate();
	/**
	 * 获取结束日期
	 * @return
	 */
	Date getEndDate();
}
