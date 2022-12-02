package com.cdcn.apartmentonlinemarket.auth.util.validator;

import com.cdcn.apartmentonlinemarket.auth.dto.request.SignupCommand;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        if (o instanceof SignupCommand) {
            SignupCommand signupCommand = (SignupCommand) o;
            return signupCommand.getPassword().equals(signupCommand.getConfirmPassword());
        }
        return true;
    }
}
