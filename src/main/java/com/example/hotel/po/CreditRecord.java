package com.example.hotel.po;

public class CreditRecord {
    private Integer id;
    private Integer userId;
    private String operationDate;
    private Integer orderId;
    private String operation;
    private double credit_delta;
    private double credit_result;

    public Integer getId() {
        return id;
    }

    public String getOperationDate() {
        return operationDate;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public double getCredit_delta() {
        return credit_delta;
    }

    public double getCredit_result() {
        return credit_result;
    }

    public String getOperation() {
        return operation;
    }

    public void setCredit_delta(double credit_delta) {
        this.credit_delta = credit_delta;
    }

    public void setCredit_result(double credit_result) {
        this.credit_result = credit_result;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setOperationDate(String operationDate) {
        this.operationDate = operationDate;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }
}
