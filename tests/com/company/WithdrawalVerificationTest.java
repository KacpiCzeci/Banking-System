package com.company;

import com.company.BankProduct.*;
import com.company.Transaction.ConcreteCommands.WithdrawalCommand;
import com.company.TransferVerification.WithdrawalVerification;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class WithdrawalVerificationTest {

    public WithdrawalVerification withdrawalVerification;

    @Mock WithdrawalCommand _WithdrawalCommand;
    @Mock Account _Account1;
    @Mock Account _Account2;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        withdrawalVerification = new WithdrawalVerification();

        when(_WithdrawalCommand.getAmount()).thenReturn(new BigDecimal("50.00"));
        when(_WithdrawalCommand.getSender()).thenReturn(_Account1);
        when(_Account1.getBalance()).thenReturn(new BigDecimal("10.00"), new BigDecimal("50.00"), new BigDecimal("100.00"));
    }

    @Test
    public void moneyVerificationTest() {
        assertEquals(withdrawalVerification.moneyVerification(_WithdrawalCommand), false);
        assertEquals(withdrawalVerification.moneyVerification(_WithdrawalCommand), true);
        assertEquals(withdrawalVerification.moneyVerification(_WithdrawalCommand), true);
    }

    @Test
    public void verifyTransaction() {
        assertEquals(withdrawalVerification.verifyTransaction(_WithdrawalCommand), false);
        verify(_WithdrawalCommand, times(1)).getSender();


        assertEquals(withdrawalVerification.verifyTransaction(_WithdrawalCommand), true);
        verify(_WithdrawalCommand, times(2)).getSender();
    }

    @AfterEach
    public void cleanData() {
        withdrawalVerification = null;
    }
}
