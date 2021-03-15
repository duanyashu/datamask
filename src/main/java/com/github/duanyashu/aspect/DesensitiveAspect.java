package com.github.duanyashu.aspect;

import com.github.duanyashu.utils.DesensitiveUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @description: 定义切面来处理敏感数据接口
 * @author: duanyashu
 * @time: 2021-03-10 14:16
 */
@Aspect
@Component
public class DesensitiveAspect {

    @Pointcut("@annotation(com.github.duanyashu.annotation.DesensitiveApi) || @within(com.github.duanyashu.annotation.DesensitiveApi)")
    public void operation(){}


    @Around("operation()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object obj = joinPoint.proceed();
        if (obj != null && !DesensitiveUtils.isPrimitive(obj.getClass())) {
            //进行脱敏处理
            DesensitiveUtils.format(obj);
        }
        return  obj;
    }
}
