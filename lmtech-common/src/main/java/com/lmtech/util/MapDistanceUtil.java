package com.lmtech.util;

/**
 * 地图距离计算工具类
 * 
 * @author huang.jb
 * 
 */
public class MapDistanceUtil {
	static double DEF_PI = 3.14159265359; // PI
	static double DEF_2PI = 6.28318530712; // 2*PI
	static double DEF_PI180 = 0.01745329252; // PI/180.0
	static double DEF_R = 6370693.5; // radius of earth

	/**
	 * 获取两点间距离，短距离使用
	 * 
	 * @param lng1 纬度
	 * @param lat1 经度
	 * @param lng2 纬度
	 * @param lat2 经度
	 * @return
	 */
	public static double getShortDistance(double lng1, double lat1, double lng2, double lat2) {
		double ew1, ns1, ew2, ns2;
		double dx, dy, dew;
		double distance;
		// 角度转换为弧度
		ew1 = lng1 * DEF_PI180;
		ns1 = lat1 * DEF_PI180;
		ew2 = lng2 * DEF_PI180;
		ns2 = lat2 * DEF_PI180;
		// 经度差
		dew = ew1 - ew2;
		// 若跨东经和西经180 度，进行调整
		if (dew > DEF_PI)
			dew = DEF_2PI - dew;
		else if (dew < -DEF_PI)
			dew = DEF_2PI + dew;
		dx = DEF_R * Math.cos(ns1) * dew; // 东西方向长度(在纬度圈上的投影长度)
		dy = DEF_R * (ns1 - ns2); // 南北方向长度(在经度圈上的投影长度)
		// 勾股定理求斜边长
		distance = Math.sqrt(dx * dx + dy * dy);
		return distance;
	}
	/**
	 * 获取两点间距离，长距离使用
	 * 
	 * @param lng1 纬度
	 * @param lat1 经度
	 * @param lng2 纬度
	 * @param lat2 经度
	 * @return
	 */
	public static double getLongDistance(double lng1, double lat1, double lng2, double lat2) {
		double ew1, ns1, ew2, ns2;
		double distance;
		// 角度转换为弧度
		ew1 = lng1 * DEF_PI180;
		ns1 = lat1 * DEF_PI180;
		ew2 = lng2 * DEF_PI180;
		ns2 = lat2 * DEF_PI180;
		// 求大圆劣弧与球心所夹的角(弧度)
		distance = Math.sin(ns1) * Math.sin(ns2) + Math.cos(ns1) * Math.cos(ns2) * Math.cos(ew1 - ew2);
		// 调整到[-1..1]范围内，避免溢出
		if (distance > 1.0)
			distance = 1.0;
		else if (distance < -1.0)
			distance = -1.0;
		// 求大圆劣弧长度
		distance = DEF_R * Math.acos(distance);
		return distance;
	}
	/**
	 * 获取两坐标点间X轴距离，即由两点形成的四边形的长度
	 * @param lng1
	 * @param lat1
	 * @param lng2
	 * @param lat2
	 * @return
	 */
	public static double getXLongDistance(double lng1, double lat1, double lng2, double lat2) {
		return getLongDistance(lng1, lat1, lng2, lat1);
	}
	/**
	 * 获取两坐标点间X轴距离，即由两点形成的四边形的长度
	 * @param lng1
	 * @param lat1
	 * @param lng2
	 * @param lat2
	 * @return
	 */
	public static double getXShortDistance(double lng1, double lat1, double lng2, double lat2) {
		return getShortDistance(lng1, lat1, lng2, lat1);
	}
	/**
	 * 获取两坐标点间Y轴距离，即由两点形成的四边形的宽度
	 * @param lng1
	 * @param lat1
	 * @param lng2
	 * @param lat2
	 * @return
	 */
	public static double getYLongDistance(double lng1, double lat1, double lng2, double lat2) {
		return getLongDistance(lng1, lat1, lng1, lat2);
	}
	/**
	 * 获取两坐标点间Y轴距离，即由两点形成的四边形的宽度
	 * @param lng1
	 * @param lat1
	 * @param lng2
	 * @param lat2
	 * @return
	 */
	public static double getYShortDistance(double lng1, double lat1, double lng2, double lat2) {
		return getShortDistance(lng1, lat1, lng1, lat2);
	}
}