package com.iqilu.message.transfer.aop;


import com.iqilu.message.transfer.aop.aspect.PageSizeHandler;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author 卢斌
 */
@Constraint(validatedBy = {PageSizeHandler.class})
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface PageSize {

    String message() default "您输入了无效的页容量大小";

    // 约束注解在验证时所属的组别
    Class<?>[] groups() default {};

    // 约束注解的有效负载
    Class<? extends Payload>[] payload() default {};

    /**
     * 页码容量最大值
     */
    int max() default 20;
}
