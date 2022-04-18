package com.bsep.admin.app.validators;

import com.bsep.admin.app.utils.SubjectUtil;
import com.bsep.admin.app.validators.constraints.DirectoryNameConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashMap;
import java.util.Map;

public class DirectoryNameValidator implements
        ConstraintValidator<DirectoryNameConstraint, String> {
    @Override
    public void initialize(DirectoryNameConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null) {
            String[] split = SubjectUtil.splitSubject(value);
            HashMap<String, String> map = SubjectUtil.convertToMap(split);
            for (Map.Entry<String, String> pair : map.entrySet()) {
                if (pair.getKey().equals("C")) {
                    if (pair.getValue().length() != 2) {
                        return false;
                    }
                }

            }


        }
        return true;
    }

}
