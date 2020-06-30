package com.example.hotel.vo;

public class AvailableVoucherVO {
    private Integer targetMoney;
    private Integer userId;


    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setTargetMoney(int targetMoney) {
        this.targetMoney = targetMoney;
    }

    public Integer getTargetMoney() {
        return targetMoney;
    }
}
