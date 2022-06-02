package com.company;

import com.company.BankProduct.*;
import com.company.InterBankPayment.IBPAagency;
import com.company.Transaction.ConcreteCommands.FailureCommand;
import com.company.Transaction.ConcreteCommands.InterBankCommand;
import com.company.Transaction.TransactionType;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;


public class IBPAagencyTest {
    public IBPAagency ibpaAgency;

    @Mock Bank _Bank1;
    @Mock Bank _Bank2;
    @Mock Account _Account1;
    @Mock Account _Account2;
    @Mock InterBankCommand _InterBankCommand1;
    @Mock InterBankCommand _InterBankCommand2;
    @Mock InterBankCommand _InterBankCommand3;
    @Mock FailureCommand _FailureCommand;

    @BeforeEach
    public void setUpEach() {
        MockitoAnnotations.initMocks(this);
        ibpaAgency = new IBPAagency();
        ibpaAgency.addBank(_Bank1);
        ibpaAgency.addBank(_Bank2);

        when(_InterBankCommand1.getReceiverBank()).thenReturn("222222222");
        when(_InterBankCommand2.getReceiverBank()).thenReturn("222222222");
        when(_InterBankCommand3.getReceiverBank()).thenReturn("123456789");

        when(_Bank1.getId()).thenReturn("111111111");
        when(_Bank2.getId()).thenReturn("222222222");

        when(_Bank2.getBankProduct("123456789", BankProductType.ACCOUNT)).thenReturn(_Account2);
        when(_Bank2.getBankProduct("13212312", BankProductType.ACCOUNT)).thenReturn(null);
        when(_Bank2.getBankProduct("123456789", BankProductType.DEPOSIT)).thenReturn(null);
        when(_Bank2.getBankProduct("13212312", BankProductType.LOAN)).thenReturn(null);

        when(_InterBankCommand1.getType()).thenReturn(TransactionType.INTERBANKPAYMENT);
        when(_InterBankCommand2.getType()).thenReturn(TransactionType.INTERBANKPAYMENT, TransactionType.INTERBANKINCOME);
        when(_InterBankCommand3.getType()).thenReturn(TransactionType.INTERBANKPAYMENT);
        when(_InterBankCommand2.getSenderBank()).thenReturn(_Bank1);
        when(_FailureCommand.getType()).thenReturn(TransactionType.FAILURE);
        when(_FailureCommand.getCommand()).thenReturn(_InterBankCommand2);

        doAnswer((Answer<Void>) invocationOnMock -> {ibpaAgency.doInterbankPayment(_Bank2, _FailureCommand); return null;}).when(_Bank2).takeInterbankPayment(_InterBankCommand2);
    }

    @Test
    public void doInterbankPaymentCorrectTest(){
        ibpaAgency.doInterbankPayment(_Bank1, _InterBankCommand1);

        verify(_InterBankCommand1, times(1)).getType();
        verify(_Bank2, times(1)).getId();
        verify(_InterBankCommand1, times(1)).setType(TransactionType.INTERBANKINCOME);
        verify(_Bank2, times(1)).takeInterbankPayment(_InterBankCommand1);
        verify(_Bank1, times(0)).takeInterbankPayment(any());
    }

    @Test
    public void doInterbankPaymentNoBankTest(){
        ibpaAgency.doInterbankPayment(_Bank1, _InterBankCommand3);

        verify(_InterBankCommand3, times(1)).getType();
        verify(_Bank2, times(0)).takeInterbankPayment(_InterBankCommand3);
        verify(_Bank1, times(0)).takeInterbankPayment(_InterBankCommand3);
        verify(_Bank1, times(1)).takeFailure(any());
    }

    @Test
    public void doInterbankPaymentNoAccountTest(){
        ibpaAgency.doInterbankPayment(_Bank1, _InterBankCommand2);

        verify(_InterBankCommand2, times(2)).getType();
        verify(_Bank2, times(1)).getId();
        verify(_InterBankCommand2, times(1)).setType(TransactionType.INTERBANKINCOME);
        verify(_Bank2, times(1)).takeInterbankPayment(_InterBankCommand2);
        verify(_Bank1, times(1)).takeFailure(any());
    }

    @AfterEach
    public void clearDataEach(){
        ibpaAgency = null;
    }
}
