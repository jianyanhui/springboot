package com.example.demo.ctrl.config.aspect;

import com.example.demo.ctrl.util.LogToString;
import com.example.demo.ctrl.util.MDCUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * 打印日志切面
 *
 * 解释:https://blog.csdn.net/youzi749/article/details/84386674
 * AOP通常意思是： 面向切面编程，通过预编译方式和运行期动态代理实现程序功能的统一维护的一种技术.AOP是OOP的延续，是软件开发中的一个热点，也是Spring框架中的一个重要内容，是函数式编程的一种衍生范型。利用AOP可以对业务逻辑的各个部分进行隔离，从而使得业务逻辑各部分之间的耦合度降低，提高程序的可重用性，同时提高了开发的效率。
 * 常用的业务环境： 配置事务、日志、权限验证、用户请求时做一些特殊处理
 *
 * 常用注解：
 * @Aspect:作用是把当前类标识为一个切面供容器读取
 * @Pointcut：Pointcut是植入Advice的触发条件。每个Pointcut的定义包括2部分，一是表达式，二是方法签名。方法签名必须是 public及void型。可以将Pointcut中的方法看作是一个被Advice引用的助记符，因为表达式不直观，因此我们可以通过方法签名的方式为 此表达式命名。因此Pointcut中的方法只需要方法签名，而不需要在方法体内编写实际代码。
 * @Around：环绕增强，相当于MethodInterceptor
 * @AfterReturning：后置增强，相当于AfterReturningAdvice，方法正常退出时执行
 * @Before：标识一个前置增强方法，相当于BeforeAdvice的功能，相似功能的还有
 * @AfterThrowing：异常抛出增强，相当于ThrowsAdvice
 * @Component 注解 把切面类加入到IOC容器中
 * @Aspect 注解 使之成为切面类
 */
@Aspect   //切面必须的注解
@Slf4j
@Component   //表示这是一个bean，被IOC控制翻转托管
@Order(1)  //指定执行顺序
public class LogAspect {

    //请求开始时间
    //ThreadLocal为解决多线程程序的并发问题提供了一种新的思路,成员变量需要考虑线程安全，局部变量不需要考虑线程安全
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    /**
     * 切入点表达式,@RequestMapping请求时触发，一般在Controller层会使用@RequestMapping用来映射请求与方法
     */
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")  //指定执行条件
    public void pointCut() {
    }

    /*前置通知*/
    @Before("pointCut()") //括号里面指定执行条件
    public void before(JoinPoint joinPoint) {
        log.info("进入LogAspect");
        ThreadLocalPool.request_time.set(System.currentTimeMillis());
        ThreadLocalPool.request_id.set(UUID.randomUUID().toString());
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Signature signature = joinPoint.getSignature();
        MDCUtil.clear();
        MDCUtil.setFlowId("2222");
        MDCUtil.setActionId("33333");
        // 记录下请求内容
        log.info("request_id : " + ThreadLocalPool.request_id.get());
        log.info("URL : " + request.getRequestURL().toString());
        log.info("HTTP_METHOD : " + request.getMethod());
        log.info("IP : " + request.getRemoteAddr());
        //类名和方法名
        log.info("CLASS_METHOD : " + signature.getDeclaringTypeName() + "." + signature.getName());
        //请求参数
        log.info("请求参数：" + LogToString.objectToString(request.getParameterMap()));
        log.info("请求参数：" + LogToString.objectToString(joinPoint.getArgs()));


    }

    /**
     * 请求正常返回
     */
    @AfterReturning(pointcut = "pointCut()", returning = "ret")  //returning指定返回值名称
    public void afterReturning(JoinPoint joinPoint, Object ret) {
        // 处理完请求，返回内容
        log.info("RESPONSE : " + ret);
        log.info("SPEND TIME : " + (System.currentTimeMillis() - ThreadLocalPool.request_time.get()));
    }

}
