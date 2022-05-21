package com.bsep.admin.app.validators;

import com.bsep.admin.app.validators.constraints.RolesConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class RolesValidator implements ConstraintValidator<RolesConstraint, List<String>> {
    @Override
    public void initialize(RolesConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<String> value, ConstraintValidatorContext context) {
        if(value.size() > 0) {
            for(String role:value) {
                System.out.println(role);
                if(!(role.equals("2") || role.equals("3") || role.equals("ROLE_TENANT") || role.equals("ROLE_HOUSE_OWNER"))) {
                    return false;
                }
            }
        }
        return true;
    }
}
