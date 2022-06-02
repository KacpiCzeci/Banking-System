package com.company;

import com.company.BankProduct.*;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BankProductOrganizerTest {

    public BankProductOrganizer bankProductOrganizer;

    @Mock Account _Account;
    @Mock Deposit _Deposit;
    @Mock Loan _Loan;
    @Mock DebitAccount _DebitAccount;


    @BeforeEach
    public void setUpEach() {
        MockitoAnnotations.initMocks(this);
        bankProductOrganizer = new BankProductOrganizer();

        when(_Account.getType()).thenReturn(BankProductType.ACCOUNT);
        when(_Account.getId()).thenReturn("123456789");
        when(_Deposit.getType()).thenReturn(BankProductType.DEPOSIT);
        when(_Deposit.getId()).thenReturn("123456789");
        when(_DebitAccount.getType()).thenReturn(BankProductType.DEBITACCOUNT);
        when(_DebitAccount.getId()).thenReturn("123456789");
        when(_Loan.getType()).thenReturn(BankProductType.LOAN);
        when(_Loan.getId()).thenReturn("123456789");
    }

    @Test
    public void addBankProductTest(){
        bankProductOrganizer.addBankProduct(_Account);
        verify(_Account, times(1)).getType();
        assertEquals(bankProductOrganizer.getAccounts().size(), 1);
        assertEquals(bankProductOrganizer.getAccounts().get(0), _Account);

        bankProductOrganizer.addBankProduct(_Deposit);
        verify(_Deposit, times(1)).getType();
        assertEquals(bankProductOrganizer.getDeposits().size(), 1);
        assertEquals(bankProductOrganizer.getDeposits().get(0), _Deposit);

        bankProductOrganizer.addBankProduct(_Loan);
        verify(_Loan, times(1)).getType();
        assertEquals(bankProductOrganizer.getLoans().size(), 1);
        assertEquals(bankProductOrganizer.getLoans().get(0), _Loan);

        bankProductOrganizer.addBankProduct(_DebitAccount);
        verify(_DebitAccount, times(1)).getType();
        assertEquals(bankProductOrganizer.getDebitAccounts().size(), 1);
        assertEquals(bankProductOrganizer.getDebitAccounts().get(0), _DebitAccount);
    }

    @Test
    public void returnProductTest(){
        bankProductOrganizer.addBankProduct(_Account);
        BankProduct acc = bankProductOrganizer.returnProduct("123456789", BankProductType.ACCOUNT);
        assertEquals(acc.getId(), "123456789");
        assertEquals(acc.getType(), BankProductType.ACCOUNT);

        acc = bankProductOrganizer.returnProduct("3242342", BankProductType.DEPOSIT);
        assertNull(acc);

        acc = bankProductOrganizer.returnProduct("123456789", BankProductType.DEBITACCOUNT);
        assertNull(acc);

        acc = bankProductOrganizer.returnProduct("3242342", BankProductType.ACCOUNT);
        assertNull(acc);
    }

    @Test
    public void deleteProductTest(){
        bankProductOrganizer.addBankProduct(_Account);
        assertEquals(bankProductOrganizer.getAccounts().size(), 1);

        bankProductOrganizer.deleteProduct("1324324", BankProductType.ACCOUNT);
        verify(_Account, times(0)).setStatus(BankProductStatus.CLOSED);
        bankProductOrganizer.deleteProduct("1324324", BankProductType.DEPOSIT);
        verify(_Account, times(0)).setStatus(BankProductStatus.CLOSED);
        bankProductOrganizer.deleteProduct("123456789", BankProductType.LOAN);
        verify(_Account, times(0)).setStatus(BankProductStatus.CLOSED);
        bankProductOrganizer.deleteProduct("123456789", BankProductType.ACCOUNT);
        verify(_Account, times(1)).setStatus(BankProductStatus.CLOSED);

        assertEquals(bankProductOrganizer.getAccounts().size(), 1);
    }

    @AfterEach
    public void clearDataEach(){
        bankProductOrganizer = null;
    }
}
