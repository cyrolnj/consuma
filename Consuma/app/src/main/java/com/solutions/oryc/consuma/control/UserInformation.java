package com.solutions.oryc.consuma.control;

public class UserInformation {

    private String userId;
    private double currentCredit;

    public String getUserId() { return userId; }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getCurrentCredit() {
        return currentCredit;
    }

    public void setCurrentCredit(double currentCredit) {
        this.currentCredit = currentCredit;
    }
}
