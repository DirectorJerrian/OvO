package com.example.hotel.vo;

public class ChargeVO {
    private String email;
    private String phoneNumber;
    private int money;

    public int getMoney() {
        return money;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}

