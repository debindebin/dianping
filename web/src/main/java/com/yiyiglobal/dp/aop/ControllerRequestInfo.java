package com.yiyiglobal.dp.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ControllerRequestInfo {
    /**
     * 接口描述
     * @return
     */
    String description() default "";
}
