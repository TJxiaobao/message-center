package com.iqilu.message.transfer.aop.aspect;


import com.iqilu.message.transfer.aop.PageSize;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author 卢斌
 */
public class PageSizeHandler implements ConstraintValidator<PageSize, Integer> {

    /**
     * 页码容量最大值
     */
    private int max;

    @Override
    public void initialize(PageSize constraintAnnotation) {
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Integer aLong, ConstraintValidatorContext constraintValidatorContext) {
        if (aLong == null) {
            return false;
        }
        return aLong > 0 && aLong <= max;
    }
}
