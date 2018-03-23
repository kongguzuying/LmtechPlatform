package com.ea.card.crm.facade.response;

import com.ea.card.crm.facade.request.GiftCardPayDetail;

import java.util.Date;
import java.util.List;

public class PayHistoryData {

    public static class PayDataItem {
        /** 未赠送 **/
        public static final int PRESENT_STATUS_NONE = 1;
        /** 赠送中 **/
        public static final int PRESENT_STATUS_ING = 2;
        /** 已赠送 **/
        public static final int PRESENT_STATUS_FINISH = 3;
        /** 超时未赠送 **/
        public static final int PRESENT_STATUS_OVERTIME = 4;

        private int mLevel;
        private String mLevelName;
        private String cardBackground;
        private double totalAmount;
        private List<GiftCardPayDetail> giftCardPayDetails;
        private int waitPresentCount;
        private int finishPresentCount;
        private int presentStatus;

        private Date payDate;
        private String orderNo;
        private String prePresentRecordId;

        public int getFinishPresentCount() {
            return finishPresentCount;
        }

        public void setFinishPresentCount(int finishPresentCount) {
            this.finishPresentCount = finishPresentCount;
        }

        public int getmLevel() {
            return mLevel;
        }

        public void setmLevel(int mLevel) {
            this.mLevel = mLevel;
        }

        public String getmLevelName() {
            return mLevelName;
        }

        public void setmLevelName(String mLevelName) {
            this.mLevelName = mLevelName;
        }

        public String getCardBackground() {
            return cardBackground;
        }

        public void setCardBackground(String cardBackground) {
            this.cardBackground = cardBackground;
        }

        public double getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
        }

        public List<GiftCardPayDetail> getGiftCardPayDetails() {
            return giftCardPayDetails;
        }

        public void setGiftCardPayDetails(List<GiftCardPayDetail> giftCardPayDetails) {
            this.giftCardPayDetails = giftCardPayDetails;
        }

        public int getPresentStatus() {
            return presentStatus;
        }

        public void setPresentStatus(int presentStatus) {
            this.presentStatus = presentStatus;
        }

        public int getWaitPresentCount() {
            return waitPresentCount;
        }

        public void setWaitPresentCount(int waitPresentCount) {
            this.waitPresentCount = waitPresentCount;
        }

        public Date getPayDate() {
            return payDate;
        }

        public void setPayDate(Date payDate) {
            this.payDate = payDate;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getPrePresentRecordId() {
            return prePresentRecordId;
        }

        public void setPrePresentRecordId(String prePresentRecordId) {
            this.prePresentRecordId = prePresentRecordId;
        }
    }

    private int pageIndex;
    private int pageSize;
    private int total;
    private List<PayDataItem> dataItems;

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
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

    public List<PayDataItem> getDataItems() {
        return dataItems;
    }

    public void setDataItems(List<PayDataItem> dataItems) {
        this.dataItems = dataItems;
    }

}
