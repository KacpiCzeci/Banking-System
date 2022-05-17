package com.company.InterBankPayment;

import com.company.Bank;
import com.company.Transaction.ConcreteCommands.FailureCommand;
import com.company.Transaction.ConcreteCommands.InterBankCommand;
import com.company.Transaction.TransactionCommand;
import com.company.Transaction.TransactionType;
import java.util.ArrayList;

public class IBPAagency {
    private final ArrayList<Bank> banks = new ArrayList<>();
//    private TransferVerification transferVerification = new TransferVerification();
//    private final ArrayList<InterBankCommand> interBankPayments = new ArrayList<>();

    public IBPAagency(){
    }

    public void addBank(Bank bank){
        this.banks.add(bank);
    }

//    public void addNewInterBankPayments(InterBankCommand interBankPayment){
//        this.interBankPayments.add(interBankPayment);
//    }

    public void doInterbankPayment(Bank sender, TransactionCommand event){
        if(event.getType().equals(TransactionType.INTERBANKPAYMENT)){
            InterBankCommand interBankPayment = (InterBankCommand) event;
            Bank bank = this.banks.stream().filter(b -> b.getId().equals(interBankPayment.getReceiverBank())).findFirst().orElse(null);
            if(bank != null){
                interBankPayment.setType(TransactionType.INTERBANKINCOME);
                bank.takeInterbankPayment(interBankPayment);
            }
            else{
                sender.takeFailure(new FailureCommand(TransactionType.FAILURE, event, interBankPayment.getAccount()));
            }
        }
        else if(event.getType().equals(TransactionType.FAILURE)){
            FailureCommand failure = (FailureCommand) event;
            if(failure.getCommand().getType().equals(TransactionType.INTERBANKINCOME)){
                InterBankCommand interBankPayment = (InterBankCommand) failure.getCommand();
                interBankPayment.getSenderBank().takeFailure(new FailureCommand(TransactionType.FAILURE, interBankPayment, interBankPayment.getAccount()));
            }
        }

    }
}
