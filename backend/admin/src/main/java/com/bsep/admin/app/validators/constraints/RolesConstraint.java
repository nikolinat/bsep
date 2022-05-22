package com.bsep.admin.app.validators.constraints;

import com.bsep.admin.app.validators.RolesValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RolesValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface RolesConstraint {
    String message() default "Invalid roles";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
