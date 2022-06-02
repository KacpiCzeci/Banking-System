package com.company.TransferVerification;

import com.company.Bank;
import com.company.BankProduct.BankProduct;
import com.company.BankProduct.BankProductType;
import com.company.Transaction.ConcreteCommands.PaymentCommand;
import com.company.Transaction.TransactionCommand;

public class PaymentVerification implements TransferVerification{
    private final Bank bank;

    public PaymentVerification(Bank bank){
        this.bank = bank;
    }

    public Boolean accountVerification(PaymentCommand paymentCommand)
    {
        BankProduct account = bank.getBankProduct(paymentCommand.getReceiver().getId(), paymentCommand.getReceiver().getType());
        return account != null;
    }

    public Boolean moneyVerification(PaymentCommand paymentCommand)
    {
        return paymentCommand.getAmount().compareTo(paymentCommand.getSender().getBalance()) <= 0;
    }

    @Override
    public Boolean verifyTransaction(TransactionCommand transaction) {
        PaymentCommand paymentTransaction = (PaymentCommand) transaction;
        if(!this.accountVerification(paymentTransaction)){
            return false;
        }
        if(!this.moneyVerification(paymentTransaction)){
            return false;
        }
        return true;
    }
}
