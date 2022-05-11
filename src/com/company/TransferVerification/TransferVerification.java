package com.company.TransferVerification;

import com.company.Bank;
import com.company.BankProduct.Account;
import com.company.Card.Card;
import com.company.Transaction.Transaction;

import java.util.ArrayList;

public interface TransferVerification {

    public Boolean verifyTransaction(Transaction transaction);
//    {
//        if(!this.bankVerification(transaction.getSender().getMyBank())){
//            return false;
//        }
//        if(!this.bankVerification(transaction.getReceiver().getMyBank())){
//            return false;
//        }
//        if(!this.accountVerification(transaction.getSender(), transaction.getSender().getMyBank())){
//            return false;
//        }
//        if(!this.accountVerification(transaction.getReceiver(), transaction.getReceiver().getMyBank())){
//            return false;
//        }
//        if(!(transaction.getCard() == null) && !this.cardVerification(transaction.getCard(), transaction.getSender())){
//            return false;
//        }
//        if(!this.moneyVerification(transaction.getAmount(), transaction.getSender())){
//            return false;
//        }
//        return true;
//    }

    public Boolean cardVerification(Card card, Account account);
//    {
//        for (Card c: account.getCards()) {
//            if(c.equals(card) && c.getNumber().equals(card.getNumber())){
//                return true;
//            }
//        }
//        return false;
//    }

    public Boolean bankVerification(Bank bank);
//    {
//        for (Bank b: banks) {
//           if(b.equals(bank) && b.getId().equals(bank.getId())){
//               return true;
//           }
//        }
//        return false;
//    }

    public Boolean accountVerification(Account account, Bank bank);
//    {
//        for (Account a: bank.getAccounts()) {
//            if(a.equals(account) && a.getId().equals(account.getId())){
//                return true;
//            }
//        }
//        return false;
//    }

    public Boolean moneyVerification(Double amount, Account account);
//    {
//        if(amount >= account.getTotalMoney()){
//            return true;
//        }
//        return false;
//    }
}
