package com.company;


import com.company.BankProduct.Account;
import com.company.Card.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class CardTest {

    public Card card;

    @Mock public Account account;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

//    @Test
//    void payByCard() {
//        card = new Card(account, 123L, 123, null);
//        account.useCardPayment(card, account, 200.00);
//        verify(account, times(1)).useCardPayment(card, account, 200.00);
//    }
}