package com.iqilu.message.transfer.aop.aspect;

import com.iqilu.message.transfer.aop.StrParam;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author 卢斌
 */
public class StringParamHandler implements ConstraintValidator<StrParam, String> {

    private boolean acceptSpace;

    private int minLength;

    private int maxLength;

    private boolean require;

    @Override
    public void initialize(StrParam constraintAnnotation) {
        this.require = constraintAnnotation.require();
        this.maxLength = constraintAnnotation.maxLength();
        this.minLength = constraintAnnotation.minLength();
        this.acceptSpace = constraintAnnotation.acceptSpace();
        if (this.minLength < 0) {
            this.minLength = 0;
        }
        if (this.maxLength < this.minLength) {
            this.maxLength = this.minLength;
        }
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return !require;
        }
        if (s.length() > maxLength) {
            return false;
        }
        if (s.length() < minLength) {
            return false;
        }
        if (! acceptSpace) {
            return !s.contains(" ");
        }
        return true;
    }
}
