package com.example.demo.ctrl.config.aspect;

import com.example.demo.ctrl.config.annotation.HandlerComponent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 处理器组件切面,这个切面主要用于临时存储数据
 */
@Aspect   //切面必须的注解
@Slf4j
@Component   //表示这是一个bean，被IOC控制翻转托管
public class HandlerComponentAspect {
    public static final ThreadLocal<String> current_handler=ThreadLocal.withInitial(() ->"");
    /**
     * 切入点表达式
     */
    @Pointcut("@annotation(com.example.demo.ctrl.config.annotation.HandlerComponent)")  //指定执行条件
    public void pointCut() {
    }

    /*前置通知*/
    @Before("pointCut() && @annotation(handlerComponent)") //括号里面指定执行条件作为入参
    public void before(JoinPoint joinPoint, HandlerComponent handlerComponent) {
        log.info("进入HandlerComponentAspect");
        current_handler.set(handlerComponent.threadHandler());
    }

    public static void main(String[] args) {
        current_handler.set("1");
        current_handler.set("2");
        current_handler.set("3");
        System.out.println("结果："+current_handler.get());

    }

}
