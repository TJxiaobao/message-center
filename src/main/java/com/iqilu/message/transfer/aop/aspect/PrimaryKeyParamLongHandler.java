package com.iqilu.message.transfer.aop.aspect;


import com.iqilu.message.transfer.aop.PrimaryKeyParam;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 注解逻辑处理：当参数类型为Long时通过本实现类处理
 *
 * @author 卢斌
 */
public class PrimaryKeyParamLongHandler implements ConstraintValidator<PrimaryKeyParam, Long> {

    private boolean require;

    private long min;

    private long max;

    @Override
    public void initialize(PrimaryKeyParam constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
        this.require = constraintAnnotation.require();
    }

    @Override
    public boolean isValid(Long aLong, ConstraintValidatorContext constraintValidatorContext) {
        if (require && aLong == null) {
            return false;
        } else {
            if (aLong == null) {
                return true;
            }
        }
        if (aLong < min) {
            return false;
        }
        return aLong <= max;
    }
}
