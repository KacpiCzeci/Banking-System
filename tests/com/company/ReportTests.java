package com.company;

import com.company.BankProduct.BankProduct;
import com.company.Report.ReportType;
import com.company.Report.UserPaymentReport;
import com.company.Transaction.TransactionCommand;
import com.company.Transaction.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.management.monitor.Monitor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReportTests {

    private UserPaymentReport userPaymentReport=new UserPaymentReport(ReportType.USERPAYMENT);

    @Mock BankProduct bankProductMOCK;
    @Mock TransactionCommand transactionCommandPaymentMOCK;
    @Mock TransactionCommand transactionCommandWithdrwalMOCK;
    @Mock User userMOCK;

    @BeforeEach
    public void setAccountsList() {

        MockitoAnnotations.initMocks(this);

        when(bankProductMOCK.getBalance()).thenReturn(new BigDecimal(1.0d));
        when(transactionCommandPaymentMOCK.getType()).thenReturn(TransactionType.PAYMENT);
        when(transactionCommandWithdrwalMOCK.getType()).thenReturn(TransactionType.WITHDRAWAL);
        when(userMOCK.getId()).thenReturn("123456789");
    }

    @Test public void CheckTypeOfGetContextIsEmptyString(){
        assertTrue(userPaymentReport.getContent().isEmpty());
    }
    @Test public void TestGetContextIsNotEmpty(){
        //given
        String expectedContent = "";
        expectedContent = expectedContent + "=======================================\n";
        expectedContent = expectedContent + "User: "  + "\n";
        expectedContent = expectedContent + "Total balance: " + "0.00" + "\n";
        expectedContent = expectedContent + "Total number of payments: " + "0" + "\n";
        expectedContent = expectedContent + "Total number of withdrawals: " + "0" + "\n";
        expectedContent = expectedContent + "Total number of operations: " + "0" + "\n";
        expectedContent = expectedContent + "Total number of bank products: " + "0" + "\n";
        //when
        userPaymentReport.generateReport();
        //then
        assertTrue(expectedContent.equals(userPaymentReport.getContent()));
    }

    @Test
    public void TestVisitorToBankProdukt(){
        //given
        BigDecimal expectedBalance = new BigDecimal(1.00d).setScale(2);
        Integer expectedAllAccount = 1;
        //when
        userPaymentReport.visitBankProduct(bankProductMOCK);//visitor
        //then
        userPaymentReport.generateReport();
        String[] lines = userPaymentReport.getContent().split(System.getProperty("line.separator"));
        BigDecimal acquiredBalance= new BigDecimal(lines[2].replaceAll("([^\\d.])",""));
        Integer acquiredAllAccount = Integer.valueOf(lines[6].replaceAll("([^\\d.])",""));

        assertArrayEquals(new Object[]{expectedBalance,expectedAllAccount},new Object[]{acquiredBalance,acquiredAllAccount});
    }


    @Test
    public void  TestVisitorToTransactionPayment()
    {
        //given
        Integer expectedNummberOfPayments=1;
        Integer expectedNumberOfOperation=1;
        //when
        userPaymentReport.visitTransaction(transactionCommandPaymentMOCK); //visitor
        //then
        userPaymentReport.generateReport();

        String[] lines = userPaymentReport.getContent().split(System.getProperty("line.separator"));
        Integer acquiredNummberOfPayments= Integer.valueOf(lines[3].replaceAll("([^\\d.])",""));
        Integer acquiredNumberOfOperation = Integer.valueOf(lines[5].replaceAll("([^\\d.])",""));

        assertArrayEquals(new Object[]{expectedNummberOfPayments,expectedNumberOfOperation},new Object[]{acquiredNummberOfPayments,acquiredNumberOfOperation});
    }
    @Test
    public void  TestVisitorToTransactionWithdrwal()
    {
        //given
        Integer expectedNummberOfWithdrwal=1;
        Integer expectedNumberOfOperation=1;
        //when
        userPaymentReport.visitTransaction(transactionCommandWithdrwalMOCK); //visitor
        //then
        userPaymentReport.generateReport();

        String[] lines = userPaymentReport.getContent().split(System.getProperty("line.separator"));
        Integer acquiredNummberOfWithdrwal= Integer.valueOf(lines[4].replaceAll("([^\\d.])",""));
        Integer acquiredNumberOfOperation = Integer.valueOf(lines[5].replaceAll("([^\\d.])",""));

        assertArrayEquals(new Object[]{expectedNummberOfWithdrwal,expectedNumberOfOperation},new Object[]{acquiredNummberOfWithdrwal,acquiredNumberOfOperation});
    }
    @Test
    public void TestVisitorToUser(){
//        given
        String expextedUser="123456789";
//        when
        userPaymentReport.visitUser(userMOCK);
//        then
        userPaymentReport.generateReport();
        String[] lines = userPaymentReport.getContent().split(System.getProperty("line.separator"));
        String acquiredUser= lines[1].replaceAll("([^\\d.])","");

        assertEquals(expextedUser,acquiredUser);

    }
    @Test
    public void CheckGeneratedReportIsOkey(){
//        given
        String expextedUser="123456789";
        BigDecimal expectedBalance = new BigDecimal(1.00d).setScale(2);
        int expectedNummberOfPayments=2;
        int expectedNummberOfWithdrwal=3;
        int expectedNumberOfOperation=5;
        int expectedAllAccount = 1;

        String expectedContent = "";
        expectedContent = expectedContent + "=======================================\n";
        expectedContent = expectedContent + "User: " + expextedUser + "\n";
        expectedContent = expectedContent + "Total balance: " + expectedBalance.toString() + "\n";
        expectedContent = expectedContent + "Total number of payments: " + Integer.toString(expectedNummberOfPayments) + "\n";
        expectedContent = expectedContent + "Total number of withdrawals: " + Integer.toString(expectedNummberOfWithdrwal) + "\n";
        expectedContent = expectedContent + "Total number of operations: " + Integer.toString(expectedNumberOfOperation) + "\n";
        expectedContent = expectedContent + "Total number of bank products: " + Integer.toString(expectedAllAccount) + "\n";
//        when
        userPaymentReport.visitBankProduct(bankProductMOCK);
        userPaymentReport.visitTransaction(transactionCommandPaymentMOCK);
        userPaymentReport.visitTransaction(transactionCommandPaymentMOCK);
        userPaymentReport.visitTransaction(transactionCommandWithdrwalMOCK);
        userPaymentReport.visitTransaction(transactionCommandWithdrwalMOCK);
        userPaymentReport.visitTransaction(transactionCommandWithdrwalMOCK);
        userPaymentReport.visitUser(userMOCK);
//        then
        userPaymentReport.generateReport();

        assertTrue(userPaymentReport.getContent().equals(expectedContent));
    }

    /**
     * Check if the entered date is correct with the real one
     * The precision is up to 1 second
     */
    @Test
    public void CheckGeneratedReportData(){
        //given
        LocalDateTime expextedData=LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        //when
        userPaymentReport.generateReport();
        //then
        LocalDateTime acquiredData=userPaymentReport.getGenerated().truncatedTo(ChronoUnit.SECONDS);

        assertTrue(expextedData.equals(acquiredData));
    }
}