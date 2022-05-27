package com.company.TransferVerification;

import com.company.Bank;
import com.company.Transaction.ConcreteCommands.PaymentCommand;
import com.company.Transaction.TransactionCommand;

public class WithdrawalVerification implements  TransferVerification{
    public WithdrawalVerification(){
    }

    public Boolean moneyVerification(PaymentCommand paymentCommand)
    {
        return paymentCommand.getAmount().compareTo(paymentCommand.getSender().getBalance()) <= 0;
    }

    @Override
    public Boolean verifyTransaction(TransactionCommand transaction) {
        PaymentCommand paymentTransaction = (PaymentCommand) transaction;
        if(!this.moneyVerification(paymentTransaction)){
            return false;
        }
        return true;
    }
}
