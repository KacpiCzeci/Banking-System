package com.company;

import com.company.BankProduct.Account;
import com.company.InterBankPayment.IBPAagency;
import com.company.Report.PaymentReport;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReportTests {

    private PaymentReport paymentReport = new PaymentReport();
    private ArrayList<Account> accountTestList = new ArrayList<>();

    @Mock private Account account1;
    @Mock private Account account2;
    @Mock private IBPAagency ibpaAgency;



    @BeforeEach
    public void setAccountsList() {

        MockitoAnnotations.initMocks(this);

        accountTestList.add(account1);
        accountTestList.add(account2);

        when(account1.getId()).thenReturn("1");
        when(account1.getOwner()).thenReturn(new User("1-9",new Bank("1","bank1",ibpaAgency)));
        when(account1.getBalance()).thenReturn(BigDecimal.valueOf(111.0));
        when(account2.getId()).thenReturn("2");
        when(account2.getOwner()).thenReturn(new User("2-9",new Bank("1","bank1",ibpaAgency)));
        when(account2.getBalance()).thenReturn(BigDecimal.valueOf(222.0));
    }
    @Test
    public void checkIfGenerateReportAboutAccountsIsRunOneTime() {
        PaymentReport paymentReportMock=mock(PaymentReport.class);
        paymentReportMock.generateRaport(accountTestList);
        verify(paymentReportMock, times(1)).generateRaport(accountTestList);
    }
    @Test
    public void seeReport()
    {
        paymentReport.generateRaport(accountTestList);
        String expected="=======================================\n" +
                "Account: 1\n" +
                "Owner: "+account1.getOwner().toString()+"\n" +
                "Money: 111.0\n" +
                "History of operations: \n" +
                "=======================================\n" +
                "=======================================\n" +
                "Account: 2\n" +
                "Owner: "+account2.getOwner().toString()+"\n" +
                "Money: 222.0\n" +
                "History of operations: \n" +
                "=======================================\n";
//        System.out.println(paymentReport.getContent());
//        System.out.println(s);
        String actual=paymentReport.getContent();
        assertEquals(expected,actual);
    }

}