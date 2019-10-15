package com.example.demo.ctrl.config.handler;

/**
 * 处理器接口定义
 * @param <T> 待处理对象
 */
public interface Handler<T> {

    /**
     * 是否支持该参数进行处理
     * @param t 待处理参数，实现时注意用final修饰，防止误修改
     * @return
     */
    boolean isEligible(final T t);

    /**
     * 处理操作 待处理参数，实现时注意用final修饰，防止误修改
     * @param t
     */
    void handle(final T t);
}
