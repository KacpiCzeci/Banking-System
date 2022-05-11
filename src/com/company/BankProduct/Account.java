package com.company.BankProduct;

import com.company.*;
import com.company.Card.Card;
import com.company.Transaction.CashTransaction;
import com.company.Transaction.SpecificTransactions.ClosingTransaction;
import com.company.Transaction.SpecificTransactions.OpeningTransaction;
import com.company.Transaction.SpecificTransactions.PaymentTransaction;
import com.company.Transaction.Transaction;
import com.company.Transaction.TransactionType;
import com.company.TransferVerification.TransferVerification;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Account extends BankProduct {
    protected final ArrayList<Deposit> deposits;
    protected final ArrayList<Loan> loans;
    protected final ArrayList<Card> cards;
    protected final TransferVerification transferVerification;

    public Account(String id, Bank bank, User user, TransferVerification trVr){
        super(id, bank, BankProductType.ACCOUNT, user, BankProductStatus.ACTIVE);
        this.deposits = new ArrayList<>();
        this.loans = new ArrayList<>();
        this.cards = new ArrayList<>();
        this.transferVerification = trVr;
    }

    public ArrayList<Transaction> getHistoryOfOperations() {
        return historyOfOperations;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public ArrayList<Deposit> getDeposits() {
        return deposits;
    }

    public ArrayList<Loan> getLoans() {
        return loans;
    }

    public TransferVerification getTransferVerification() { return transferVerification; }

    @Override
    protected BigDecimal withdrawMoney(BigDecimal money) {
        if(this.balance.compareTo(money) >= 0){
            this.balance = new BigDecimal(String.valueOf(this.balance.subtract(money)));
            return money;
        }
        else{
            return new BigDecimal("0.00");
        }
    }

    @Override
    protected void receiveMoney(BigDecimal money) {
        this.balance = new BigDecimal(String.valueOf(this.balance.add(money)));
    }

    public CashTransaction makePayment(BigDecimal amount, String receiverID){
        PaymentTransaction paymentTransaction = new PaymentTransaction(TransactionType.PAYMENT, amount, this.id, receiverID);
        this.addOperationToHistory(paymentTransaction);
        this.withdrawMoney(amount);
        this.bank.addToHistory(paymentTransaction);
        return paymentTransaction;
    }

    public void takePayment(CashTransaction transaction){
        this.addOperationToHistory(transaction);
        this.bank.addToHistory(transaction);
        this.receiveMoney(transaction.getAmount());
    }

    public void openDeposit(String id, Integer time){
        OpeningTransaction openingTransaction = new OpeningTransaction(TransactionType.OPENING, "Deposit");
        this.addOperationToHistory(openingTransaction);
        Deposit deposit = new Deposit(id, this.bank, this.owner, LocalDateTime.now().plusMonths(time));
        deposit.addOperationToHistory(openingTransaction);
        this.bank.addToHistory(openingTransaction);
        this.deposits.add(deposit);
    }

    public void deposeMoneyToDeposit(String id, BigDecimal amount){
        Deposit deposit = this.deposits.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
        if(deposit != null && this.balance.compareTo(amount) >= 0){
            PaymentTransaction paymentTransaction = new PaymentTransaction(TransactionType.PAYMENT, amount, this.id, this.id);
            this.addOperationToHistory(paymentTransaction);
            this.bank.addToHistory(paymentTransaction);
            deposit.depositMoney(amount);
            this.withdrawMoney(amount);
        }
    }

    public void closeDeposit(String id){
        Deposit deposit = this.deposits.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
        if(deposit != null && deposit.getStatus().equals(BankProductStatus.ACTIVE)){
            ClosingTransaction closingTransaction = new ClosingTransaction(TransactionType.CLOSING, "Deposit", "Regular closing");
            deposit.addOperationToHistory(closingTransaction);
            this.bank.addToHistory(closingTransaction);
            this.receiveMoney(deposit.closeDeposit());
        }
    }

    public void openLoan(String id, Integer time){
        OpeningTransaction openingTransaction = new OpeningTransaction(TransactionType.OPENING, "Loan");
        this.addOperationToHistory(openingTransaction);
        Loan loan = new Loan(id, this.bank, this.owner, LocalDateTime.now().plusMonths(time));
        loan.addOperationToHistory(openingTransaction);
        this.bank.addToHistory(openingTransaction);
        this.loans.add(loan);
    }

    public void takeMoneyFromLoan(String id, BigDecimal amount){
        Loan loan = this.loans.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
        if(loan != null){
            PaymentTransaction paymentTransaction = new PaymentTransaction(TransactionType.PAYMENT, amount, this.id, this.id);
            this.addOperationToHistory(paymentTransaction);
            this.bank.addToHistory(paymentTransaction);
            this.receiveMoney(loan.takeLoan(amount));
        }
    }

    public void closeLoan(String id){
        Loan loan = this.loans.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
        if(loan != null && loan.getStatus().equals(BankProductStatus.ACTIVE) && this.balance.compareTo(loan.getBalance()) >= 0){
            ClosingTransaction closingTransaction = new ClosingTransaction(TransactionType.CLOSING, "Loan", "Regular closing");
            loan.addOperationToHistory(closingTransaction);
            this.bank.addToHistory(closingTransaction);
            this.withdrawMoney(loan.payLoan());
        }
    }

    public void addCard(String id, Long number, Integer cvc, LocalDateTime expDate){
        this.cards.add(new Card(id, this, number, cvc, expDate));
    }

    public void removeCard(String id){
        Card card = this.cards.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
        if(card != null){
            card.setStatus(BankProductStatus.CLOSED);
        }
    }

    public void useCardPayment(String cardID, String receiveID, BigDecimal amount){
        Card card = this.cards.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
        if(card != null && card.getStatus().equals(BankProductStatus.ACTIVE) && this.balance.compareTo(amount) >= 0){
            PaymentTransaction paymentTransaction = new PaymentTransaction(TransactionType.PAYMENT, amount, this.id, receiveID);
            this.addOperationToHistory(paymentTransaction);
            this.bank.addToHistory(paymentTransaction);
            card.payByCard(receiveID, amount);
            this.withdrawMoney(amount);
        }
    }
}
