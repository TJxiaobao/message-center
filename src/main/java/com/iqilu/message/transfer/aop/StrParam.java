package com.iqilu.message.transfer.aop;

import com.iqilu.message.transfer.aop.aspect.StringParamHandler;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 卢斌
 */
@Constraint(validatedBy = {StringParamHandler.class})
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
public @interface StrParam {

    String message() default "请检查您写入的内容是否有误";

    // 约束注解在验证时所属的组别
    Class<?>[] groups() default {};

    // 约束注解的有效负载
    Class<? extends Payload>[] payload() default {};

    /**
     * 是否为必须参数
     */
    boolean require() default true;


    /**
     * 最小长度
     */
    int minLength() default  1;

    /**
     * 最大长度
     */
    int maxLength() default Integer.MAX_VALUE;

    /**
     * 是否允许包含空格
     */
    boolean acceptSpace() default true;

}
