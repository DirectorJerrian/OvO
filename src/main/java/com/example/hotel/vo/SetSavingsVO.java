package com.example.hotel.vo;

public class SetSavingsVO {
    private int cardId;
    private double savings;

    public double getSavings() {
        return savings;
    }

    public void setSavings(double savings) {
        this.savings = savings;
    }

    public int getCardId() {
        return cardId;
    }

    public void setUserId(int userId) {
        this.cardId = userId;
    }
}

