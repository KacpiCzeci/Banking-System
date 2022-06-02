package com.company;

import com.company.BankProduct.Account;
import com.company.Report.ReportVisitor;
import com.company.Transaction.ConcreteCommands.PaymentCommand;
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

public class WithdrawalCommandTest {
    public WithdrawalCommand withdrawalCommand;

    @Mock Account _Account1;
    @Mock ReportVisitor _ReportVisitor;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        withdrawalCommand = new WithdrawalCommand(TransactionType.WITHDRAWAL, _Account1, new BigDecimal("40.00"));

        when(_Account1.getId()).thenReturn("123456789");
    }

    @Test
    public void executeTest(){
        withdrawalCommand.execute();
        verify(_Account1, times(1)).withdrawMoney(new BigDecimal("40.00"));
    }

    @Test
    public void createDescriptionTest(){
        withdrawalCommand.execute();
        assertEquals(withdrawalCommand.getDescription(), "Withdrawal in amount of 40.00.");
    }

    @Test
    public void acceptVisitorTest(){
        withdrawalCommand.acceptVisitor(_ReportVisitor);
        verify(_ReportVisitor, times(1)).visitTransaction(withdrawalCommand);
    }

    @AfterEach
    public void clearData(){
        withdrawalCommand = null;
    }
}
