package com.company;

import com.company.Transaction.TransactionType;
import com.company.TransferVerification.PaymentVerification;
import com.company.TransferVerification.TransferVerification;
import com.company.TransferVerification.TransferVerificationCreator;
import com.company.TransferVerification.WithdrawalVerification;
import org.junit.jupiter.api.*;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

public class TransferVerificationCreatorTest {

    public TransferVerificationCreator transferVerificationCreator;

    @Mock Bank _Bank;

    @BeforeEach
    public void setUp(){
        transferVerificationCreator = new TransferVerificationCreator(_Bank);
    }

    @Test
    public void createPaymentTest(){
        TransferVerification transferVerification = transferVerificationCreator.create(TransactionType.PAYMENT);

        assertEquals(transferVerification.getClass(), PaymentVerification.class);
    }

    @Test
    public void createWithdrawalTest(){
        TransferVerification transferVerification = transferVerificationCreator.create(TransactionType.WITHDRAWAL);

        assertEquals(transferVerification.getClass(), WithdrawalVerification.class);
    }

    @AfterEach
    public void clearData(){
        transferVerificationCreator = null;
    }
}
