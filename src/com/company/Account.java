package com.company;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Account {
    protected Integer id;
    protected String owner;
    protected LocalDateTime dateOfOpening;
    protected ArrayList<Transaction> historyOfOperations;
    protected Double totalMoney;
    protected ArrayList<Deposit> deposits;
    protected ArrayList<Loan> loans;
    protected ArrayList<Card> cards;
    protected Bank myBank;
    protected TransferVerification transferVerification;

    public Account(Bank bank, String owner, TransferVerification trVr){
        this.owner = owner;
        this.dateOfOpening = LocalDateTime.now();
        this.historyOfOperations = new ArrayList<>();
        this.totalMoney = 0.00;
        this.deposits = new ArrayList<>();
        this.loans = new ArrayList<>();
        this.cards = new ArrayList<>();
        this.myBank = bank;
        this.transferVerification = trVr;
    }

    public Integer getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public LocalDateTime getDateOfOpening() {
        return dateOfOpening;
    }

    public ArrayList<Transaction> getHistoryOfOperations() {
        return historyOfOperations;
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public Bank getMyBank() {
        return myBank;
    }

    public ArrayList<Deposit> getDeposits() {
        return deposits;
    }

    public ArrayList<Loan> getLoans() {
        return loans;
    }

    public void depositMoney(Double amount){
        this.totalMoney += amount;
    }

    public Boolean makeTransaction(Account recv, Double amount, Card card){
        Transaction transaction;
        if(card == null){
            transaction = new Transaction(this, recv, amount);
        }
        else{
            transaction = new Transaction(this, recv, card, amount);
        }
        if(transferVerification.verifyTransaction(transaction)){
            this.historyOfOperations.add(transaction);
            return true;
        }
        return false;
    }

    public void takePayment(Transaction transaction){
        this.historyOfOperations.add(transaction);
        this.totalMoney += transaction.getAmount();
    }

    public Double withdrawMoney(Double amount){
        if(!this.makeTransaction(this, amount, null)){
            return -1.0;
        }
        if(this.totalMoney - amount >= 0.00){
            this.totalMoney -= amount;
            return amount;
        }
        else {
            return 0.00;
        }
    }

    public void addDeposit(Long id, Double amount, Boolean fromAccount){
        this.deposits.add(new Deposit(id, amount, LocalDateTime.now(), myBank.getInterestRate()));
        if(fromAccount){
            this.totalMoney -= amount;
        }
    }

    public void closeDeposit(Long id){
        for (Deposit deposit: this.deposits) {
            if(deposit.getId().equals(id)){
                this.totalMoney += deposit.withdrawMoney();
                this.deposits.remove(deposit);
                return;
            }
        }
    }

    public void addLoan(Long id, Double amount){
        this.loans.add(new Loan(id, amount, LocalDateTime.now(), myBank.getInterestRate()));
        this.totalMoney += amount;
    }

    public void closeLoan(Long id){
        for (Loan loan: this.loans) {
            if(loan.getId().equals(id)){
                Double installment = loan.getLoan();
                if(this.totalMoney >= installment){
                    loan.payLoan();
                    this.totalMoney -= installment;
                }
                return;
            }
        }
    }

    public void addCard(Integer id, Integer cvc, LocalDateTime expDate){
        this.cards.add(new Card(this, id, cvc, expDate));
    }

    public void removeCard(Integer id){
        this.cards.removeIf(card -> card.getNumber().equals(id));
    }

    public void useCardPayment(Card card, Account recv, Double amount){
        if(!this.makeTransaction(recv, amount, card)){
            return;
        }
        this.totalMoney -= amount;
    }
}
