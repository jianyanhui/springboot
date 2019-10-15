package com.example.demo.ctrl.config.aspect;

/**
 * thread池，为了线程安全考虑，临时存储变量
 * ThreadLocal类为解决多线程程序的并发问题提供了一种新的思路
 */
public class ThreadLocalPool {

    //ThreadLocal为解决多线程程序的并发问题提供了一种新的思路,成员变量需要考虑线程安全，局部变量不需要考虑线程安全
    /*请求id*/
    public static final ThreadLocal<String> request_id=new ThreadLocal<>();
    /*请求时间*/
    public static final ThreadLocal<Long> request_time=new ThreadLocal<>();
    public static final void removeAll(){
        request_id.remove();
        request_time.remove();
    }

}
