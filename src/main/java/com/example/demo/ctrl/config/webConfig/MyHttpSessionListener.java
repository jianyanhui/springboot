package com.example.demo.ctrl.config.webConfig;


import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
//监听器
/*
* 监听器:https://blog.csdn.net/zhangguoqin66/article/details/81018081
*
* */
public class MyHttpSessionListener implements HttpSessionListener {

    public static int online = 0;
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("创建session");
        online ++;
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("销毁session");

    }



}
