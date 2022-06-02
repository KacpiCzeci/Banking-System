package com.company;

import com.company.BankProduct.*;
import com.company.Transaction.ConcreteCommands.PaymentCommand;
import com.company.TransferVerification.PaymentVerification;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentVerificationTest {

    public PaymentVerification paymentVerification;

    @Mock Bank _Bank;
    @Mock PaymentCommand _PaymentCommand;
    @Mock Account _Account1;
    @Mock Account _Account2;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        paymentVerification = new PaymentVerification(_Bank);

        when(_Bank.getBankProduct("123456789", BankProductType.ACCOUNT)).thenReturn(_Account1);
        when(_Bank.getBankProduct("214233214", BankProductType.ACCOUNT)).thenReturn(null);

        when(_Account2.getId()).thenReturn("123456789", "123456789", "12412354", "1234512353");
        when(_Account2.getType()).thenReturn(BankProductType.ACCOUNT, BankProductType.DEPOSIT, BankProductType.ACCOUNT, BankProductType.DEPOSIT);

        when(_PaymentCommand.getAmount()).thenReturn(new BigDecimal("50.00"));
        when(_PaymentCommand.getSender()).thenReturn(_Account1);
        when(_PaymentCommand.getReceiver()).thenReturn((_Account2));
        when(_Account1.getBalance()).thenReturn(new BigDecimal("10.00"), new BigDecimal("50.00"), new BigDecimal("100.00"));
    }

    @Test
    public void moneyVerificationTest() {
        assertEquals(paymentVerification.moneyVerification(_PaymentCommand), false);
        assertEquals(paymentVerification.moneyVerification(_PaymentCommand), true);
        assertEquals(paymentVerification.moneyVerification(_PaymentCommand), true);
    }

    @Test
    public void accountVerification() {
        assertEquals(paymentVerification.accountVerification(_PaymentCommand), true);
        assertEquals(paymentVerification.accountVerification(_PaymentCommand), false);
        assertEquals(paymentVerification.accountVerification(_PaymentCommand), false);
        assertEquals(paymentVerification.accountVerification(_PaymentCommand), false);
    }

    @Test
    public void verifyTransaction() {
        paymentVerification.verifyTransaction(_PaymentCommand);
        verify(_PaymentCommand, times(2)).getReceiver();
        verify(_PaymentCommand, times(1)).getSender();


        paymentVerification.verifyTransaction(_PaymentCommand);
        verify(_PaymentCommand, times(4)).getReceiver();
        verify(_PaymentCommand, times(1)).getSender();
    }

    @AfterEach
    public void cleanData() {
        paymentVerification = null;
    }
}
