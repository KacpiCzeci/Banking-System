package com.company.TransferVerification;

import com.company.Bank;
import com.company.Transaction.TransactionType;

public class TransferVerificationCreator {
    private final Bank bank;

    public TransferVerificationCreator(Bank bank){
        this.bank = bank;
    }

    public TransferVerification create(TransactionType type){
        switch (type){
            case PAYMENT:
                return new PaymentVerification(bank);
            case WITHDRAWAL:
                return new WithdrawalVerification();
            case CLOSEDEPOSIT:
                return null;
            case OPENDEPOSIT:
                return null;
            case CLOSELOAN:
                return null;
            case OPENLOAN:
                return null;
            case CLOSEACCOUNT:
                return null;
            case OPENACCOUNT:
                return null;
            case INTERESTRATECALCULATION:
                return null;
            case REPORT:
                return null;
            case DEPOSE:
                return null;
            case CHANGEOFINTERESTRATE:
                return null;
            case RECEIVE:
                return null;
            case INTERBANKPAYMENT:
                return null;
            case INTERBANKINCOME:
                return null;
            case DEBIT:
                return null;
            case FAILURE:
                return null;
            case ADDCARD:
                return null;
            case REMOVECARD:
                return null;
            case USECARD:
                return null;
            default:
                return null;
        }
    }
}
