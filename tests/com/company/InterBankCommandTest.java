package com.company;

import com.company.BankProduct.Account;
import com.company.Report.ReportVisitor;
import com.company.Transaction.ConcreteCommands.InterBankCommand;
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

public class InterBankCommandTest {
    public InterBankCommand interBankCommand;

    @Mock
    Bank _Bank1;
    @Mock
    Account _Account1;
    @Mock
    ReportVisitor _ReportVisitor;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        interBankCommand = new InterBankCommand(TransactionType.INTERBANKPAYMENT, _Account1, _Bank1, "123456789", "333666999",new BigDecimal("40.00"));

        when(_Account1.getId()).thenReturn("123456789");
        when(_Bank1.getId()).thenReturn("111222333");
    }

    @Test
    public void executeTest(){
        interBankCommand.execute();
        verify(_Account1, times(1)).withdrawMoney(new BigDecimal("40.00"));

        interBankCommand.setType(TransactionType.INTERBANKINCOME);
        interBankCommand.execute();
        verify(_Account1, times(1)).receiveMoney(new BigDecimal("40.00"));
    }

    @Test
    public void setTypeText(){
        assertEquals(interBankCommand.getType(), TransactionType.INTERBANKPAYMENT);
        interBankCommand.setType(TransactionType.INTERBANKINCOME);
        assertEquals(interBankCommand.getType(), TransactionType.INTERBANKINCOME);
        interBankCommand.setType(TransactionType.DEPOSE);
        assertEquals(interBankCommand.getType(), TransactionType.INTERBANKINCOME);
        interBankCommand.setType(TransactionType.INTERBANKPAYMENT);
        assertEquals(interBankCommand.getType(), TransactionType.INTERBANKPAYMENT);
    }

    @Test
    public void createDescriptionTest(){
        interBankCommand.execute();
        assertEquals(interBankCommand.getDescription(), "InterBankPayment from 111222333 to 123456789 in amount of 40.00.");
    }

    @Test
    public void acceptVisitorTest(){
        interBankCommand.acceptVisitor(_ReportVisitor);
        verify(_ReportVisitor, times(1)).visitTransaction(interBankCommand);
    }

    @AfterEach
    public void clearData(){
        interBankCommand = null;
    }
}
