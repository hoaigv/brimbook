package com.example.bookshop.utils.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AgeValidator implements ConstraintValidator<AgeConstraint, String> {

    @Override
    public boolean isValid(String ageField, ConstraintValidatorContext context) {
        if (ageField == null) {
            return true;
        }

        try {
            int age = Integer.parseInt(ageField);
            return age >= 18;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
