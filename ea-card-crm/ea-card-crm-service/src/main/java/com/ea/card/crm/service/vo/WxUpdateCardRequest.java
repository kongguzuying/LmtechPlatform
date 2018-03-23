package com.ea.card.crm.service.vo;

/**
 * 微信更新卡片请求
 * @author
 */
public class WxUpdateCardRequest {

    public static class NotifyOptional {
        private boolean is_notify_bonus;
        private boolean is_notify_balance;
        private boolean is_notify_custom_field1;

        public boolean isIs_notify_bonus() {
            return is_notify_bonus;
        }

        public void setIs_notify_bonus(boolean is_notify_bonus) {
            this.is_notify_bonus = is_notify_bonus;
        }

        public boolean isIs_notify_balance() {
            return is_notify_balance;
        }

        public void setIs_notify_balance(boolean is_notify_balance) {
            this.is_notify_balance = is_notify_balance;
        }

        public boolean isIs_notify_custom_field1() {
            return is_notify_custom_field1;
        }

        public void setIs_notify_custom_field1(boolean is_notify_custom_field1) {
            this.is_notify_custom_field1 = is_notify_custom_field1;
        }
    }

    private String code;
    private String card_id;
    private String background_pic_url;
    private String record_bonus;
    private Long bonus;
    private Long add_bonus;
    private String record_balance;
    private Double balance;
    private String custom_field_value1;
    private String custom_field_value2;
    private NotifyOptional notify_optional;

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

    public String getRecord_bonus() {
        return record_bonus;
    }

    public void setRecord_bonus(String record_bonus) {
        this.record_bonus = record_bonus;
    }

    public String getRecord_balance() {
        return record_balance;
    }

    public void setRecord_balance(String record_balance) {
        this.record_balance = record_balance;
    }

    public Long getBonus() {
        return bonus;
    }

    public void setBonus(Long bonus) {
        this.bonus = bonus;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getCustom_field_value1() {
        return custom_field_value1;
    }

    public void setCustom_field_value1(String custom_field_value1) {
        this.custom_field_value1 = custom_field_value1;
    }

    public String getCustom_field_value2() {
        return custom_field_value2;
    }

    public void setCustom_field_value2(String custom_field_value2) {
        this.custom_field_value2 = custom_field_value2;
    }

    public NotifyOptional getNotify_optional() {
        return notify_optional;
    }

    public void setNotify_optional(NotifyOptional notify_optional) {
        this.notify_optional = notify_optional;
    }

    public Long getAdd_bonus() {
        return add_bonus;
    }

    public void setAdd_bonus(Long add_bonus) {
        this.add_bonus = add_bonus;
    }
}
