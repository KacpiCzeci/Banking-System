package com.company;

import com.company.BankProduct.Account;
import com.company.BankProduct.Deposit;
import com.company.Report.ReportVisitor;
import com.company.Transaction.ConcreteCommands.OpenDepositCommand;
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

public class OpenDepositCommandTest {
    public OpenDepositCommand openDepositCommand;

    @Mock
    Account _Account1;
    @Mock
    ReportVisitor _ReportVisitor;
    @Mock
    Deposit _Deposit;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        openDepositCommand = new OpenDepositCommand(TransactionType.OPENDEPOSIT, _Account1, "222444888", 14);

        when(_Account1.getId()).thenReturn("123456789");
        when(_Account1.getDeposit("222444888")).thenReturn(_Deposit);
        when(_Deposit.getId()).thenReturn("222444888");
    }

    @Test
    public void executeTest(){
        openDepositCommand.execute();
        verify(_Account1, times(1)).openDeposit("222444888", 14);
    }

    @Test
    public void createDescriptionTest(){
        openDepositCommand.execute();
        assertEquals(openDepositCommand.getDescription(), "Open Deposit 222444888 to account 123456789.");
    }

    @Test
    public void acceptVisitorTest(){
        openDepositCommand.acceptVisitor(_ReportVisitor);
        verify(_ReportVisitor, times(1)).visit(openDepositCommand);
    }

    @AfterEach
    public void clearData(){
        openDepositCommand = null;
    }
}
