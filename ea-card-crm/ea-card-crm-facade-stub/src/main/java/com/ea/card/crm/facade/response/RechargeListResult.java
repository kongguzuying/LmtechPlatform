package com.ea.card.crm.facade.response;

import java.util.LinkedHashMap;

public class RechargeListResult {

    String tId;
    String state;
    String msg;
    LinkedHashMap<String, RechargeListData> data;

    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public LinkedHashMap<String, RechargeListData> getData() {
        return data;
    }

    public void setData(LinkedHashMap<String, RechargeListData> data) {
        this.data = data;
    }

    public class RechargeListData {
        String payStatus;     //订单状态码
        String payStatusName;     //订单状态名称
        String totalFee;     //订单优惠后金额
        String totalAmount;     //订单优惠前金额
        String ordersn;     //充值订单号
        String tradeNo;     //支付商户订单号
        String outTradeNo;     //支付交易流水号
        String name;     //商品名称
        String shopName;     //商户名称
        String payChannelName;     //支付方式名称
        String payTime;     //支付时间
        String buyerCellphone;       //买家手机号码
        String type;     //订单类型
        String typeName;     //订单类型名称
        String rechargeNum;     //充值号码

        public String getPayStatus() {
            return payStatus;
        }

        public void setPayStatus(String payStatus) {
            this.payStatus = payStatus;
        }

        public String getPayStatusName() {
            return payStatusName;
        }

        public void setPayStatusName(String payStatusName) {
            this.payStatusName = payStatusName;
        }

        public String getTotalFee() {
            return totalFee;
        }

        public void setTotalFee(String totalFee) {
            this.totalFee = totalFee;
        }

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getOrdersn() {
            return ordersn;
        }

        public void setOrdersn(String ordersn) {
            this.ordersn = ordersn;
        }

        public String getTradeNo() {
            return tradeNo;
        }

        public void setTradeNo(String tradeNo) {
            this.tradeNo = tradeNo;
        }

        public String getOutTradeNo() {
            return outTradeNo;
        }

        public void setOutTradeNo(String outTradeNo) {
            this.outTradeNo = outTradeNo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getPayChannelName() {
            return payChannelName;
        }

        public void setPayChannelName(String payChannelName) {
            this.payChannelName = payChannelName;
        }

        public String getPayTime() {
            return payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }

        public String getBuyerCellphone() {
            return buyerCellphone;
        }

        public void setBuyerCellphone(String buyerCellphone) {
            this.buyerCellphone = buyerCellphone;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getRechargeNum() {
            return rechargeNum;
        }

        public void setRechargeNum(String rechargeNum) {
            this.rechargeNum = rechargeNum;
        }
    }
}
