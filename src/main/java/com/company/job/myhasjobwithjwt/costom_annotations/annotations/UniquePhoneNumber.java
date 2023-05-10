package com.company.job.myhasjobwithjwt.costom_annotations.annotations;

import com.company.job.myhasjobwithjwt.costom_annotations.validators.UniqueNumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueNumberValidator.class)
public @interface UniquePhoneNumber {

    String message() default "Phone Number is already registered";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
