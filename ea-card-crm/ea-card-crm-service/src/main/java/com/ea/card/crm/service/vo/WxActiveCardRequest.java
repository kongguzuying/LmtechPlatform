package com.ea.card.crm.service.vo;

public class WxActiveCardRequest {
    private double init_bonus;
    private String init_bonus_record;
    private double init_balance;
    private String membership_number;
    private String code;
    private String card_id;
    private String background_pic_url;
    private String init_custom_field_value1;

    public double getInit_bonus() {
        return init_bonus;
    }

    public void setInit_bonus(double init_bonus) {
        this.init_bonus = init_bonus;
    }

    public String getInit_bonus_record() {
        return init_bonus_record;
    }

    public void setInit_bonus_record(String init_bonus_record) {
        this.init_bonus_record = init_bonus_record;
    }

    public double getInit_balance() {
        return init_balance;
    }

    public void setInit_balance(double init_balance) {
        this.init_balance = init_balance;
    }

    public String getMembership_number() {
        return membership_number;
    }

    public void setMembership_number(String membership_number) {
        this.membership_number = membership_number;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public String getBackground_pic_url() {
        return background_pic_url;
    }

    public void setBackground_pic_url(String background_pic_url) {
        this.background_pic_url = background_pic_url;
    }

    public String getInit_custom_field_value1() {
        return init_custom_field_value1;
    }

    public void setInit_custom_field_value1(String init_custom_field_value1) {
        this.init_custom_field_value1 = init_custom_field_value1;
    }
}
