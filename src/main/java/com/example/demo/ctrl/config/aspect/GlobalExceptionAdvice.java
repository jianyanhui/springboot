package com.example.demo.ctrl.config.aspect;

import com.example.demo.ctrl.config.aspect.response.ResultReturn;
import com.example.demo.ctrl.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理，自定义返回异常代码
 */
@Slf4j
@RestControllerAdvice   //异常处理注解
public class GlobalExceptionAdvice {

    /**
     * 处理业务异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResultReturn<Object> bussinessExceptionHandler(BusinessException e, HttpServletRequest request){
        log.error("异常结果：requestId:"+ThreadLocalPool.request_id.get()+
                ",flowId:"+request.getRequestURI()+
                ",resultCode:"+e.getResultCode().getCode()+
                ",resultMessage:"+e.getResultCode().getMessage()+
                ",data:"+e.getData()+
                ",异常详细位置:",e
        );
        log.error("flowid:{},异常详细位置2:",request.getRequestURI(),e);
        return ResultReturn.build(e.getResultCode(),e.getData());
    }


}
