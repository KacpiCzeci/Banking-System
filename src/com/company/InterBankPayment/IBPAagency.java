package com.company.InterBankPayment;

import com.company.TransferVerification.TransferVerification;

import java.util.ArrayList;

public class IBPAagency {
    //private final ArrayList<Bank> banks = new ArrayList<>();
    private TransferVerification transferVerification = new TransferVerification();
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
}
