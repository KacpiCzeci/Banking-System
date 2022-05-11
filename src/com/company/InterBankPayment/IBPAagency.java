package com.company.InterBankPayment;

import com.company.Bank;
import com.company.BankProduct.Account;
import com.company.Card.Card;
import com.company.Transaction.Transaction;
import com.company.TransferVerification.TransferVerification;

import java.util.ArrayList;

public class IBPAagency implements TransferVerification{
    //private final ArrayList<Bank> banks = new ArrayList<>();
    //    private TransferVerification transferVerification = new TransferVerification();
    private final ArrayList<InterBankPayment> interBankPayments = new ArrayList<>();

    public IBPAagency(){
    }

    public void addNewInterBankPayments(InterBankPayment interBankPayment){
        this.interBankPayments.add(interBankPayment);
    }

    public void doInterbankPayments(){
        for(InterBankPayment interBankPayment: interBankPayments){
            interBankPayment.getreceiverBank().takeInterbankPayment(interBankPayment.getTransaction());
        }
        interBankPayments.clear();
    }

    private Boolean verifyTransfer(InterBankPayment interBankPayment){
        return Boolean.TRUE;
    }

    @Override
    public Boolean verifyTransaction(Transaction transaction) {
        return null;
    }

    @Override
    public Boolean cardVerification(Card card, Account account) {
        return null;
    }

    @Override
    public Boolean bankVerification(Bank bank) {
        return null;
    }

    @Override
    public Boolean accountVerification(Account account, Bank bank) {
        return null;
    }

    @Override
    public Boolean moneyVerification(Double amount, Account account) {
        return null;
    }
}
