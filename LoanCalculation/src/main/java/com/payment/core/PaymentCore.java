package com.payment.core;

import com.payment.dto.PaymentPlan;
import lombok.extern.slf4j.Slf4j;
import org.decimal4j.util.DoubleRounder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


@Component
@Slf4j
public class PaymentCore {

    private int NO_OF_MONTHS_IN_YEAR = 12;
    private int NO_OF_DAYS_IN_MONTH = 30;
    private int NO_OF_DAYS_IN_YEAR = 360;

    /**
     * This method is used for calculating the annuity value for service
     *
     * @param totalAmount
     * @param nominalInterestRate
     * @param period
     * @return annuity
     */
    public double getAnnuity(final double totalAmount, final double nominalInterestRate, final double period) {

        double monthlyInterest = (nominalInterestRate / NO_OF_MONTHS_IN_YEAR) / 100;
        double annuityValue = applyAnnuityFormula(totalAmount, monthlyInterest, period);

        log.info("Annuity calculated.");
        return roundOff(annuityValue);
    }

    /**
     * This method is the actual formula used for calculating annuity
     *
     * @param totalAmount
     * @param monthlyInterest
     * @param period
     * @return annuity
     */
    private double applyAnnuityFormula(double totalAmount, double monthlyInterest, double period) {

        double numeratorValue = totalAmount * monthlyInterest;
        double exponentialValue = Math.pow(1 + monthlyInterest, -period);
        double denominatorValue = 1 - exponentialValue;
        double result = numeratorValue / denominatorValue;

        return result;
    }


    /**
     * This method is used for calculating the interest to be paid with every payment
     *
     * @param nominalInterestRate
     * @param initialOutstandingPrincipal
     * @return interest value
     */
    private double getInterest(double nominalInterestRate, double initialOutstandingPrincipal) {

        double interestInCents = (nominalInterestRate * NO_OF_DAYS_IN_MONTH *
                initialOutstandingPrincipal) / NO_OF_DAYS_IN_YEAR;

        return roundOff((interestInCents / 100));
    }

    /**
     * This method is used for calculating the actual principal amount to be paid every month
     *
     * @param annuity
     * @param interest
     * @return principal amount
     */
    private double getPrincipal(double annuity, double interest) {

        double principal = annuity - interest;
        return roundOff(principal);
    }

    /**
     * This method is used to calculating the whole payment plan. It calculates
     * all the necessary information that is to be shown to the customer.
     *
     * @param nominalIntRate
     * @param amount
     * @param duration
     * @param startDate
     * @param annuity
     * @return it returns the list of Payments to be made every month for given
     * period of time to the service class. payment includes the monthly interest as well.
     */
    public List<PaymentPlan> getPaymentPlan(final double nominalIntRate, final double amount, final int duration,
                                            final String startDate, final double annuity) {

        log.debug("nominalIntRate:{}, duration:{}, startDate:{}", nominalIntRate, duration, startDate);

        LocalDate paymentStartDate = changeToLocalDate(startDate);
        LocalDate paymentEndDate = paymentStartDate.plusMonths(duration);
        double initialOutstandingPrincipal = amount;
        double borrowerPaymentAmount = annuity;

        List<PaymentPlan> paymentPlanList = new ArrayList<>();

        for (LocalDate date = paymentStartDate; date.isBefore(paymentEndDate); date = date.plusMonths(1)) {

            double interest = getInterest(nominalIntRate, initialOutstandingPrincipal);
            double totalPrincipal = getPrincipal(borrowerPaymentAmount, interest);
            double principal = totalPrincipal < initialOutstandingPrincipal ? totalPrincipal : initialOutstandingPrincipal;
            double remainingPrincipal = roundOff(initialOutstandingPrincipal - principal);

            borrowerPaymentAmount = roundOff(principal + interest);

            paymentPlanList.add(new PaymentPlan(borrowerPaymentAmount, date.toString(), initialOutstandingPrincipal,
                    interest, principal, remainingPrincipal));

            initialOutstandingPrincipal = remainingPrincipal;
        }
        log.info("Payment plan calculated for {} months", duration);
        return paymentPlanList;
    }

    /**
     * Method is used for converting the ZonedDateTime to LocalDate
     *
     * @param strZonedDateTime
     * @return LocalDate
     */
    private LocalDate changeToLocalDate(String strZonedDateTime) {
        return ZonedDateTime.parse(strZonedDateTime).toLocalDate();
    }

    /**
     * Method is used to round off the double value up to 2 precision
     *
     * @param value
     * @return double value
     */
    private double roundOff(double value) {
        return DoubleRounder.round(value, 2);
    }

}
