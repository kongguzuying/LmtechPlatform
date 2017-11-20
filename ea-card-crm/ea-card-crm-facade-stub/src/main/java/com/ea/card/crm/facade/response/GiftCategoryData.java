package com.ea.card.crm.facade.response;

import com.ea.card.crm.model.GiftCategory;

import java.util.List;

public class GiftCategoryData {
    private int lastPage;//是否最后一页：0、否1、是
    private int totalPage;//总页数
    private List<GiftCategory> list;

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<GiftCategory> getList() {
        return list;
    }

    public void setList(List<GiftCategory> list) {
        this.list = list;
    }
}
