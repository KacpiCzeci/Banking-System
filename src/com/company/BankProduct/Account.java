package com.company.BankProduct;

import com.company.*;
import com.company.BankProduct.Data.DepositData;
import com.company.BankProduct.Data.LoanData;
import com.company.Card.Card;
import com.company.Report.ReportVisitor;
import com.company.Transaction.ConcreteCommands.InterBankCommand;
import com.company.Transaction.TransactionCommand;
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

    @Override
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
        if(this.balance.compareTo(money) >= 0 && money.compareTo(new BigDecimal("0.00")) >= 0){
            this.balance = new BigDecimal(String.valueOf(this.balance.subtract(money)));
            return money;
        }
        else{
            return new BigDecimal("0.00");
        }
    }

    @Override
    public void receiveMoney(BigDecimal money) {
        if(money.compareTo(new BigDecimal("0.00")) >= 0){
            this.balance = new BigDecimal(String.valueOf(this.balance.add(money)));
        }
    }

    @Override
    public void doTransaction(TransactionCommand transactionCommand) {
        transactionCommand.execute();
    }

    @Override
    public void handleFailure(TransactionCommand transactionCommand) {
        if(transactionCommand.getType().equals(TransactionType.INTERBANKINCOME)){
            InterBankCommand interBankCommand = (InterBankCommand) transactionCommand;
            this.receiveMoney(interBankCommand.getAmount());
        }
    }

    @Override
    public void acceptVisitor(ReportVisitor reportVisitor) {
        reportVisitor.visitBankProduct(this);
    }

    public void openDeposit(String id, Integer time){
        DepositData depositData = new DepositData(id, this.bank, BankProductType.DEPOSIT, this.owner, new BigDecimal("0.00"), LocalDateTime.now().plusMonths(time));
        Deposit deposit = (Deposit) this.bank.createBankProduct(BankProductType.DEPOSIT, depositData);
        this.deposits.add(deposit);
    }

    public void deposeMoneyToDeposit(String id, BigDecimal amount){
        Deposit deposit = this.deposits.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
        if(deposit != null && this.balance.compareTo(amount) >= 0 && amount.compareTo(new BigDecimal("0.00")) >= 0){
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
        if(loan != null && amount.compareTo(new BigDecimal("0.00")) >= 0){
            this.receiveMoney(loan.takeLoan(amount));
        }
    }

    public void closeLoan(String id){
        Loan loan = this.loans.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
        if(loan != null && loan.getStatus().equals(BankProductStatus.ACTIVE) && this.balance.compareTo(loan.getBalance()) >= 0){
            this.withdrawMoney(loan.payLoan());
        }
    }

    public void addCard(Card card){
        this.cards.add(card);
    }

    public void removeCard(Long id){
        this.cards.stream().filter(product -> product.getNumber().equals(id)).findFirst().ifPresent(card -> card.setStatus(BankProductStatus.CLOSED));
    }

    public void useCardPayment(Long number, BigDecimal amount){
        Card card = this.cards.stream().filter(product -> product.getNumber().equals(number)).findFirst().orElse(null);
        if(card != null && card.getStatus().equals(BankProductStatus.ACTIVE) && this.balance.compareTo(amount) >= 0){
            this.withdrawMoney(amount);
        }
    }
}
