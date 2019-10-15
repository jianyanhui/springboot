package com.example.demo.ctrl.config.handler;

import org.springframework.core.Ordered;

/**
 * 有序的处理器接口定义，具有有序的、独立的各个操作必须实现该接口
 * @param <T> 待处理对象
 */
public interface OrderedHandler<T> extends Handler<T>, Ordered {
}
