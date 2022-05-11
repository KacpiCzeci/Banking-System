package com.company;

import com.company.TransferVerification.TransferVerification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.hamcrest.CoreMatchers.is;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {

    public Bank bank;

    @Mock public TransferVerification transferVerification;

    @BeforeEach
    public void setUp(){
        bank = new Bank(0,"TestBank",1.0,2.0,3.0);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void CheckIfBankAccountIsCreate(){
        //when
        bank.createAccount("Tester", transferVerification);
        //then
        assertTrue(bank.getAccounts().size()>0);
    }

    @Test
    public void CheckIfBankAccoutIsDelete(){
        //given
        bank.createAccount("Tester", transferVerification);
        //when
        bank.deleteAccount(0);
        //then
        assertEquals(0, bank.getAccounts().size());
    }

    @AfterEach
    public void clearData(){
        bank = null;
    }
}