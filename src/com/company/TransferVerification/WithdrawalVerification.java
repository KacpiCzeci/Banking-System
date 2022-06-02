package com.company.TransferVerification;

import com.company.Bank;
import com.company.Transaction.ConcreteCommands.PaymentCommand;
import com.company.Transaction.ConcreteCommands.WithdrawalCommand;
import com.company.Transaction.TransactionCommand;

public class WithdrawalVerification implements  TransferVerification{
    public WithdrawalVerification(){
    }

    public Boolean moneyVerification(WithdrawalCommand withdrawalCommand)
    {
        return withdrawalCommand.getAmount().compareTo(withdrawalCommand.getSender().getBalance()) <= 0;
    }

    @Override
    public Boolean verifyTransaction(TransactionCommand transaction) {
        WithdrawalCommand withdrawalCommand = (WithdrawalCommand) transaction;
        if(!this.moneyVerification(withdrawalCommand)){
            return false;
        }
        return true;
    }
}
