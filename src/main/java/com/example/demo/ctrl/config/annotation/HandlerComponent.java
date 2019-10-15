package com.example.demo.ctrl.config.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 用在一个类上声明一个处理器名称集；
 * 用在方法上，标识当前线程方法中需要执行的处理器的名字
 */
@Documented
@Target({ElementType.METHOD,ElementType.TYPE})   //注明在方法和类上是使用
@Retention(RetentionPolicy.RUNTIME)
@Component   //被IOC容器管理
public @interface HandlerComponent {
    /**
     * 指定处理器名称集，默认无名的处理器会被忽略
     * @return 处理器名称数组
     */
    String[] value() default "";
    /**
     * 线程执行的处理器
     * @return 处理器名称
     */
    String threadHandler() default "";
    /**
     * 制定处理器组，主要为策略处理器/forkjoin处理器进行分组
     * @return
     */
    String group() default "";


}
