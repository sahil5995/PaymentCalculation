package com.payment.validation;

import com.payment.dto.LoanRequestObj;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class FieldValidator {

    /**
     *  This method is used to validate the empty fields
     * @param loanRequestObj
     * @return boolean
     */
    public boolean validateFields(LoanRequestObj loanRequestObj) {

        if (StringUtils.isEmpty(loanRequestObj.getDuration()) || StringUtils.isEmpty(loanRequestObj.getNominalRate())
                || StringUtils.isEmpty(loanRequestObj.getLoanAmount()) || StringUtils.isEmpty(loanRequestObj.getStartDate())) {
            throw new ValidationException("Request body is invalid..!!");
        }
        return true;
    }

}
