package com.bsep.admin.app.validators;

import com.bsep.admin.app.utils.ExtensionsUtil;
import com.bsep.admin.app.validators.constraints.ExtendedKeyUsageConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ExtendedKeyUsageValidator implements
        ConstraintValidator<ExtendedKeyUsageConstraint, List<String>> {
    @Override
    public void initialize(ExtendedKeyUsageConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<String> value, ConstraintValidatorContext context) {
        if (value.size() !=0 ) {
            for (String keyUsage : value) {
                if (!ExtensionsUtil.extendedKeyName(keyUsage).equals("")) {
                    return true;
                }
            }

        }
        return false;
    }
}
