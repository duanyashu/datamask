package com.github.duanyashu.utils;

import com.github.duanyashu.type.DesensitiveType;
import com.github.duanyashu.annotation.DesensitiveInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.*;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * @description: 脱敏工具类
 * @author: duanyashu
 * @time: 2021-03-12 09:42
 */
public class DesensitiveUtils {

    private DesensitiveUtils() {
    }

    /**
     * 扫描方法注解，脱敏
     *
     * @param obj
     */
    public static void format(Object obj) {
        DesensitiveUtils.formatMethod(obj);
    }

    /**
     *  判断对象类型，进行分类处理
     *
     * @param obj   需要反射对象
     */
    private static void formatMethod(Object obj) {
        if (obj == null || isPrimitive(obj.getClass())) {
            return;
        }
        if (obj.getClass().isArray()) {
            for (Object object : (Object[]) obj) {
                formatMethod(object);
            }
        } else if (Collection.class.isAssignableFrom(obj.getClass())) {
            for (Object o : ((Collection) obj)) {
                formatMethod(o);
            }
        } else if (Map.class.isAssignableFrom(obj.getClass())) {
            for (Object o : ((Map) obj).values()) {
                formatMethod(o);
            }
        } else {
            objFormat(obj);
        }
    }

    /**
     * 只有对象才格式化数据
     *
     * @param obj
     */
    private static void objFormat(Object obj) {
        for (Field field : obj.getClass().getDeclaredFields()) {
            if (isPrimitive(field.getType())) {
                fieldSetSensitiveValue(obj, field);
            }else{
                //对象类型进行下一级处理
                ReflectionUtils.makeAccessible(field);
                Object fieldValue = ReflectionUtils.getField(field, obj);
                if (fieldValue != null) {
                    formatMethod(fieldValue);
                }
            }
        }
    }

    /**
     * 进行脱敏处理的方法
     * @param obj
     * @param field
     */
    private static void fieldSetSensitiveValue(Object obj, Field field)  {
        DesensitiveInfo annotation = field.getAnnotation(DesensitiveInfo.class);
        if (annotation!=null && String.class.isAssignableFrom(field.getType())){
            ReflectionUtils.makeAccessible(field);
            String padStr = annotation.padStr();
            int retainLeft = annotation.retainLeft();
            int retainRight = annotation.retainRight();
            DesensitiveType type = annotation.value();
            String separator = annotation.separator();
            Object fieldValue = ReflectionUtils.getField(field, obj);
            if (fieldValue!=null){
                String source = String.valueOf(fieldValue);
                String s;
                if (retainLeft<0){
                    retainLeft = type.getRetainLeft();
                }
                if (retainRight <0){
                    retainRight = type.getRetainRight();
                }
                if (StringUtils.isBlank(separator)){
                    separator=type.getSeparator();
                }
                //处理普通方式
                if (StringUtils.isBlank(separator)){
                    s = StringUtils.left(source, retainLeft).concat(StringUtils.leftPad(StringUtils.right(source, retainRight), StringUtils.length(source) - retainLeft, padStr));
                //处理带分隔符方式
                }else {
                    String[] split = source.split(separator);
                    String leftStr = StringUtils.left(split[0], retainLeft);
                    String rightStr = StringUtils.right(split[split.length-1], retainRight);
                    split[0] = StringUtils.rightPad(leftStr,StringUtils.length(split[0]),padStr);
                    split[split.length-1] = StringUtils.leftPad(rightStr, StringUtils.length(split[split.length-1]),padStr);
                    s = StringUtils.join(split, separator);

                }
                ReflectionUtils.setField(field, obj, s);
            }

        }
    }

    /**
     * 基本数据类型和String类型判断
     *
     * @param clz
     * @return
     */
    public static boolean isPrimitive(Class<?> clz) {
        try {
            if (String.class.isAssignableFrom(clz) || Date.class.isAssignableFrom(clz)|| BigDecimal.class.isAssignableFrom(clz) || LocalDateTime.class.isAssignableFrom(clz)
                    || LocalDate.class.isAssignableFrom(clz)  || LocalTime.class.isAssignableFrom(clz)  || Year.class.isAssignableFrom(clz)
                    || YearMonth.class.isAssignableFrom(clz)  || Month.class.isAssignableFrom(clz) || clz.isPrimitive()) {
                return true;
            } else {
                return ((Class) clz.getField("TYPE").get(null)).isPrimitive();
            }
        } catch (Exception e) {
            return false;
        }
    }

}
