package com.iqilu.message.transfer.aop;



import com.iqilu.message.transfer.aop.aspect.PageNumHandler;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author 卢斌
 */
@Constraint(validatedBy = {PageNumHandler.class})
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface PageNum {

    String message() default "您输入了无效的页码号";

    // 约束注解在验证时所属的组别
    Class<?>[] groups() default {};

    // 约束注解的有效负载
    Class<? extends Payload>[] payload() default {};

    int max() default 0;

}
