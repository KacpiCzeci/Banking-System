package com.company;

import java.time.LocalDateTime;

public class Transaction {
    private Account sender;
    private Account receiver;
    private LocalDateTime dateOfTransaction;
    private Double amount;
    private Card card;

    public Transaction(Account s, Account r, Double amt){
        this.sender = s;
        this.receiver = r;
        this.dateOfTransaction = LocalDateTime.now();
        this.amount = amt;
        this.card = null;
    }

    public Transaction(Account s, Account r, Card c, Double amt){
        this.sender = s;
        this.receiver = r;
        this.dateOfTransaction = LocalDateTime.now();
        this.amount = amt;
        this.card = c;
    }

    public Account getSender() {
        return sender;
    }

    public Account getReceiver() {
        return receiver;
    }

    public LocalDateTime getDateOfTransaction() {
        return dateOfTransaction;
    }

    public Double getAmount() {
        return amount;
    }

    public Card getCard() {
        return card;
    }
}
