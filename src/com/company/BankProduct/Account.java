package com.company.BankProduct;

import com.company.*;
import com.company.BankProduct.Data.DepositData;
import com.company.BankProduct.Data.LoanData;
import com.company.Card.Card;
import com.company.Transaction.TransactionCommand;
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

    public ArrayList<TransactionCommand> getHistoryOfOperations() {
        return this.historyOfOperations.getHistory();
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public ArrayList<Deposit> getDeposits() {
        return deposits;
    }

    public Deposit getDeposit(String id){
        return this.deposits.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
    }

    public ArrayList<Loan> getLoans() {
        return loans;
    }

    public Loan getLoan(String id){
        return this.loans.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
    }

    public TransferVerification getTransferVerification() { return transferVerification; }

    @Override
    public BigDecimal withdrawMoney(BigDecimal money) {
        if(this.balance.compareTo(money) >= 0){
            this.balance = new BigDecimal(String.valueOf(this.balance.subtract(money)));
            return money;
        }
        else{
            return new BigDecimal("0.00");
        }
    }

    @Override
    public void receiveMoney(BigDecimal money) {
        this.balance = new BigDecimal(String.valueOf(this.balance.add(money)));
    }

    @Override
    public void doTransaction(TransactionCommand transactionCommand) {
        transactionCommand.execute();
    }

    public void openDeposit(String id, Integer time){
        DepositData depositData = new DepositData(id, this.bank, BankProductType.DEPOSIT, this.owner, new BigDecimal("0.00"), LocalDateTime.now().plusMonths(time));
        Deposit deposit = (Deposit) this.bank.createBankProduct(BankProductType.DEPOSIT, depositData);
        this.deposits.add(deposit);
    }

    public void deposeMoneyToDeposit(String id, BigDecimal amount){
        Deposit deposit = this.deposits.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
        if(deposit != null && this.balance.compareTo(amount) >= 0){
            deposit.depositMoney(amount);
            this.withdrawMoney(amount);
        }
    }

    public void closeDeposit(String id){
        Deposit deposit = this.deposits.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
        if(deposit != null && deposit.getStatus().equals(BankProductStatus.ACTIVE)){
            this.receiveMoney(deposit.closeDeposit());
        }
    }

    public void openLoan(String id, Integer time){
        LoanData loanData = new LoanData(id, this.bank, BankProductType.LOAN, this.owner, new BigDecimal("0.00"), LocalDateTime.now().plusMonths(time));
        Loan loan = (Loan) this.bank.createBankProduct(BankProductType.LOAN, loanData);
        this.loans.add(loan);
    }

    public void takeMoneyFromLoan(String id, BigDecimal amount){
        Loan loan = this.loans.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
        if(loan != null){
            this.receiveMoney(loan.takeLoan(amount));
        }
    }

    public void closeLoan(String id){
        Loan loan = this.loans.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
        if(loan != null && loan.getStatus().equals(BankProductStatus.ACTIVE) && this.balance.compareTo(loan.getBalance()) >= 0){
            this.withdrawMoney(loan.payLoan());
        }
    }
//
//    public void addCard(String id, Long number, Integer cvc, LocalDateTime expDate){
//        this.cards.add(new Card(id, this, number, cvc, expDate));
//    }
//
//    public void removeCard(String id){
//        Card card = this.cards.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
//        if(card != null){
//            card.setStatus(BankProductStatus.CLOSED);
//        }
//    }
//
//    public void useCardPayment(String cardID, String receiveID, BigDecimal amount){
//        Card card = this.cards.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
//        if(card != null && card.getStatus().equals(BankProductStatus.ACTIVE) && this.balance.compareTo(amount) >= 0){
//            PaymentTransaction paymentTransaction = new PaymentTransaction(TransactionType.PAYMENT, amount, this.id, receiveID);
//            this.addOperationToHistory(paymentTransaction);
//            this.bank.addToHistory(paymentTransaction);
//            card.payByCard(receiveID, amount);
//            this.withdrawMoney(amount);
//        }
//    }
}
