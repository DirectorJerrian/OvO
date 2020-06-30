package com.example.hotel.po;

public class Voucher {
    private Integer id;
    private Integer userId;
    private String description;
    private double discount=1;
    private Integer target_money;
    private Integer discount_money;
    private Integer number=0;
    private String status;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getDiscount() {
        return discount;
    }

    public Integer getDiscount_money() {
        return discount_money;
    }

    public Integer getNumber() {
        return number;
    }

    public Integer getTarget_money() {
        return target_money;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public void setDiscount_money(Integer discount_money) {
        this.discount_money = discount_money;
    }

    public void setTarget_money(Integer target_money) {
        this.target_money = target_money;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
