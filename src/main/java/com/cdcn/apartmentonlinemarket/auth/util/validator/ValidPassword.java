package com.cdcn.apartmentonlinemarket.auth.util.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidPasswordValidator.class)
@Documented
public @interface ValidPassword {
    String message() default "Password must be minimum 6 characters, at least 1 uppercase letter and " +
            "1 number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}