package com.example.hotel.po;

public class Vipcard {
    private Integer cardId;
    private Integer userId;
    private double vip_credit;
    private Integer level;
    private String card_type;
    private double savings;
    private  String info;

    public String getInfo(){return info;}

    public void setInfo(String info){this.info=info;}

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public double getVip_credit() {
        return vip_credit;
    }

    public Integer getLevel() {
        return level;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getCard_type() {
        return card_type;
    }

    public double getSavings() {
        return savings;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setVip_credit(double vip_credit) {
        this.vip_credit = vip_credit;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public void setSavings(double savings) {
        this.savings = savings;
    }

}
