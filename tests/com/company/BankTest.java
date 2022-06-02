package com.company;

import com.company.BankProduct.Account;
import com.company.BankProduct.BankProduct;
import com.company.BankProduct.BankProductStatus;
import com.company.BankProduct.BankProductType;
import com.company.BankProduct.Data.AccountProductData;
import com.company.BankProduct.Data.BankProductData;
import com.company.InterBankPayment.IBPAagency;
import com.company.Transaction.ConcreteCommands.InterBankCommand;
import com.company.Transaction.ConcreteCommands.RemoveCard;
import com.company.Transaction.TransactionCommand;
import com.company.Transaction.TransactionType;
import com.company.TransferVerification.TransferVerification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class BankTest {

    public Bank bank;

    /**
     * Mock's
     */
    @Mock public IBPAagency _ibpaAgencyMOCK;
    @Mock public User _userMOCK;
    @Mock BankProductData bankProductDataMOCK;
    @Mock TransferVerification transferVerificationMOCK;
    @Mock InterBankCommand transactionCommandMOCK;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        bank = new Bank("0","TestBank", _ibpaAgencyMOCK);
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
    public void CheckReturnMethodOfBankProduct(){
        BankProductType bankProductType= BankProductType.ACCOUNT;
        User  userLOCAL =new User("1234567890",bank);
        AccountProductData accountProductData= new AccountProductData("0",bank,bankProductType,userLOCAL,transferVerificationMOCK);
        bank.createBankProduct(bankProductType,accountProductData);
        //when
        BankProduct returnedProduct=bank.getBankProduct("0",bankProductType);
        //then
        assertArrayEquals(new Object[]{returnedProduct.getType(),returnedProduct.getId(),returnedProduct.getBank(),returnedProduct.getOwner()},new Object[]{bankProductType,"0",bank,userLOCAL});

    }
    @Test
    public void CheckIfBankIfDeleteBankProductMethodWork(){
        //given
        BankProductType bankProductType= BankProductType.ACCOUNT;
        User  userLOCAL =new User("1234567890",bank);
        AccountProductData accountProductData= new AccountProductData("0",bank,bankProductType,userLOCAL,transferVerificationMOCK);
        BankProduct returnedProduct=bank.createBankProduct(bankProductType,accountProductData);
        //when
        bank.deleteBankProduct("0",bankProductType);
        //then
        assertEquals(BankProductStatus.CLOSED, bank.getBankProduct("0",bankProductType).getStatus());
    }

    /**
     * InterBankPayments
     */
    @Test
    public void MakeInterBankPayments(){
        //given
//        Bank bankMock =mock(Bank.class);
//        BankProductType bankProductType= BankProductType.ACCOUNT;
//        AccountProductData accountProductData= new AccountProductData("0",bankMock,bankProductType,_userMOCK,transferVerificationMOCK);
//        Account returnedProductBankMock=mock(Account.class);
//        //when
//        bankMock.makeInterBankPayments(returnedProductBankMock,bankMock.getId(),"0",new BigDecimal(0.1d));
////        InterBankCommand transactionCommand = new InterBankCommand(TransactionType.INTERBANKPAYMENT, returnedProductBankMock,  this, idBank, idAccount, amount);
//        //then
//        verify(returnedProductBankMock, times(1)).doTransaction(transactionCommandMOCK);

    }

    @AfterEach
    public void clearData(){
//        bank = null;
    }
}