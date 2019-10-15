package com.example.demo.ctrl.exception;

import lombok.Data;
import lombok.NonNull;

/**
 * 异常类
 */
@Data
public class BusinessException extends RuntimeException{
    //错误码
    @NonNull
    private CommonResultCode resultCode;
    private Object data;
}
