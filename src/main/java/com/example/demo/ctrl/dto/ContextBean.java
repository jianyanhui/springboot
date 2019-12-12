package com.example.demo.ctrl.dto;

import com.example.demo.ctrl.util.IDKeyUtil;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存上下文大对象
 */
@Data
public class ContextBean {
    public static void main(String[] args) {
        ContextBean c=new ContextBean();
//        c.putParam("hh",false);
        Boolean a=c.getParam("hh");//这里不要转为基本数据类型，会报空指针
        if(a!=null && a){
            System.out.println("结果2："+a);
        }
        System.out.println("结果："+a);
        String s=null;

    }
    //用于存放缓存类
    private static final ThreadLocal<ContextBean> thread_context_bean=new ThreadLocal<>();//线程安全存储
    /**flowid */
    private String flowId;
    /**签名 */
    private String sign;
    /**是否需要登录 */
    private boolean logined;
    /**登录信息 */
    private Author author;
    //请求参数map(禁止在forkjoin中并发操作)
    private Map<String,Object> paramMap=new HashMap<>();
    //返回参数map(考虑到可能会在forkjoin中并发操作，使用ConcurrentHashMap)
    private Map<String,Object> resultMap=new ConcurrentHashMap<>();
    //流程是否中断，true:是，false:否
    private boolean interrupted;


    public void putParam(String key,Object ob){
        paramMap.put(key,ob);
    }

    public <T> T getParam(String key){
       return (T)paramMap.get(key);
    }

    public void putResult(String key,Object ob){
        resultMap.put(key,ob);
    }

    public <T> T getResult(String key){
        return (T)resultMap.get(key);
    }
    //获取新的上下文
    public static ContextBean build(){
        ContextBean c=new ContextBean();
        c.setFlowId(IDKeyUtil.generateId());
        return c;
    }

    public static ContextBean getThreadContextBean(){
        return thread_context_bean.get();
    }

    public static void setThreadContextBean(ContextBean c){
        thread_context_bean.set(c);
    }

    public static void removeThreadContextBean(){
        thread_context_bean.remove();
    }
}
