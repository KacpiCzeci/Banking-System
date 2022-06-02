package com.company;

import com.company.BankProduct.BankProduct;
import com.company.InterestRate.ConstantInterestRate;
import com.company.InterestRate.CurveInterestRate;
import com.company.InterestRate.InterestRate;
import com.company.InterestRate.LinearInterestRate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class InterestRateTest {

    public InterestRate interestRate=null;

    @Mock BankProduct bankProductMOCK;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void CheckIfYouGetCurveInterestRate(){
        //when
        interestRate=new CurveInterestRate(bankProductMOCK);
        //then
        assertEquals(CurveInterestRate.class,interestRate.getClass());
    }
    @Test
    public void CheckIfYouGetConstantInterestRate(){
        //when
        interestRate=new ConstantInterestRate(bankProductMOCK);
        //then
        assertEquals(ConstantInterestRate.class,interestRate.getClass());
    }
    @Test
    public void CheckIfYouGetLinearInterestRate(){
        //when
        interestRate=new LinearInterestRate(bankProductMOCK);
        //then
        assertEquals(LinearInterestRate.class,interestRate.getClass());
    }
    @Test
    public void TestLinearInterestRate(){
        //given
        BigDecimal expectedInterestRate = new BigDecimal("1200.00");
        //when
        interestRate=new LinearInterestRate(bankProductMOCK);
        //then
        assertEquals(expectedInterestRate,interestRate.calculateInterestRate(new BigDecimal("0.05"),new BigDecimal("1000"),new BigDecimal("24")));
    }
    @Test
    public void TestConstantInterestRateLessThenFiveMonth(){
        //given
        BigDecimal expectedInterestRate = new BigDecimal("160.00");
        //when
        interestRate=new ConstantInterestRate(bankProductMOCK);
        //then
        System.out.println(interestRate.calculateInterestRate(new BigDecimal("0.04"),new BigDecimal("1000"),new BigDecimal("4")));
        assertEquals(expectedInterestRate,interestRate.calculateInterestRate(new BigDecimal("0.04"),new BigDecimal("1000"),new BigDecimal("4")));
    }
    @Test
    public void TestConstantInterestRateMoreThenFiveMonthBUTLessThenTen(){
        //given
        BigDecimal expectedInterestRate = new BigDecimal("200.00");
        //when
        interestRate=new ConstantInterestRate(bankProductMOCK);
        //then
        BigDecimal eightMonth= interestRate.calculateInterestRate(new BigDecimal("0.04"),new BigDecimal("1000"),new BigDecimal("8"));
        BigDecimal nineMonth= interestRate.calculateInterestRate(new BigDecimal("0.04"),new BigDecimal("1000"),new BigDecimal("8"));

        assertArrayEquals(new Object[]{expectedInterestRate,expectedInterestRate},new Object[]{eightMonth,nineMonth});
    }
    @Test
    public void TestConstantInterestRateLessThenTenMonth(){
        //given
        BigDecimal expectedInterestRate = new BigDecimal("960.00");
        //when
        interestRate=new ConstantInterestRate(bankProductMOCK);
        //then
        assertEquals(expectedInterestRate,interestRate.calculateInterestRate(new BigDecimal("0.04"),new BigDecimal("1000"),new BigDecimal("24")));
    }

    @Test
    public void TestCurveInterestRate(){
        //given
        BigDecimal expectedInterestRate = new BigDecimal("7200.00");
        //when
        interestRate=new CurveInterestRate(bankProductMOCK);
        //then
        assertEquals(expectedInterestRate,interestRate.calculateInterestRate(new BigDecimal("0.05"),new BigDecimal("1000"),new BigDecimal("12")));
    }
}