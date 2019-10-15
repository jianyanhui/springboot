package com.example.demo.ctrl.config.annotation;


import java.lang.annotation.*;

/**
 * 排除上下文缓存，在不需要缓存上下文信息的情况下使用，比如内部系统间接调用
 */
@Documented
@Target({ElementType.METHOD})   //注明在方法上是使用
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcludeContextCache {
}
