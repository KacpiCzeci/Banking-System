package com.company;

import com.company.BankProduct.*;
import com.company.BankProduct.Data.AccountProductData;
import com.company.BankProduct.Data.DebitAccountProductData;
import com.company.BankProduct.Data.DepositData;
import com.company.BankProduct.Data.LoanData;
import com.company.Card.Card;
import com.company.TransferVerification.TransferVerification;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

class BankProductCreatorTest {

    public BankProductCreator bankProductCreator;
    public Clock clock = Clock.fixed(Instant.parse("2014-12-22T10:15:30Z"), ZoneId.of("UTC"));

    @Mock Bank _Bank;
    @Mock User _User;
    @Mock TransferVerification _TransferVerification;
    @Mock Account _Account;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        bankProductCreator = new BankProductCreator();
    }

    @Test
    public void createAccountTest(){
        AccountProductData accountProductData = new AccountProductData("123456789", _Bank, BankProductType.ACCOUNT, _User, _TransferVerification);
        BankProduct bankProduct = bankProductCreator.create(BankProductType.ACCOUNT, accountProductData);
        assertEquals(bankProduct.getId(), "123456789");
        assertEquals(bankProduct.getBank(), _Bank);
        assertEquals(bankProduct.getType(), BankProductType.ACCOUNT);
        assertEquals(bankProduct.getOwner(), _User);
        assertEquals(((Account)bankProduct).getTransferVerification(), _TransferVerification);
    }

    @Test
    public void createDepositTest(){
        DepositData depositData = new DepositData("123456789", _Bank, BankProductType.DEPOSIT, _User, new BigDecimal("0.00"), LocalDateTime.now(clock));
        BankProduct bankProduct = bankProductCreator.create(BankProductType.DEPOSIT, depositData);
        assertEquals(bankProduct.getId(), "123456789");
        assertEquals(bankProduct.getBank(), _Bank);
        assertEquals(bankProduct.getType(), BankProductType.DEPOSIT);
        assertEquals(bankProduct.getOwner(), _User);
        assertEquals(bankProduct.getBalance(), new BigDecimal("0.00"));
        assertEquals(((Deposit)bankProduct).getCloseDate(), LocalDateTime.now(clock));
    }

    @Test
    public void createLoanTest(){
        LoanData loanData = new LoanData("123456789", _Bank, BankProductType.LOAN, _User, new BigDecimal("0.00"), LocalDateTime.now(clock));
        BankProduct bankProduct = bankProductCreator.create(BankProductType.LOAN, loanData);
        assertEquals(bankProduct.getId(), "123456789");
        assertEquals(bankProduct.getBank(), _Bank);
        assertEquals(bankProduct.getType(), BankProductType.LOAN);
        assertEquals(bankProduct.getOwner(), _User);
        assertEquals(bankProduct.getBalance(), new BigDecimal("0.00"));
        assertEquals(((Loan)bankProduct).getCloseDate(), LocalDateTime.now(clock));
    }

    @Test
    public void createDebitAccountTest(){
        DebitAccountProductData debitAccountProductData = new DebitAccountProductData("123456789", _Bank, BankProductType.DEPOSIT, _User, _Account);
        BankProduct bankProduct = bankProductCreator.create(BankProductType.DEBITACCOUNT, debitAccountProductData);
        assertEquals(bankProduct.getId(), "123456789");
        assertEquals(bankProduct.getBank(), _Bank);
        assertEquals(bankProduct.getType(), BankProductType.DEBITACCOUNT);
        assertEquals(bankProduct.getOwner(), _User);
        assertEquals(((DebitAccount)bankProduct).getBankProduct(), _Account);
    }

    @AfterEach
    void cleanData(){
        bankProductCreator = null;
    }
}