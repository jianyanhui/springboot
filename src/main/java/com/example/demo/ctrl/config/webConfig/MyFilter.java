package com.example.demo.ctrl.config.webConfig;


import com.example.demo.ctrl.util.LogToString;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
//过滤器
//过滤器作用教程：https://www.cnblogs.com/byw-/p/9255836.html
//过滤器：https://blog.csdn.net/a_helloword/article/details/80026351

public class MyFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest hrequest = (HttpServletRequest)servletRequest;
        System.out.println("过滤器请求入参："+ LogToString.objectToString(hrequest.getParameterMap()));
        HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper((HttpServletResponse) servletResponse);
//        if(hrequest.getRequestURI().indexOf("/index") != -1 ||
//                hrequest.getRequestURI().indexOf("/asd") != -1 ||
//                hrequest.getRequestURI().indexOf("/online") != -1 ||
//                hrequest.getRequestURI().indexOf("/login") != -1
//        ) {//如果这几种链接，则不拦截
//            System.out.println("过滤器开始处理");
//            filterChain.doFilter(servletRequest, servletResponse);//不拦截，放行
//        }else {//否则跳登录页
//            System.out.println("跳登录页");
//            wrapper.sendRedirect("/login.html");
//        }
        //写死放行
        filterChain.doFilter(servletRequest, servletResponse);//不拦截，放行
    }
    @Override
    public void destroy() {
        System.out.println("过滤器结束");
    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("过滤器初始化数据");
    }
}