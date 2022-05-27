package com.company;

import com.company.BankProduct.Account;
import com.company.BankProduct.BankProduct;
import com.company.BankProduct.BankProductType;
import com.company.BankProduct.Data.AccountProductData;
import com.company.BankProduct.Data.BankProductData;
import com.company.InterBankPayment.IBPAagency;
import com.company.TransferVerification.TransferVerification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {

    public Bank bank;

    /**
     * Mock's
     */
    @Mock public IBPAagency _ibpaAgencyMOCK;
    @Mock public User _userMOCK;
    @Mock BankProductData bankProductDataMOCK;
    @Mock TransferVerification transferVerificationMOCK;

    @BeforeEach
    public void setUp(){
        bank = new Bank("0","TestBank", _ibpaAgencyMOCK);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void CheckGetIdMethod(){
        //given
        String idWhatYouExpect="0";
        //then
        assertTrue(bank.getId().equals(idWhatYouExpect));
    }

    @Test
    public void CheckGetNameMethod(){
        //given
        String nameWhatYouExpect="TestBank";
        //then
        assertTrue(bank.getName().equals(nameWhatYouExpect));
    }

    /**
     * User operation test's
     */
    @Test
    public void  CheckGetUsersMethodReturnArrayList(){
        assertTrue(bank.getUsers() instanceof ArrayList);
    }
    @Test
    public void CheckCreateUserMethod(){
        //when
        bank.createUser("1");
        //then
        assertTrue(bank.getUsers().size()==1);
    }

    @Test
    public void CheckDeleteUserMethod(){
        //given
        String idOfUser="1";
        //when
        bank.createUser(idOfUser);
        bank.deleteUser(idOfUser);
        //then
        assertEquals(UserStatus.DELETED, bank.getUsers().get(0).getStatus());
    }

    /**
     * Bank product operation
     */

    @Test
    public void CheckIfProductIsCretedByCreateBankProductMethodIsRunning(){
        //given
        Bank bankMock =mock(Bank.class);
        BankProductType bankProductType= BankProductType.ACCOUNT;
        AccountProductData accountProductData= new AccountProductData("0",bankMock,bankProductType,_userMOCK,transferVerificationMOCK);
        //when
        bankMock.createBankProduct(bankProductType,accountProductData);
        //then
        verify(bankMock, times(1)).createBankProduct(bankProductType,accountProductData);
    }

    @Test
    public void CheckCretedProduct(){
        //given
        BankProductType bankProductType= BankProductType.ACCOUNT;
        User  userLOCAL =new User("1234567890",bank);
        AccountProductData accountProductData= new AccountProductData("0",bank,bankProductType,userLOCAL,transferVerificationMOCK);
        //when
        BankProduct returnedProduct=bank.createBankProduct(bankProductType,accountProductData);
        //then
        assertArrayEquals(new Object[]{returnedProduct.getType(),returnedProduct.getId(),returnedProduct.getBank(),returnedProduct.getOwner()},new Object[]{bankProductType,"0",bank,userLOCAL});
    }
    @Test
    public void CheckIfBankAccoutIsDelete(){
//        //given
//        bank.createAccount("Tester", transferVerification);
//        //when
//        bank.deleteAccount(0);
//        //then
//        assertEquals(0, bank.getAccounts().size());
    }
    @AfterEach
    public void clearData(){
//        bank = null;
    }
}