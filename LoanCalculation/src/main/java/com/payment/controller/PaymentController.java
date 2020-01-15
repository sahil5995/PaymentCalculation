package com.payment.controller;

import com.payment.dto.LoanRequestObj;
import com.payment.dto.PaymentPlan;
import com.payment.service.PaymentService;
import com.payment.validation.FieldValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private FieldValidator validator;


    @GetMapping("/generate-plan")
    public List<PaymentPlan> getPaymentPlan(@RequestBody LoanRequestObj loanRequestObj) {

        log.info("Getting Payment plan.");

        if (validator.validateFields(loanRequestObj)) {
            final List<PaymentPlan> paymentPlan = paymentService.getPaymentPlan(loanRequestObj);
            return paymentPlan;
        }
        return new ArrayList<>();
    }


}
