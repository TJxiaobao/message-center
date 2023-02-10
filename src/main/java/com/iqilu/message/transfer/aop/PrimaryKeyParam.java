package com.iqilu.message.transfer.aop;



import com.iqilu.message.transfer.aop.aspect.PrimaryKeyParamIntegerHandler;
import com.iqilu.message.transfer.aop.aspect.PrimaryKeyParamLongHandler;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 参数校验注解，用于整形和长整型；限制数值不为空，并要求最小值为1，最大值可以在mysql的11位整形字段中保存。
 * @author 卢斌
 */
@Constraint(validatedBy = {PrimaryKeyParamIntegerHandler.class, PrimaryKeyParamLongHandler.class})
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
public @interface PrimaryKeyParam {
    String message() default "请检查您写入的内容是否有误";

    // 约束注解在验证时所属的组别
    Class<?>[] groups() default {};

    // 约束注解的有效负载
    Class<? extends Payload>[] payload() default {};

    boolean require() default true;

    // 自增主键从1开始
    long min() default 1L;

    // 十一位自增主键的最大值
    long max() default 4294967295L;

}
