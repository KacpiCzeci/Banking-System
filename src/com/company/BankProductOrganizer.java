package com.company;

import com.company.BankProduct.*;

import java.util.ArrayList;

public class BankProductOrganizer {
    private final ArrayList<Account> accounts = new ArrayList<>();
    private final ArrayList<DebitAccount> debitAccounts = new ArrayList<>();
    private final ArrayList<Deposit> deposits = new ArrayList<>();
    private final ArrayList<Loan> loans = new ArrayList<>();

    public BankProductOrganizer(){
    }

    public void addBankProduct(BankProduct product){
        switch (product.getType()){
            case ACCOUNT:
                accounts.add((Account) product);
                break;
            case DEBITACCOUNT:
                debitAccounts.add((DebitAccount) product);
                break;
            case DEPOSIT:
                deposits.add((Deposit) product);
                break;
            case LOAN:
                loans.add((Loan) product);
                break;
            default:
                break;
        }
    }

    public BankProduct returnProduct(String id, BankProductType type){
        switch (type){
            case ACCOUNT:
                return accounts.stream().filter(product -> product.getId().equals(id) && product.getType().equals(BankProductType.ACCOUNT)).findFirst().orElse(null);
            case DEBITACCOUNT:
                return debitAccounts.stream().filter(product -> product.getId().equals(id) && product.getType().equals(BankProductType.DEBITACCOUNT)).findFirst().orElse(null);
            case DEPOSIT:
                return deposits.stream().filter(product -> product.getId().equals(id) && product.getType().equals(BankProductType.DEPOSIT)).findFirst().orElse(null);
            case LOAN:
                return loans.stream().filter(product -> product.getId().equals(id) && product.getType().equals(BankProductType.LOAN)).findFirst().orElse(null);
            default:
                return null;
        }
    }

    public void deleteProduct(String id, BankProductType type){
        switch (type){
            case ACCOUNT:
                Account account = accounts.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
                if (account != null && type == account.getType()){
                    account.setStatus(BankProductStatus.CLOSED);
                }
                break;
            case DEBITACCOUNT:
                DebitAccount debitAccount = debitAccounts.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
                if (debitAccount != null && type == debitAccount.getType()){
                    debitAccount.setStatus(BankProductStatus.CLOSED);
                }
                break;
            case DEPOSIT:
                Deposit deposit = deposits.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
                if (deposit != null && type == deposit.getType()){
                    deposit.setStatus(BankProductStatus.CLOSED);
                }
                break;
            case LOAN:
                Loan loan = loans.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
                if (loan != null && type == loan.getType()){
                    loan.setStatus(BankProductStatus.CLOSED);
                }
                break;
            default:
                break;
        }
    }

    public ArrayList<Loan> getLoans() {
        return loans;
    }

    public ArrayList<Deposit> getDeposits() {
        return deposits;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public ArrayList<DebitAccount> getDebitAccounts() {
        return debitAccounts;
    }
}
