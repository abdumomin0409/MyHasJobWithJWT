package com.company.job.myhasjobwithjwt.costom_annotations.validators;

import com.company.job.myhasjobwithjwt.costom_annotations.annotations.UniquePhoneNumber;
import com.company.job.myhasjobwithjwt.service.auth.AuthService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueNumberValidator implements ConstraintValidator<UniquePhoneNumber, String> {

    private final AuthService authUserService;

    @Override
    public void initialize(UniquePhoneNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !authUserService.existsByPhoneNumber(s);
    }
}
