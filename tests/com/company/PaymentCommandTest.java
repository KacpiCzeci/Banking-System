package com.company;

import com.company.BankProduct.*;
import com.company.Report.ReportVisitor;
import com.company.Transaction.ConcreteCommands.PaymentCommand;
import com.company.Transaction.TransactionType;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentCommandTest {
    public PaymentCommand paymentCommand;

    @Mock Account _Account1;
    @Mock Account _Account2;
    @Mock ReportVisitor _ReportVisitor;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        paymentCommand = new PaymentCommand(TransactionType.PAYMENT, _Account1, _Account2, new BigDecimal("40.00"));

        when(_Account1.getId()).thenReturn("123456789");
        when(_Account2.getId()).thenReturn("987654321");
    }

    @Test
    public void executeTest(){
        paymentCommand.execute();
        verify(_Account1, times(1)).withdrawMoney(new BigDecimal("40.00"));
        verify(_Account2, times(1)).receiveMoney(new BigDecimal("40.00"));
    }

    @Test
    public void createDescriptionTest(){
        paymentCommand.execute();
        assertEquals(paymentCommand.getDescription(), "Payment from 123456789 to 987654321 in amount of 40.00.");
    }

    @Test
    public void acceptVisitorTest(){
        paymentCommand.acceptVisitor(_ReportVisitor);
        verify(_ReportVisitor, times(1)).visit(paymentCommand);
    }

    @AfterEach
    public void clearData(){
        paymentCommand = null;
    }
}
