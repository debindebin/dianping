package com.yiyiglobal.dp.util;

import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * Created by zhangdebin on 2018/5/3.
 */
public class Validation {

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
}
