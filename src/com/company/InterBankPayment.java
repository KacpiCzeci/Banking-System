package com.company;

public class InterBankPayment {
    private Bank myBank;
    private Bank paymentBank;
    private Transaction transaction;

    public InterBankPayment(Bank mB, Bank pB, Transaction t){
        this.myBank = mB;
        this.paymentBank = pB;
        this.transaction = t;
    }

    public Bank getMyBank() {
        return myBank;
    }

    public Bank getPaymentBank() {
        return paymentBank;
    }

    public Transaction getTransaction() {
        return transaction;
    }
}
