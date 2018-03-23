package com.ea.card.crm.facade.request;

public class IntegralRequest {
    private String openId;//用户openId
    private int integralType;//积分操作类型 1、增加  2、消费
    private int integralSource;//积分来源 1、赠卡  2、签到 3、分享, 4、抽奖 5、兑换商品
    private String integralOrderId;//订单号Id
    private long integralNumber;//积分值
    private int pageNum;//第几页 默认 1
    private int pageSize;//每页条数 默认20

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public int getIntegralType() {
        return integralType;
    }

    public void setIntegralType(int integralType) {
        this.integralType = integralType;
    }

    public int getIntegralSource() {
        return integralSource;
    }

    public void setIntegralSource(int integralSource) {
        this.integralSource = integralSource;
    }

    public String getIntegralOrderId() {
        return integralOrderId;
    }

    public void setIntegralOrderId(String integralOrderId) {
        this.integralOrderId = integralOrderId;
    }

    public long getIntegralNumber() {
        return integralNumber;
    }

    public void setIntegralNumber(long integralNumber) {
        this.integralNumber = integralNumber;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
