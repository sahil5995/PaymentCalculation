package com.payment.service;

import com.payment.dto.LoanRequestObj;
import com.payment.dto.PaymentPlan;
import com.payment.core.PaymentCore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PaymentService {

    @Autowired
    private PaymentCore paymentCore;

    /**
     *  This method is called by the API. It further makes call to the business logic
     *  for all the calculations
     * @param loanRequestObj
     * @return it returns the list of Payments to be made every month for given
     *         period of time back to the API call.
     */
    public List<PaymentPlan> getPaymentPlan(LoanRequestObj loanRequestObj) {

        int durationInMonths = Integer.parseInt(loanRequestObj.getDuration());
        double nominalInterestRate = Double.parseDouble(loanRequestObj.getNominalRate());
        double loanAmount = Double.parseDouble(loanRequestObj.getLoanAmount());

        log.info("Calculating Annuity.");
        double annuity = paymentCore.getAnnuity(loanAmount, nominalInterestRate, durationInMonths);

        log.info("Calculating Payment plan for annuity.");
        final List<PaymentPlan> resultPaymentPlan = paymentCore.getPaymentPlan(nominalInterestRate,
                loanAmount, durationInMonths, loanRequestObj.getStartDate(), annuity);

        return resultPaymentPlan;
    }
}
