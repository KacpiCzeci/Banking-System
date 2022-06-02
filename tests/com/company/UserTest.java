package com.company;

import com.company.BankProduct.BankProductStatus;
import com.company.Card.Card;
import com.company.Report.ReportVisitor;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class UserTest {

    public User user;

    @Mock ReportVisitor _ReportVisitor;
    @Mock Bank _Bank;

    @BeforeEach
    public void setUpEach() {
        MockitoAnnotations.initMocks(this);
        user = new User("123456789", _Bank);
    }

    @Test
    public void acceptVisitorTest(){
        user.acceptVisitor(_ReportVisitor);
        verify(_ReportVisitor, times(1)).visitUser(user);
    }

    @Test
    void setStatusDeleteWhenActiveTest() {
        assertEquals(user.getStatus(), UserStatus.ACTIVE);
        user.setStatus(UserStatus.DELETED);
        assertEquals(user.getStatus(), UserStatus.DELETED);
    }

    @Test
    void setStatusDeleteWhenDeletedTest() {
        user.setStatus(UserStatus.DELETED);
        assertEquals(user.getStatus(), UserStatus.DELETED);
        user.setStatus(UserStatus.DELETED);
        assertEquals(user.getStatus(), UserStatus.DELETED);
    }

    @Test
    void setStatusActiveWhenActiveTest() {
        assertEquals(user.getStatus(), UserStatus.ACTIVE);
        user.setStatus(UserStatus.ACTIVE);
        assertEquals(user.getStatus(), UserStatus.ACTIVE);
    }

    @Test
    void setStatusActiveWhenDeleteTest() {
        user.setStatus(UserStatus.DELETED);
        assertEquals(user.getStatus(), UserStatus.DELETED);
        user.setStatus(UserStatus.ACTIVE);
        assertEquals(user.getStatus(), UserStatus.ACTIVE);
    }

    @AfterEach
    public void clearDataEach(){
        user = null;
    }
}
