package com.lishang.aop.click;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author:LiShang
 * date:  2020-06-11
 * desc:  注解 see{@SingleClickAspectX.onSingleClickMethod()}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SingleClick {
    /**
     * 默认点击时间间隔
     *
     * @return
     */
    long value() default 1500;

    /**
     * 是否防止重复点击
     *
     * @return
     */
    boolean enabled() default true;

    /**
     * 过滤对应的View
     * enabled ：true  过滤的是可以重复点击的View
     * enabled : false 过滤的是不可重复点击的View
     * 一句话，filter里面的View 逻辑是与enable 相反的
     *
     * @return
     */
    int[] filter() default {};
}
