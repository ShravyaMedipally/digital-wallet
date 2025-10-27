package com.walletapp.dto;

public class AddMoneyRequest {
    private String username;
    private Double amount;

    // getters & setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
}

