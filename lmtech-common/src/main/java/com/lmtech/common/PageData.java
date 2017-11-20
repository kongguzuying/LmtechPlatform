package com.lmtech.common;

import java.rmi.RemoteException;
import java.util.List;

import com.lmtech.model.EntityBase;

/**
 * page data
 * @author huang.jb
 *
 */
public class PageData<T> extends EntityBase {
	private static final long serialVersionUID = 1L;
	
	public static final String PAGE_INDEX_KEY = "pageIndex";
	public static final String PAGE_SIZE_KEY = "pageSize";
	
	private int pageSize = 20;
	private int total;
	private int currentPageNumber = 1;
	private List<T> items;
	private Object data;
	
	public PageData() throws RemoteException {}
	public PageData(int pageSize, int total, int currentPageNumber,
			List<T> items) throws RemoteException {
		super();
		this.pageSize = pageSize;
		this.total = total;
		this.currentPageNumber = currentPageNumber;
		this.items = items;
	}
	
	public int getItemSize() {
		if (items != null) {
			return items.size();
		} else {
			return 0;
		}
	}

	public int getTotalPage() {
		if (total % pageSize == 0) {
			return total / pageSize;
		} else {
			return total / pageSize + 1;
		}
	}

	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getCurrentPageNumber() {
		return currentPageNumber;
	}
	public void setCurrentPageNumber(int currentPageNumber) {
		this.currentPageNumber = currentPageNumber;
	}
	public List<?> getItems() {
		return items;
	}
	public void setItems(List<T> items) {
		this.items = items;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}