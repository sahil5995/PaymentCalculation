package com.payment.core;


import com.payment.dto.PaymentPlan;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class PaymentCoreTest {

    @Autowired
    private PaymentCore paymentCore;

    @Test
    public void testGetAnnuity() {

        //Arrange
        double amount = 5000;
        double intRate = 5;
        int period = 24;

        //Act
        double annuity = paymentCore.getAnnuity(amount, intRate, period);

        //Assert
        assertEquals(219.36, annuity);
    }

    @Test
    public void testGetPaymentPlan() {

        //Arrange
        double amount = 5000;
        double intRate = 5;
        int period = 24;
        String startDate = "2018-01-01T00:00:01Z";
        double annuity = 219.36;

        //Act
        List<PaymentPlan> resultPaymentPlans = paymentCore.getPaymentPlan(intRate, amount, period, startDate, annuity);

        //Assert
        assertEquals(24, resultPaymentPlans.size());
        assertEquals(219.36, resultPaymentPlans.get(0).getBorrowerPaymentAmount());
    }

}
