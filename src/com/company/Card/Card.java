package com.company.Card;

import com.company.BankProduct.Account;
import com.company.BankProduct.BankProductStatus;
import com.company.Transaction.SpecificTransactions.PaymentTransaction;
import com.company.Transaction.Transaction;
import com.company.Transaction.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Card {
    private final String id;
    private final Account myAccount;
    private final Long number;
    private final Integer cvc;
    private final LocalDateTime expirationDate;
    protected BankProductStatus status = BankProductStatus.ACTIVE;
    protected final LocalDateTime dateOfOpening = LocalDateTime.now();
    protected final ArrayList<Transaction> historyOfOperations = new ArrayList<>();

    public Card(String id, Account acc, Long num, Integer cvc, LocalDateTime expD){
        this.id = id;
        this.myAccount = acc;
        this.number = num;
        this.cvc = cvc;
        this.expirationDate = expD;
    }

    public Account getMyAccount(){
        return this.myAccount;
    }

    public Long getNumber() {
        return number;
    }

    public Integer getCvc() {
        return cvc;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public String getId() {
        return id;
    }

    public ArrayList<Transaction> getHistoryOfOperations() {
        return historyOfOperations;
    }

    public BankProductStatus getStatus() {
        return status;
    }

    public LocalDateTime getDateOfOpening() {
        return dateOfOpening;
    }

    public void setStatus(BankProductStatus status) {
        this.status = status;
    }

    public void payByCard(String receiverID, BigDecimal amount){
        PaymentTransaction paymentTransaction = new PaymentTransaction(TransactionType.PAYMENT, amount, this.myAccount.getId(), receiverID);
        this.historyOfOperations.add(paymentTransaction);
        this.myAccount.getBank().addToHistory(paymentTransaction);
    }
}
