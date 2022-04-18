package com.bsep.admin.app.validators.constraints;

import com.bsep.admin.app.validators.ExtendedKeyUsageValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ExtendedKeyUsageValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExtendedKeyUsageConstraint {
    String message() default "Invalid extended key usage name";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
