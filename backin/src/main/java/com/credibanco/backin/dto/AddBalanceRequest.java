package com.credibanco.backin.dto;

public class AddBalanceRequest {

    String cardId;

    Double balance;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardiId) {
        this.cardId = cardiId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
