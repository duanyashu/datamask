package com.github.duanyashu.annotation;

import com.github.duanyashu.type.DesensitiveType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: 脱敏字段注解
 * @author: duanyashu
 * @time: 2021-02-25 09:50
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DesensitiveInfo {

    DesensitiveType value() default DesensitiveType.BASIC;

    String separator() default "";

    String padStr() default "*";

    int retainLeft() default -1;
    int retainRight() default  -1;

}