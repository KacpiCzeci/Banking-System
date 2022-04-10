package com.company;

import java.time.LocalDateTime;

public class Card {
    private Account myAccount;
    private Integer number;
    private Integer cvc;
    private LocalDateTime expirationDate;

    public Card(Account acc, Integer num, Integer cvc, LocalDateTime expD){
        this.myAccount = acc;
        this.number = num;
        this.cvc = cvc;
        this.expirationDate = expD;
    }

    public Account getMyAccount(){
        return this.myAccount;
    }

    public Integer getNumber() {
        return number;
    }

    public Integer getCvc() {
        return cvc;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void payByCard(Double amount, Account recv){
        myAccount.useCardPayment(this, recv, amount);
    }
}
