package com.company.BankProduct;

import com.company.Bank;
import com.company.BankProduct.Data.DepositData;
import com.company.BankProduct.Data.LoanData;
import com.company.Card.Card;
import com.company.Report.ReportVisitor;
import com.company.Transaction.TransactionCommand;
import com.company.TransferVerification.TransferVerification;
import com.company.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DebitAccount extends BankProductDecorator {
    private BigDecimal debitLimit = new BigDecimal("0.00");
    private BigDecimal debit = new BigDecimal("0.00");

    public DebitAccount(String id, Bank bank, User owner, Account bankProduct) {
        super(id, bank, BankProductType.DEBITACCOUNT, owner, BankProductStatus.ACTIVE, bankProduct);
    }

    //
    @Override
    public ArrayList<TransactionCommand> getHistoryOfOperations() {
        ArrayList<TransactionCommand> accountHist = ((Account) this.bankProduct).getHistoryOfOperations();
        ArrayList<TransactionCommand> debitHist = this.historyOfOperations.getHistory();
        accountHist.addAll(debitHist);
        return accountHist;
    }

    /**
     * Account function
     *
     * @return balance of account
     */
    @Override
    public BigDecimal getBalance() {
        return this.bankProduct.getBalance();
    }

    /**
     * Account function
     * @param balance
     */
    @Override
    public void setBalance(BigDecimal balance) {
        this.bankProduct.setBalance(balance);
    }

    /**
     * Account function
     *
     * @return ArrayList Card
     */
    public ArrayList<Card> getCards() {
        return ((Account) this.bankProduct).cards;
    }

    /**
     * Account function
     *
     * @return ArrayList Deposit
     */
    public ArrayList<Deposit> getDeposits() {
        return ((Account) this.bankProduct).deposits;
    }

    /**
     * Account function
     *
     * @param id id of deposit
     * @return Deposit
     */
    public Deposit getDeposit(String id) {
        return ((Account) this.bankProduct).deposits.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
    }

    /**
     * Account function
     *
     * @return ArrayList Loan
     */
    public ArrayList<Loan> getLoans() {
        return ((Account) this.bankProduct).loans;
    }

    /**
     * Account function
     *
     * @param id id of loan
     * @return Loan
     */
    public Loan getLoan(String id) {
        return ((Account) this.bankProduct).loans.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
    }

    public TransferVerification getTransferVerification() {
        return ((Account) this.bankProduct).getTransferVerification();
    }

    public BigDecimal getDebitLimit() {
        return this.debitLimit;
    }

    public void setDebitLimit(BigDecimal debit) {
        this.debitLimit = debit;
    }

    public BigDecimal getDebit() {
        return this.debit;
    }

    @Override
    public void receiveMoney(BigDecimal amount) {
        if (this.debit.compareTo(new BigDecimal("0.00")) > 0 && amount.compareTo(new BigDecimal("0.00"))>=0) {
            if (this.debit.compareTo(amount) <= 0) {
                this.bankProduct.receiveMoney(amount.subtract(this.debit));
                this.debit = new BigDecimal("0.00");
            }
            if (this.debit.compareTo(amount) > 0) {
                this.debit = this.debit.subtract(amount);
            }
        }
    }

    @Override
    public BigDecimal withdrawMoney(BigDecimal amount) {
        if(amount.compareTo(new BigDecimal("0.00"))>=0) {
            if (this.bankProduct.getBalance().compareTo(amount) < 0) {
                BigDecimal diff = amount.subtract(this.bankProduct.getBalance());
                if (diff.add(debit).compareTo(this.debitLimit) <= 0) {
                    this.debit = this.debit.add(diff);
                    BigDecimal moneyFromAccount = this.bankProduct.withdrawMoney(this.bankProduct.getBalance());
                    return moneyFromAccount.add(diff);
                } else {
                    return null;
                }
            } else {
                return this.bankProduct.withdrawMoney(amount);
            }
        }else{
            return new BigDecimal("0.00");
        }
    }

    @Override
    public void doTransaction(TransactionCommand transactionCommand) {
        transactionCommand.execute();
    }

    @Override
    public void handleFailure(TransactionCommand transactionCommand) {
        ((Account) this.bankProduct).handleFailure(transactionCommand);
    }

    @Override
    public void acceptVisitor(ReportVisitor reportVisitor) {
        reportVisitor.visitBankProduct(this);
    }

    /**
     * Account function
     * @param id
     * @param time
     */
    public void openDeposit(String id, Integer time){
        ((Account) this.bankProduct).openDeposit(id, time);
    }

    /**
     * Account function
     *
     * @param id     id of deposit
     * @param amount how much money
     */
    public void deposeMoneyToDeposit(String id, BigDecimal amount) {
        ((Account) this.bankProduct).deposeMoneyToDeposit(id, amount);
    }

    /**
     * Account function
     *
     * @param id id of deposit
     */
    public void closeDeposit(String id) {
        ((Account) this.bankProduct).closeDeposit(id);
    }

    /**
     * Account function
     *
     * @param id   id of loan
     * @param time for how long
     */
    public void openLoan(String id, Integer time) {
        ((Account) this.bankProduct).openLoan(id, time);
    }

    /**
     * Account function
     *
     * @param id id of loan
     * @param amount how much money
     */
    public void takeMoneyFromLoan(String id, BigDecimal amount) {
        ((Account) this.bankProduct).takeMoneyFromLoan(id, amount);
    }

    /**
     * Account function
     * @param id
     */
    public void closeLoan(String id) {
        ((Account) this.bankProduct).closeLoan(id);
    }

    /**
     * Account function
     * @param card new card
     */
    public void addCard(Card card){
        ((Account) this.bankProduct).addCard(card);
    }

    /**
     * Account function
     * @param id id of card
     */
    public void removeCard(Long id){
        ((Account) this.bankProduct).removeCard(id);
    }

    /**
     *
     * @param number accoutn number
     * @param amount how much
     */
    public void useCardPayment(Long number, BigDecimal amount){
        ((Account) this.bankProduct).useCardPayment(number,amount);
    }
}