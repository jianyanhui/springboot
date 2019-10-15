package com.example.demo.ctrl.config.aspect;

import com.example.demo.ctrl.dto.ContextBean;
import com.example.demo.ctrl.util.MDCUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * 会话上下文存取切面，主要用来缓存数据，一般用redis缓存
 */
@Aspect   //切面必须的注解
@Slf4j
@Component   //表示这是一个bean，被IOC控制翻转托管
@Order(2)  //指定执行顺序
public class ThreadContextBeanAspect {
    //过期时间10分钟
    private final int TIMEOUT=10*60;
    private final TimeUnit TIMEUNIT=TimeUnit.SECONDS;
    //request请求
    @Autowired
    private HttpServletRequest request;
//    //redis缓存
//    private RedisTemplate<String,Object> redis;


    /**
     * 切入点表达式,RequestMapping表示请求时触发
     */
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")  //指定执行条件
    public void pointCut() {}

    /*前置通知*/
    @Before("pointCut()") //括号里面指定执行条件
    public void before() {
        log.info("进入ThreadContextBeanAspect");
//        MDCUtil.removeFlowId();
        ContextBean.removeThreadContextBean();
        //创建缓存对象
        ContextBean c=null;
        String flowId=request.getParameter("flowId");//前端传的flowId
        if(StringUtils.hasText(flowId)){
            //从redis获取缓存，判断是否为null，由于没有redis这里写死了数据，不判断
            c=new ContextBean();
            c.setFlowId(flowId);
        }

        if(c==null){
            c=ContextBean.build();
        }

        ContextBean.setThreadContextBean(c);
        MDCUtil.setFlowId(c.getFlowId());
    }


    /**
     * 请求正常返回
     */
    @AfterReturning("pointCut()")  //returning指定返回值名称
    public void afterReturning() {
        // 处理完请求，返回内容
        ContextBean c=ContextBean.getThreadContextBean();
//        redis.set(c.getFlowId(),c,TIMEOUT,TIMEUNIT);
    }
}
