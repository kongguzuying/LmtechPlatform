package com.ea.card.crm.facade.response;

import java.util.Date;

public class IntegralMemberDetailSchema {
    private String userId;//用户ID
    private long integralNumber;//积分值
    private int integralSource;//积分来源 1、赠卡  2、签到 3、分享, 4、抽奖 5、兑换商品,6、领卡,7、抽奖抽中积分
    private String integralOrderId;//订单号Id
    private String createTime;//创建时间

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getIntegralNumber() {
        return integralNumber;
    }

    public void setIntegralNumber(long integralNumber) {
        this.integralNumber = integralNumber;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
