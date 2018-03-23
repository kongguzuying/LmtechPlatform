package com.lmtech.util;

import com.github.pagehelper.Page;
import com.lmtech.common.ContextManager;
import com.lmtech.common.PageData;

/**
 * Mybatis分页辅助类
 * Created by huang.jb on 2016-8-5.
 */
public class PageHelper {

    /**
     * 开始分页
     *
     * @param pageNum
     * @param pageSize
     */
    public static void startPage(int pageNum, int pageSize) {
        Page page = com.github.pagehelper.PageHelper.startPage(pageNum, pageSize);
        ContextManager.setValue("pager", page);
    }

    /**
     * 结束分页并返回结果，该方法必须被调用，否则localPage会一直保存下去，直到下一次startPage
     *
     * @return
     */
    public static PageData endPage() {
        try {
            Page page = ContextManager.getValue("pager");
            PageData pageData = new PageData();
            pageData.setPageSize(page.getPageSize());
            pageData.setCurrentPageNumber(page.getPageNum());
            pageData.setItems(page.getResult());
            pageData.setTotal((int) page.getTotal());
            return pageData;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
