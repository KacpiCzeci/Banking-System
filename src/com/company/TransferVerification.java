package com.company;

import javax.swing.text.StyledEditorKit;
import java.util.ArrayList;

public class TransferVerification {
    private ArrayList<Bank> banks;

    public TransferVerification(){
        this.banks = new ArrayList<Bank>();
    }

    public Boolean verifyTransaction(Transaction transaction){
        if(!this.bankVerification(transaction.getSender().getMyBank())){
            return false;
        }
        if(!this.bankVerification(transaction.getReceiver().getMyBank())){
            return false;
        }
        if(!this.accountVerification(transaction.getSender(), transaction.getSender().getMyBank())){
            return false;
        }
        if(!this.accountVerification(transaction.getReceiver(), transaction.getReceiver().getMyBank())){
            return false;
        }
        if(!(transaction.getCard() == null) && !this.cardVerification(transaction.getCard(), transaction.getSender())){
            return false;
        }
        if(!this.moneyVerification(transaction.getAmount(), transaction.getSender())){
            return false;
        }
        return true;
    }

    public ArrayList<Bank> getBanks() {
        return banks;
    }

    public void addBank(Bank bank) {
        this.banks.add(bank);
    }

    private Boolean cardVerification(Card card, Account account){
        for (Card c: account.getCards()) {
            if(c.equals(card) && c.getNumber().equals(card.getNumber())){
                return true;
            }
        }
        return false;
    }

    private Boolean bankVerification(Bank bank){
        for (Bank b: banks) {
           if(b.equals(bank) && b.getId().equals(bank.getId())){
               return true;
           }
        }
        return false;
    }

    private Boolean accountVerification(Account account, Bank bank){
        for (Account a: bank.getAccounts()) {
            if(a.equals(account) && a.getId().equals(account.getId())){
                return true;
            }
        }
        return false;
    }

    private Boolean moneyVerification(Double amount, Account account){
        if(amount >= account.getTotalMoney()){
            return true;
        }
        return false;
    }
}
