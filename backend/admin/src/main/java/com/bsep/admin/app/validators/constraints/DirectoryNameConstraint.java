package com.bsep.admin.app.validators.constraints;

import com.bsep.admin.app.validators.DirectoryNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DirectoryNameValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DirectoryNameConstraint {
    String message() default "Country code must me two characters in length.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

