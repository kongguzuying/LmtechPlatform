package com.ea.card.crm.facade.request;

public class GiftCardPayDetail {
    private String giftCategoryId;
    private double price;
    private int number;
    private int presentDays;

    public String getGiftCategoryId() {
        return giftCategoryId;
    }

    public void setGiftCategoryId(String giftCategoryId) {
        this.giftCategoryId = giftCategoryId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPresentDays() {
        return presentDays;
    }

    public void setPresentDays(int presentDays) {
        this.presentDays = presentDays;
    }
}
