package com.company;

import com.company.BankProduct.Account;
import com.company.Report.ReportVisitor;
import com.company.Transaction.ConcreteCommands.UseCardCommand;
import com.company.Transaction.ConcreteCommands.WithdrawalCommand;
import com.company.Transaction.TransactionType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UseCardCommandTest {
    public UseCardCommand useCardCommand;

    @Mock
    Account _Account1;
    @Mock
    ReportVisitor _ReportVisitor;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        useCardCommand = new UseCardCommand(TransactionType.USECARD, _Account1, 123456789L, new BigDecimal("40.00"));

        when(_Account1.getId()).thenReturn("123456789");
    }

    @Test
    public void executeTest(){
        useCardCommand.execute();
        verify(_Account1, times(1)).useCardPayment( 123456789L,new BigDecimal("40.00"));
    }

    @Test
    public void createDescriptionTest(){
        useCardCommand.execute();
        assertEquals(useCardCommand.getDescription(), "Paid by card 123456789 in amount of 40.00.");
    }

    @Test
    public void acceptVisitorTest(){
        useCardCommand.acceptVisitor(_ReportVisitor);
        verify(_ReportVisitor, times(1)).visit(useCardCommand);
    }

    @AfterEach
    public void clearData(){
        useCardCommand = null;
    }
}
