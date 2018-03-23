package com.lmtech.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.lmtech.common.IComplex;

/**
 * 处理复合对象工具类
 * @author huang.jb
 *
 */
public class ComplexUtil<T> {
	/**
	 * 将父子关系转换成一个有序的队列
	 * @param complexs
	 * @return
	 */
	public List<T> convertToList(List<T> complexs) {
		List<T> result = new ArrayList<T>();
		getSortedList(result, complexs, null);
		return result;
	}
	/**
	 * 将父子关系转换成一个有序的队列
	 * @param complexs
	 * @return
	 */
	public List<T> convertToList(List<T> complexs, List<String> exceptIds) {
		List<T> result = new ArrayList<T>();
		getSortedList(result, complexs, exceptIds);
		return result;
	}
	/**
	 * 将有序的队列转换成父子有关系
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> convertToComplex(List<T> list) {
		Map<String, T> map = new LinkedHashMap<String, T>();
		//填充id键值对
		for (T item : list) {
			IComplex<T> compItem = (IComplex<T>)item;
			map.put(compItem.getId(), item);
		}
		//获取顶层项
		List<T> topList = new ArrayList<T>();
		for (T item : list) {
			IComplex<T> compItem = (IComplex<T>)item;
			if (!map.containsKey(compItem.getParentId())) {
				topList.add(item);
			}
		}
		//整合父子关系
		appendChild(topList, map);
		return topList;
	}
	/**
	 * 获取按父子关系排好序的列表
	 * @param list
	 * @return
	 */
	public List<T> getSortedByParentId(List<T> list) {
		List<T> complex = convertToComplex(list);
		return convertToList(complex);
	}
	
	@SuppressWarnings("unchecked")
	private void getSortedList(List<T> result, List<T> complexs, List<String> exceptIds) {
		for (T item : complexs) {
			IComplex<T> compItem = (IComplex<T>)item;
			boolean needExcept = (exceptIds != null && exceptIds.size() > 0 && exceptIds.contains(compItem.getId()));
			if (!needExcept) {
				result.add(item);
				if (compItem.hasChildren()) {
					getSortedList(result, compItem.getChildrens(), exceptIds);
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void appendChild(List<T> levelItems, Map<String, T> allItems) {
		List<T> newLevelItems = new ArrayList<T>();
		for (T levelItem : levelItems) {
			IComplex<T> compLevelItem = (IComplex<T>)levelItem;
			//此处可能会引起性能问题，后续可调优
			compLevelItem.getChildrens().clear();
			for (Iterator<?> it =allItems.keySet().iterator(); it.hasNext();) {
				T item = allItems.get(it.next());
				IComplex<T> compItem = (IComplex<T>)item;
				if (compItem.getParentId().equals(compLevelItem.getId())) {
					compLevelItem.addChildren(item);
					newLevelItems.add(item);
				}
			}
		}
		if (newLevelItems.size() > 0) {
			appendChild(newLevelItems, allItems);
		}
	}
}
