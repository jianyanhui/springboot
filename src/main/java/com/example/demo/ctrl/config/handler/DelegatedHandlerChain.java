package com.example.demo.ctrl.config.handler;

import com.example.demo.ctrl.config.annotation.HandlerComponent;
import com.example.demo.ctrl.config.aspect.HandlerComponentAspect;
import com.example.demo.ctrl.dto.ContextBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 委托处理器链，主要用来控制handler执行顺序com.example.demo.ctrl.config.handler.DelegatedHandlerChain
 */
@Slf4j
@Primary   //一个接口多个实现类时优先加载的子类
@HandlerComponent  //自定义注解
public class DelegatedHandlerChain implements OrderedHandler<ContextBean> {
    //所有handler链放这里,以HandlerComponent的threadHandler名称作为key,相同key的handler作为value集合
    private  final Map<String,List<OrderedHandler<ContextBean>>> handlerMap=new HashMap<>();

    //构造函数
    public DelegatedHandlerChain(List<OrderedHandler<ContextBean>> handlers){
        log.info("开始---DelegatedHandlerChain");
        for(OrderedHandler<ContextBean> orderedHandler:handlers){
            HandlerComponent handlerComponent= AnnotationUtils.findAnnotation(orderedHandler.getClass(),HandlerComponent.class);
            log.info("开始---handlerComponent:"+handlerComponent);
            if(handlerComponent!=null){
                for (String name: handlerComponent.value()){
                    if("".equals(name)) continue;;
                    List<OrderedHandler<ContextBean>> list=handlerMap.get(name);
                    if(list==null){
                        list=new ArrayList<>();
                        handlerMap.put(name,list);
                    }
                    list.add(orderedHandler);
                }
            }
        }
    }

    public DelegatedHandlerChain() {
    }

    /**
     * 是否支持该参数进行处理
     * @param t 待处理参数，实现时注意用final修饰，防止误修改
     * @return
     */
    @Override
    public boolean isEligible(final ContextBean t){
        //委托处理器链本身不需要根据处理对象来执行操作，始终返回错误，防止死循环
        return false;
    }

    /**
     * 处理操作 待处理参数，实现时注意用final修饰，防止误修改
     * @param t
     */
    @Override
    public void handle(final ContextBean t){
        List<OrderedHandler<ContextBean>> handlers=handlerMap.get(HandlerComponentAspect.current_handler.get());
        if(handlers==null){return;}
        for (OrderedHandler<ContextBean> handler:handlers){
            long start=System.currentTimeMillis();
            if(!t.isInterrupted()){
                handler.handle(t);
            }
            long end=System.currentTimeMillis();
            log.info("handler:{} ,useTimes:{}",handler.getClass().getName(),end-start);
        }
    }
    @Override
    public  int getOrder(){
        return Ordered.HIGHEST_PRECEDENCE;
    }

}
