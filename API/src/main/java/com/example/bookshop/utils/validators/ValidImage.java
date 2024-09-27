package com.example.bookshop.utils.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.PARAMETER ,ElementType.CONSTRUCTOR, ElementType.METHOD , ElementType.ANNOTATION_TYPE , ElementType.LOCAL_VARIABLE , ElementType.TYPE_PARAMETER , ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ImageValidator.class)
public @interface ValidImage {

    String message() default "Invalid file format. Only JPG or PNG or webp are allowed.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

