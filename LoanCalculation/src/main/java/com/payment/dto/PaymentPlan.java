package com.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaymentPlan {

    double borrowerPaymentAmount;
    String date ;
    double initialOutstandingPrincipal;
    double interest ;
    double principalAmount;
    double remainingOutstandingPrincipal;

}
