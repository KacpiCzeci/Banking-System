package com.company;

import java.util.Date;

public class Card {
    private Account myAccount;
    private Integer number;
    private Integer cvc;
    private Date expirationDate;

    public Card(Account acc, Integer num, Integer cvc, Date expD){
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

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void payByCard(){
        myAccount.useCardPayment();
    }
}
