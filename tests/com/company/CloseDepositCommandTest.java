package com.company;

import com.company.BankProduct.Account;
import com.company.BankProduct.Deposit;
import com.company.Report.ReportVisitor;
import com.company.Transaction.ConcreteCommands.ClosingDepositCommand;
import com.company.Transaction.TransactionType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CloseDepositCommandTest {
    public ClosingDepositCommand closingDepositCommand;

    @Mock
    Account _Account1;
    @Mock
    ReportVisitor _ReportVisitor;
    @Mock
    Deposit _Deposit;


    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        closingDepositCommand = new ClosingDepositCommand(TransactionType.CLOSEDEPOSIT, _Account1, "123456789");

        when(_Account1.getId()).thenReturn("123456789");
        when(_Account1.getDeposit("123456789")).thenReturn(_Deposit);
        when(_Deposit.getId()).thenReturn("123456789");
    }

    @Test
    public void executeTest(){
        closingDepositCommand.execute();
        verify(_Account1, times(1)).closeDeposit("123456789");
    }

    @Test
    public void createDescriptionTest(){
        closingDepositCommand.execute();
        assertEquals(closingDepositCommand.getDescription(), "Closed Deposit 123456789 of account 123456789.");
    }

    @Test
    public void acceptVisitorTest(){
        closingDepositCommand.acceptVisitor(_ReportVisitor);
        verify(_ReportVisitor, times(1)).visit(closingDepositCommand);
    }

    @AfterEach
    public void clearData(){
        closingDepositCommand = null;
    }
}
