package com.ea.card.crm.service.vo;

public class GetMyBalanceData {
    private String isDisplayWallet;
    private String myBalance;
    private String myLockedBalance;
    private String myTotalBalance;
    private int hasPayPwd;

    public String getIsDisplayWallet() {
        return isDisplayWallet;
    }

    public void setIsDisplayWallet(String isDisplayWallet) {
        this.isDisplayWallet = isDisplayWallet;
    }

    public String getMyBalance() {
        return myBalance;
    }

    public void setMyBalance(String myBalance) {
        this.myBalance = myBalance;
    }

    public String getMyLockedBalance() {
        return myLockedBalance;
    }

    public void setMyLockedBalance(String myLockedBalance) {
        this.myLockedBalance = myLockedBalance;
    }

    public String getMyTotalBalance() {
        return myTotalBalance;
    }

    public void setMyTotalBalance(String myTotalBalance) {
        this.myTotalBalance = myTotalBalance;
    }

    public int getHasPayPwd() {
        return hasPayPwd;
    }

    public void setHasPayPwd(int hasPayPwd) {
        this.hasPayPwd = hasPayPwd;
    }
}
