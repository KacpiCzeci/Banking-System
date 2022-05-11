package com.company;

import com.company.BankProduct.*;
import com.company.BankProduct.Data.BankProductData;
import com.company.InterBankPayment.IBPAagency;
import com.company.InterBankPayment.InterBankPayment;
import com.company.Transaction.HistoryOfOperations;
import com.company.Transaction.SpecificTransactions.InterBankTransaction;
import com.company.Transaction.Transaction;

import java.util.ArrayList;

public class Bank {
    private final String id;
    private final String name;
    private final BankProductOrganizer bankOrganizer = new BankProductOrganizer();
    private final IBPAagency ibpaAgency;
    private final ArrayList<User> users = new ArrayList<>();
    private final HistoryOfOperations historyOfOperations = new HistoryOfOperations();
    private final BankProductFactory bankProductFactory = new BankProductFactory();

    public Bank(String id, String name, IBPAagency ibpaAgency){
        this.id = id;
        this.name = name;
        this.ibpaAgency = ibpaAgency;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void addToHistory(Transaction transaction){
        this.historyOfOperations.addOperation(transaction);
    }

    public BankProduct createBankProduct(BankProductType type, BankProductData bankProductData){
        BankProduct bankProduct = bankProductFactory.create(type, bankProductData);
        this.bankOrganizer.addBankProduct(bankProduct);
        return bankProduct;
    }

    public void deleteBankProduct(String id, BankProductType type){
        bankOrganizer.deleteProduct(id, type);
    }

    public void createUser(String id){
        users.add(new User(id, this));
    }

    public void deleteUser(String id){
        users.stream().filter(u -> u.getId().equals(id)).findFirst().ifPresent(user -> user.setStatus(UserStatus.DELETED));
    }

    public void makeInterBankPayments(InterBankPayment interBankPayment){
        this.ibpaAgency.addNewInterBankPayments(interBankPayment);
    }

    public void takeInterbankPayment(InterBankTransaction transaction){
        Account account = (Account) this.bankOrganizer.returnProduct(transaction.getReceiverID(), BankProductType.ACCOUNT);
        if(account != null && account.getStatus().equals(BankProductStatus.ACTIVE)){
            account.takePayment(transaction);
        }
    }
}
