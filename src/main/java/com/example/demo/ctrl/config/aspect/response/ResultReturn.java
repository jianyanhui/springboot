package com.example.demo.ctrl.config.aspect.response;

import com.example.demo.ctrl.dto.ContextBean;
import com.example.demo.ctrl.exception.CommonResultCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Optional;

/**
 * 接口返回值处理
 */
@Getter
@Setter
@ToString
public class ResultReturn<T> {
    //结果码
    private int code;
    //结果码描述
    private String message;
    //数据对象
    private T data;
    //流程id
    private String flowId;

    private ResultReturn(int code,String message,T data){
        this.code=code;
        this.message=message;
        this.data=data;
        this.flowId= Optional.ofNullable(ContextBean.getThreadContextBean()).map(ContextBean::getFlowId).orElse(null);//java8的用法，使用双冒号获取属性值
    }

    public static <T> ResultReturn<T> build(CommonResultCode result,T data){
        return new ResultReturn<T>(result.getCode(),result.getMessage(),data);
    }


}
