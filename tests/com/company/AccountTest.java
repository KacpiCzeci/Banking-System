package com.company;

import com.company.BankProduct.*;
import com.company.Card.Card;
import com.company.Report.ReportVisitor;
import com.company.Transaction.ConcreteCommands.InterBankCommand;
import com.company.Transaction.TransactionCommand;
import com.company.Transaction.TransactionType;
import com.company.TransferVerification.TransferVerification;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccountTest {
    public Account account;

    @Mock Bank _Bank;
    @Mock User _User;
    @Mock TransferVerification _TransferVerification;
    @Mock TransactionCommand _TransactionCommand;
    @Mock ReportVisitor _ReportVisitor;
    @Mock Deposit _Deposit;
    @Mock Loan _Loan;
    @Mock Card _Card;
    @Mock InterBankCommand _InterBankCommand;

    @BeforeAll
    public void setUpAll(){
    }

    @BeforeEach
    public void setUpEach(){
        MockitoAnnotations.initMocks(this);
        account = new Account("123456789", _Bank, _User, _TransferVerification);

        Clock clock = Clock.fixed(Instant.parse("2014-12-22T10:15:30Z"), ZoneId.of("UTC"));

        when(_Deposit.getId()).thenReturn("123456789");
        when(_Deposit.getType()).thenReturn(BankProductType.DEPOSIT);
        when(_Deposit.getBalance()).thenReturn(new BigDecimal("0.00"));
        when(_Deposit.getStatus()).thenReturn(BankProductStatus.ACTIVE);
        when(_Deposit.getBank()).thenReturn(account.getBank());
        when(_Deposit.getOwner()).thenReturn(account.getOwner());
        when(_Deposit.getHistoryOfOperations()).thenReturn(new ArrayList<>());
        when(_Deposit.getCloseDate()).thenReturn(LocalDateTime.now(clock).plusMonths(6));
        when(_Deposit.getDateOfOpening()).thenReturn(LocalDateTime.now(clock));
        when(_Deposit.closeDeposit()).thenReturn(new BigDecimal("40.00"));
        //when(_Deposit.getStatus()).thenReturn(BankProductStatus.ACTIVE);

        when(_Loan.getId()).thenReturn("123456789");
        when(_Loan.getType()).thenReturn(BankProductType.LOAN);
        when(_Loan.getBalance()).thenReturn(new BigDecimal("0.00"));
        when(_Loan.getStatus()).thenReturn(BankProductStatus.ACTIVE);
        when(_Loan.getBank()).thenReturn(account.getBank());
        when(_Loan.getOwner()).thenReturn(account.getOwner());
        when(_Loan.getHistoryOfOperations()).thenReturn(new ArrayList<>());
        when(_Loan.getCloseDate()).thenReturn(LocalDateTime.now(clock).plusMonths(6));
        when(_Loan.getDateOfOpening()).thenReturn(LocalDateTime.now(clock));
        when(_Loan.payLoan()).thenReturn(new BigDecimal("40.00"));
        when(_Loan.takeLoan(new BigDecimal("40.00"))).thenReturn(new BigDecimal("40.00"));
        when(_Loan.takeLoan(new BigDecimal("400.00"))).thenReturn(new BigDecimal("400.00"));
        //when(_Deposit.getStatus()).thenReturn(BankProductStatus.ACTIVE);

        when(_Card.getNumber()).thenReturn(123456789L);
        when(_Card.getStatus()).thenReturn(BankProductStatus.ACTIVE);

        when(_Bank.createBankProduct(eq(BankProductType.DEPOSIT), any())).thenReturn(_Deposit);
        when(_Bank.createBankProduct(eq(BankProductType.LOAN), any())).thenReturn(_Loan);

        when(_InterBankCommand.getType()).thenReturn(TransactionType.INTERBANKINCOME);
        when(_InterBankCommand.getAmount()).thenReturn(new BigDecimal("30.00"));
    }

    @Test
    public void receiveMoneyTest(){
        BigDecimal amount = new BigDecimal("0.00");
        assertEquals(account.getBalance(), amount);

        amount = new BigDecimal("50.00");
        account.receiveMoney(amount);
        assertEquals(account.getBalance(), amount);

        amount = new BigDecimal("50.00");
        account.receiveMoney(amount);
        assertEquals(account.getBalance(), new BigDecimal("100.00"));

        amount = new BigDecimal("-50.00");
        account.receiveMoney(amount);
        assertEquals(account.getBalance(), new BigDecimal("100.00"));
    }

    @Test
    public void withdrawMoneyTest(){
        BigDecimal amount = new BigDecimal("50.00");
        account.receiveMoney(amount);

        account.withdrawMoney(new BigDecimal("14.79"));
        assertEquals(account.getBalance(), new BigDecimal("35.21"));

        account.withdrawMoney(new BigDecimal("0.00"));
        assertEquals(account.getBalance(), new BigDecimal("35.21"));

        account.withdrawMoney(new BigDecimal("500.00"));
        assertEquals(account.getBalance(), new BigDecimal("35.21"));

        account.withdrawMoney(new BigDecimal("-500.00"));
        assertEquals(account.getBalance(), new BigDecimal("35.21"));
    }

    @Test
    public void doTransactionTest(){
        account.doTransaction(_TransactionCommand);

        verify(_TransactionCommand, times(1)).execute();
    }

    @Test
    public void handleFailureTest(){
        assertEquals(account.getBalance(), new BigDecimal("0.00"));
        account.handleFailure(_InterBankCommand);

        assertEquals(account.getBalance(), new BigDecimal("30.00"));
        verify(_InterBankCommand, times(1)).getAmount();
    }

    @Test
    public void acceptVisitorTest(){
        account.acceptVisitor(_ReportVisitor);
        verify(_ReportVisitor, times(1)).visitBankProduct(account);
    }

    @Test
    public void openDepositTest(){
        assertEquals(account.getDeposits().size(), 0);
        account.openDeposit("123456789", 6);
        assertEquals(account.getDeposits().size(), 1);

        assertNotNull(account.getDeposit("123456789"));

        Clock clock = Clock.fixed(Instant.parse("2014-12-22T10:15:30Z"), ZoneId.of("UTC"));

        Deposit deposit = account.getDeposit("123456789");
        assertEquals(deposit.getId(), "123456789");
        assertEquals(deposit.getType(), BankProductType.DEPOSIT);
        assertEquals(deposit.getBalance(), new BigDecimal("0.00"));
        assertEquals(deposit.getStatus(), BankProductStatus.ACTIVE);
        assertEquals(deposit.getBank(), account.getBank());
        assertEquals(deposit.getOwner(), account.getOwner());
        assertEquals(deposit.getHistoryOfOperations().size(), 0);
        assertEquals(deposit.getCloseDate(), LocalDateTime.now(clock).plusMonths(6));
        assertEquals(deposit.getDateOfOpening(), LocalDateTime.now(clock));
    }

    @Test
    public void openLoanTest(){
        assertEquals(account.getDeposits().size(), 0);
        account.openLoan("123456789", 6);
        assertEquals(account.getLoans().size(), 1);

        assertNotNull(account.getLoan("123456789"));

        Clock clock = Clock.fixed(Instant.parse("2014-12-22T10:15:30Z"), ZoneId.of("UTC"));

        Loan loan = account.getLoan("123456789");
        assertEquals(loan.getId(), "123456789");
        assertEquals(loan.getType(), BankProductType.LOAN);
        assertEquals(loan.getBalance(), new BigDecimal("0.00"));
        assertEquals(loan.getStatus(), BankProductStatus.ACTIVE);
        assertEquals(loan.getBank(), account.getBank());
        assertEquals(loan.getOwner(), account.getOwner());
        assertEquals(loan.getHistoryOfOperations().size(), 0);
        assertEquals(loan.getCloseDate(), LocalDateTime.now(clock).plusMonths(6));
        assertEquals(loan.getDateOfOpening(), LocalDateTime.now(clock));
    }

    @Test
    public void deposeMoneyToDepositTest(){
        account.receiveMoney(new BigDecimal("30.00"));
        account.openDeposit("123456789", 6);

        account.deposeMoneyToDeposit("123456789", new BigDecimal("30.00"));
        assertEquals(account.getBalance(), new BigDecimal("0.00"));
        verify(_Deposit, times(1)).depositMoney(new BigDecimal("30.00"));

        account.receiveMoney(new BigDecimal("30.00"));
        account.deposeMoneyToDeposit("123456789", new BigDecimal("80.00"));
        assertEquals(account.getBalance(), new BigDecimal("30.00"));
        verify(_Deposit, times(0)).depositMoney(new BigDecimal("80.00"));

        account.deposeMoneyToDeposit("123456789", new BigDecimal("-20.00"));
        assertEquals(account.getBalance(), new BigDecimal("30.00"));
        verify(_Deposit, times(0)).depositMoney(new BigDecimal("-20.00"));
    }

    @Test
    public void takeMoneyFromLoanTest(){
        account.openLoan("123456789", 6);
        assertEquals(account.getBalance(), new BigDecimal("0.00"));

        account.takeMoneyFromLoan("123456789", new BigDecimal("40.00"));
        assertEquals(account.getBalance(), new BigDecimal("40.00"));

        account.takeMoneyFromLoan("123456789", new BigDecimal("-40.00"));
        assertEquals(account.getBalance(), new BigDecimal("40.00"));
        verify(_Loan, times(0)).takeLoan(new BigDecimal("-40.00"));
    }

    @Test
    public void closeDepositTest(){
        account.receiveMoney(new BigDecimal("50.00"));
        account.openDeposit("123456789", 6);
        account.deposeMoneyToDeposit("123456789", new BigDecimal("40.00"));

        assertEquals(account.getBalance(), new BigDecimal("10.00"));
        account.closeDeposit("123123453");
        assertEquals(account.getBalance(), new BigDecimal("10.00"));

        account.closeDeposit("123456789");
        assertEquals(account.getBalance(), new BigDecimal("50.00"));

        account.deposeMoneyToDeposit("123456789", new BigDecimal("400.00"));
        assertEquals(account.getBalance(), new BigDecimal("50.00"));

        account.deposeMoneyToDeposit("123456789", new BigDecimal("-40.00"));
        assertEquals(account.getBalance(), new BigDecimal("50.00"));
    }

    @Test
    public void closeLoanTest(){
        account.receiveMoney(new BigDecimal("50.00"));
        account.openLoan("123456789", 6);
        account.takeMoneyFromLoan("123456789", new BigDecimal("40.00"));

        assertEquals(account.getBalance(), new BigDecimal("90.00"));
        account.closeLoan("123123453");
        assertEquals(account.getBalance(), new BigDecimal("90.00"));

        account.closeLoan("123456789");
        assertEquals(account.getBalance(), new BigDecimal("50.00"));
        verify(_Loan, times(1)).payLoan();

        account.takeMoneyFromLoan("123456789", new BigDecimal("400.00"));
        account.withdrawMoney(new BigDecimal("400.00"));
        account.closeLoan("123123453");
        verify(_Loan, times(1)).payLoan();
    }

    @Test
    public void addCardTest(){
        assertEquals(account.getCards().size(), 0);

        account.addCard(_Card);
        assertEquals(account.getCards().size(), 1);

        account.getCards().stream().filter(c -> c.getNumber().equals(123456789L)).findFirst().orElse(null);

        verify(_Card, times(1)).getNumber();

        account.addCard(_Card);
        assertEquals(account.getCards().size(), 2);
    }

    @Test
    public void removeCardTest(){
        assertEquals(account.getCards().size(), 0);

        account.addCard(_Card);

        account.removeCard(12343324L);
        verify(_Card, times(0)).setStatus(BankProductStatus.CLOSED);

        account.removeCard(123456789L);
        verify(_Card, times(1)).setStatus(BankProductStatus.CLOSED);
    }

    @Test
    public void useCardPaymentTest(){
        account.receiveMoney(new BigDecimal("50.00"));
        account.addCard(_Card);

        account.useCardPayment(123456789L, new BigDecimal("5.00"));
        verify(_Card, times(1)).getNumber();
        verify(_Card, times(1)).getStatus();

        assertEquals(account.getBalance(), new BigDecimal("45.00"));

        account.useCardPayment(123458667L, new BigDecimal("5.00"));
        verify(_Card, times(2)).getNumber();
        verify(_Card, times(1)).getStatus();

        assertEquals(account.getBalance(), new BigDecimal("45.00"));

        account.useCardPayment(123456789L, new BigDecimal("-5.00"));
        verify(_Card, times(3)).getNumber();
        verify(_Card, times(2)).getStatus();

        assertEquals(account.getBalance(), new BigDecimal("45.00"));

        account.addCard(_Card);
        assertEquals(account.getCards().size(), 2);

        account.useCardPayment(123456789L, new BigDecimal("5.00"));
        verify(_Card, times(4)).getNumber();
        verify(_Card, times(3)).getStatus();

        assertEquals(account.getBalance(), new BigDecimal("40.00"));
    }

    @AfterEach
    public void clearDataEach(){
        account = null;
    }

    @AfterAll
    public void clearDataAll(){
        account = null;
    }

}