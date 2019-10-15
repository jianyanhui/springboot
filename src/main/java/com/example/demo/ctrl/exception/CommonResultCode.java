package com.example.demo.ctrl.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 错误码
 */
@Getter
@AllArgsConstructor //所有参数构造方法
public enum CommonResultCode  {

    SUCCESS(0,"请求成功"),
    ILLEGAL_REQUEST(1,"非法请求"),
    LOGIN_FAIL(2,"未登录"),
    HTTP_ERROR(3,"调用http失败")
    ;

    private int code;
    private String message;

}
