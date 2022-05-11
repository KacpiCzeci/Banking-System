package com.company.Transaction.SpecificTransactions;

import com.company.Transaction.CashTransaction;
import com.company.Transaction.TransactionType;

import java.math.BigDecimal;

public class PaymentTransaction extends CashTransaction {
    private final String senderID;
    private final String receiverID;

    public PaymentTransaction(TransactionType type, BigDecimal amount, String senderID, String receiverID){
        super(type, amount);
        this.senderID = senderID;
        this.receiverID = receiverID;
    }

    public String getSenderID() {
        return senderID;
    }

    public String getReceiverID() {
        return receiverID;
    }

    @Override
    protected String createDescription() {
        return "Payment from " + this.senderID + " to " + this.receiverID + " in amount of " + this.getAmount().toString() + ".";
    }
}
