package com.example.demo.ctrl.config.aspect;

import com.example.demo.ctrl.dto.ContextBean;
import com.example.demo.ctrl.exception.BusinessException;
import com.example.demo.ctrl.exception.CommonResultCode;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 登录检查切面
 */
@Aspect   //切面必须的注解
@Slf4j
@Component   //表示这是一个bean，被IOC控制翻转托管
public class LoginNeededAspect {

    /**
     * 切入点表达式,通过注解来作为触发条件，@LoginNeeded注解会触发这个切面
     */
    @Pointcut("@annotation(com.example.demo.ctrl.config.annotation.LoginNeeded)")  //指定执行条件
    public void pointCut() {
    }

    /*前置通知*/
    @Before("pointCut()") //括号里面指定执行条件
    public void before(JoinPoint joinPoint) {
        log.info("进入LoginNeededAspect");
        ContextBean c=ContextBean.getThreadContextBean();
        if(!c.isLogined()){
            throw new BusinessException(CommonResultCode.LOGIN_FAIL);
        }
    }

    public static void main(String[] args) {
        System.out.println("开始测试");
        try{
            ss();
        }catch(Exception e){
            log.error("出错",e);
        }

    }

    private static void ss(){

        int a=1;
        if(a==1){
            throw new BusinessException(CommonResultCode.LOGIN_FAIL);
        }
    }

}
