package com.ea.card.crm.facade.response;

import java.util.List;

public class IntegralDetailsData {
    private int lastPage;//是否最后一页：0、否1、是
    private int totalPage;//总页数
    private int total;//总条数
    private List<IntegralMemberDetailSchema> list;

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

    public List<IntegralMemberDetailSchema> getList() {
        return list;
    }

    public void setList(List<IntegralMemberDetailSchema> list) {
        this.list = list;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
