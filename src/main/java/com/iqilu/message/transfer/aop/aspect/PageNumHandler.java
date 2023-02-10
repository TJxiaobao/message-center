package com.iqilu.message.transfer.aop.aspect;


import com.iqilu.message.transfer.aop.PageNum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author 卢斌
 */
public class PageNumHandler implements ConstraintValidator<PageNum, Integer> {

    private int max;

    @Override
    public boolean isValid(Integer aLong, ConstraintValidatorContext constraintValidatorContext) {
        if (aLong == null) {
            return false;
        }
        if (max <= 0) {
            return aLong > 0;
        } else {
            return aLong > 0 && aLong <= max;
        }
    }

    @Override
    public void initialize(PageNum constraintAnnotation) {
        this.max = constraintAnnotation.max();
    }
}
