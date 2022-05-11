package com.company.InterBankPayment;

import com.company.Bank;
import com.company.Transaction.SpecificTransactions.InterBankTransaction;
import com.company.Transaction.Transaction;

public class InterBankPayment {
    private final Bank senderBank;
    private final Bank receiverBank;
    private final InterBankTransaction transaction;

    public InterBankPayment(Bank senderBank, Bank receiverBank, InterBankTransaction transaction){
        this.senderBank = senderBank;
        this.receiverBank = receiverBank;
        this.transaction = transaction;
    }

    public Bank getsenderBank() {
        return senderBank;
    }

    public Bank getreceiverBank() {
        return receiverBank;
    }

    public InterBankTransaction getTransaction() {
        return transaction;
    }
}
